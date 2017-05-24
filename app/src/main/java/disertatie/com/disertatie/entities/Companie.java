package disertatie.com.disertatie.entities;

/**
 * Created by Roxana on 5/9/2017.
 */
public class Companie {
    private int cod_companie;
    private String denumire_companie;
    private String nr_inreg_RC;
    private Adresa adresa;
    private String email;

    public Companie(int cod_companie, String denumire_companie, String nr_inreg_RC, Adresa adresa, String email) {
        this.cod_companie = cod_companie;
        this.denumire_companie = denumire_companie;
        this.nr_inreg_RC = nr_inreg_RC;
        this.adresa = adresa;
        this.email = email;
    }

    public Companie(String denumire_companie, String nr_inreg_RC, Adresa adresa, String email) {
        this.cod_companie = cod_companie;
        this.denumire_companie = denumire_companie;
        this.nr_inreg_RC = nr_inreg_RC;
        this.adresa = adresa;
        this.email = email;
    }

    public  Companie(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCod_companie() {
        return cod_companie;
    }

    public void setCod_companie(int cod_companie) {
        this.cod_companie = cod_companie;
    }

    public String getDenumire_companie() {
        return denumire_companie;
    }

    public void setDenumire_companie(String denumire_companie) {
        this.denumire_companie = denumire_companie;
    }

    public String getNr_inreg_RC() {
        return nr_inreg_RC;
    }

    public void setNr_inreg_RC(String nr_inreg_RC) {
        this.nr_inreg_RC = nr_inreg_RC;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Companie{" +
                "cod_companie=" + cod_companie +
                ", denumire_companie='" + denumire_companie + '\'' +
                ", nr_inreg_RC='" + nr_inreg_RC + '\'' +
                ", adresa=" + adresa +
                ", email='" + email + '\'' +
                '}';
    }
}
