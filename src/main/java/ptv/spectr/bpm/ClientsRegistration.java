package ptv.spectr.bpm;

import net.minidev.json.JSONValue;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ptv.spectr.bpm.domain.Clients;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClientsRegistration implements JavaDelegate {
    @Value("${urlPathClientRegistration}")
    private String urlPathClientRegistration;

    @Value("${host}")
    private String host;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String PassportNumber = (String) delegateExecution.getVariable("PassportNumber");
        String clientName = (String) delegateExecution.getVariable("clientName");
        String familyClient = (String) delegateExecution.getVariable("familyClient");
        String address = (String) delegateExecution.getVariable("address");
        String comment = (String) delegateExecution.getVariable("comment");

        Clients NewClient =  new Clients(familyClient, clientName, comment, 1);
        NewClient.setPassports(PassportNumber);
        NewClient.setAddresses(address);
        JSONValue jsn = new JSONValue();
        System.out.println(jsn.toJSONString(NewClient));
//      String jsonS = "{\"name\":\"Демид\",\"family\":\"Рунокатов\",\"comments\":\"агент 03\",\"idclienttype\":\"1\",\"passports\":\"3302 145111\"}";
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(host).path(urlPathClientRegistration).build().encode();
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().url(uriComponents.toUriString()).post().payload(jsn.toJSONString(NewClient));
        Map<String,String> headers = new HashMap<>(); //загловки
        headers.put("Content-type", "application/json");
        request.setRequestParameter("headers", headers);
        HttpResponse response = request.execute();
        if (response.getStatusCode() != 201) {   //ресурс создан
            throw new BpmnError("ClientNotRegistred");
        }
        response.close();


    }
}
