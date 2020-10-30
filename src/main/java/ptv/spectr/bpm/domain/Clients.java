package ptv.spectr.bpm.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true) //игнорируем неизветсные поля в JSON
public class Clients  implements Serializable {


    private  Integer idclient;
    @JsonAlias("family")
    private  String family;
    private  String name;
    private  String Comments;
    private  Integer idclienttype;
//    private List<Passports> passports;
    private  String passports;
//    private List<Addresses> addresses;
    private String addresses;

    private static  final long serialVersionUID = 1L;
    public Clients() {
    }

    public Clients(String family, String name, String comments, Integer idclienttype) {
        this.family = family;
        this.name = name;
        Comments = comments;
        this.idclienttype = idclienttype;
    }

    @Override
    public String toString() {
        return "\nClients{" +
                "idClient=" + idclient +
                ", family='" + family + '\'' +
                ", name='" + name + '\'' +
                ", Comments='" + Comments + '\'' +
                ", idClientType=" + idclienttype +
                " "+passports.toString()+
                '}';
    }
    public Integer getIdclienttype() {
        return idclienttype;
    }

    public void setIdclienttype(Integer idclienttype) {
        this.idclienttype = idclienttype;
    }
//    public List<Passports> getPassports() {
//        return passports;
//    }

//    public void setPassports(List<Passports> passports) {
//        this.passports = passports;
//    }
    public void setPassports(String passports) {
        this.passports = passports;
   }


    public Integer getIdClient() {
        return idclient;
    }

    public void setIdClient(Integer idClient) {
        this.idclient = idClient;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getPassports() {
        return passports;
    }

    public String getAddresses() {
        return addresses;
    }
}


/*
{
    "name":"Виктор",
    "family":"Михайлов",
    "comments":"агент 03",
    "idclienttype":"1",
    "passports":"3302 145234"
}
 */