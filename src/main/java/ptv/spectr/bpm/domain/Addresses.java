package ptv.spectr.bpm.domain;

public class Addresses {
    private Integer idaddress;
    private String Address;
    //    private Integer idclient;

    public Addresses() {
    }

    public Addresses(String address) {
        Address = address;
    }


    public Integer getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(Integer idaddress) {
        this.idaddress = idaddress;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "\nAddresses{" +
                "idaddress=" + idaddress +
                ", Addresses='" + Address + '\'' +
                '}';
    }
}
