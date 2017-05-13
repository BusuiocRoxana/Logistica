package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by Roxana on 5/9/2017.
 */
public class Furnizor implements Serializable {

    private int cod_furnizor;
    private String denumire_furnizor;
    private String nr_inregistrare_RC;
    private int cod_adresa;
    private int rating;
    private String email;
    private Adresa adresa;

    public Furnizor(String denumire_furnizor, String nr_inregistrare_RC, int cod_adresa, int rating, String email) {
        this.denumire_furnizor = denumire_furnizor;
        this.nr_inregistrare_RC = nr_inregistrare_RC;
        this.cod_adresa = cod_adresa;
        this.rating = rating;
        this.email = email;
    }

    public Furnizor(String denumire_furnizor, String nr_inregistrare_RC, Adresa adresa, int rating, String email) {
        this.denumire_furnizor = denumire_furnizor;
        this.nr_inregistrare_RC = nr_inregistrare_RC;
        this.adresa = adresa;
        this.rating = rating;
        this.email = email;
    }

    public Furnizor(){}

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public int getCod_furnizor() {
        return cod_furnizor;
    }

    public void setCod_furnizor(int cod_furnizor) {
        this.cod_furnizor = cod_furnizor;
    }

    public String getDenumire_furnizor() {
        return denumire_furnizor;
    }

    public void setDenumire_furnizor(String denumire_furnizor) {
        this.denumire_furnizor = denumire_furnizor;
    }

    public String getNr_inregistrare_RC() {
        return nr_inregistrare_RC;
    }

    public void setNr_inregistrare_RC(String nr_inregistrare_RC) {
        this.nr_inregistrare_RC = nr_inregistrare_RC;
    }

    public int getCod_adresa() {
        return cod_adresa;
    }

    public void setCod_adresa(int cod_adresa) {
        this.cod_adresa = cod_adresa;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
