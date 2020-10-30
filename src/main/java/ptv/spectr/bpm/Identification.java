package ptv.spectr.bpm;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ptv.spectr.bpm.domain.Clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.camunda.spin.Spin.JSON;


@Component
public class Identification implements JavaDelegate{

    //тянем из настроек приложения application.yaml
    @Value("${PassportLenght}")
    private int PassportLenght;

    @Value("${urlPathClientByPassport}")
    private String urlPathClientByPassport;

    @Value("${host}")
    private String host;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
       String PassportNumber = (String) delegateExecution.getVariable("PassportNumber");
        PassportLenght = PassportLenght == 0 ? 10 : PassportLenght; //на случай если забыли указать в настройках
       //генерация ошибки
       if(PassportNumber.length()<PassportLenght){
           throw new BpmnError("lenghtNumberError");
       }
        Boolean PassportIs = false;
        ArrayList<Clients> MyClients = createClientByPassport(PassportNumber);
        if(!MyClients.isEmpty()){
            if(MyClients.size()>1){
             for (Clients clt:MyClients) {
                System.out.println(clt.getFamily());
             }
                delegateExecution.setVariable("WithSamePasports", MyClients);
                throw new BpmnError("ClientsMoreThanOne"); //3302 122234
            }else{
                PassportIs = true;
            }
        }else{
            System.out.println("Клиент с таким паспортом не найден");
            PassportIs = false;
//            throw new BpmnError("ClientNotFound");
        }

       delegateExecution.setVariable("PassportIs", PassportIs);
       System.out.println(PassportNumber);
//       System.out.println("фамилия: " + MyClient.getFamily());
//

    }
    /*
    возвращает список клиентов с одинаковым паспортом
     */
    private ArrayList<Clients> createClientByPassport(String PassportNumber){
        ArrayList<Clients> clients = new ArrayList<>();
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(host).path(urlPathClientByPassport+PassportNumber).build().encode();
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().url(uriComponents.toUriString()).get();
        Map<String,String> headers = new HashMap<>(); //загловки
        headers.put("Content-type", "application/json");
        request.setRequestParameter("headers", headers);
        HttpResponse response = request.execute();

        if (response.getStatusCode() == 200 || !response.getResponse().isEmpty()) {
            Object obj= JSONValue.parse(response.getResponse());  //
            JSONArray finalResult=(JSONArray)obj;
            if(!finalResult.isEmpty()){
                for (Object jsn:finalResult) {
            //      System.out.println(finalResult.get(0));
            //      client = JSON(response.getResponse()).mapTo(Clients.class);  это не заработало
                    SpinJsonNode node = JSON(jsn);
                    Clients client = new Clients();
                    client.setFamily(node.prop("family").stringValue());
                    client.setName(node.prop("name").stringValue());
//                    System.out.println(client.getFamily());
                    clients.add(client);
                }
            }
        }
        response.close();
        return clients;
    }
}
