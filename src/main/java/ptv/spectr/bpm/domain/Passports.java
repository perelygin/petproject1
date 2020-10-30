package ptv.spectr.bpm.domain;

public class Passports {
    private Integer idpassports;
    private String passportnumber;
    public Passports() {
    }

    public Passports(String passportnumber) {
        this.passportnumber = passportnumber;
    }



    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    @Override
    public String toString() {
        return "Passports{" +
                "idpassports=" + idpassports +
                ", passportnumber='" + passportnumber + '\'' +
                '}';
    }
}
