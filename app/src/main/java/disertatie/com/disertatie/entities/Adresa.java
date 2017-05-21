package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by Roxana on 5/9/2017.
 */
public class Adresa implements Serializable {
    private int cod_adresa;
    private int numar;
    private String strada;
    private String judet_sector;
    private String localitate;
    private String tara;

    public Adresa(int cod_adresa, int numar, String strada, String judet_sector, String localitate, String tara) {
        this.cod_adresa = cod_adresa;
        this.numar = numar;
        this.strada = strada;
        this.judet_sector = judet_sector;
        this.localitate = localitate;
        this.tara = tara;
    }
    public Adresa(int numar, String strada, String judet_sector, String localitate, String tara) {
        this.cod_adresa = cod_adresa;
        this.numar = numar;
        this.strada = strada;
        this.judet_sector = judet_sector;
        this.localitate = localitate;
        this.tara = tara;
    }
    public Adresa(){}

    public int getCod_adresa() {
        return cod_adresa;
    }

    public void setCod_adresa(int cod_adresa) {
        this.cod_adresa = cod_adresa;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getJudet_sector() {
        return judet_sector;
    }

    public void setJudet_sector(String judet_sector) {
        this.judet_sector = judet_sector;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    @Override
    public String toString() {

        return "Adresa"+
                " \nNumar " + numar +
                ", Strada " + strada +
                ", \nLocalitate " + localitate +
                ", Judet/Sector " + judet_sector+
                ", \nTara " + tara;
    }
}
