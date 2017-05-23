package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by Roxana on 5/21/2017.
 */

public class Taxa implements Serializable {
    int cod_taxa;
    String denumire_taxa;
    double procent_taxa;

    public Taxa(int cod_taxa, String denumire_taxa, double procent_taxa) {
        this.cod_taxa = cod_taxa;
        this.denumire_taxa = denumire_taxa;
        this.procent_taxa = procent_taxa;
    }

    public Taxa(){}

    public int getCod_taxa() {
        return cod_taxa;
    }

    public void setCod_taxa(int cod_taxa) {
        this.cod_taxa = cod_taxa;
    }

    public String getDenumire_taxa() {
        return denumire_taxa;
    }

    public void setDenumire_taxa(String denumire_taxa) {
        this.denumire_taxa = denumire_taxa;
    }

    public double getProcent_taxa() {
        return procent_taxa;
    }

    public void setProcent_taxa(double procent_taxa) {
        this.procent_taxa = procent_taxa;
    }

    @Override
    public String toString() {
        return denumire_taxa;

    }
}
