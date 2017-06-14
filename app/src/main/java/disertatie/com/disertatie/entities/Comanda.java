package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by Roxana on 5/21/2017.
 */

public class Comanda implements Serializable {

    private int cod_comanda;
    private CerereOferta cerereOferta;
    private Taxa taxa;

    public Comanda(int cod_comanda, CerereOferta cerereOferta, Taxa taxa) {
        this.cod_comanda = cod_comanda;
        this.cerereOferta = cerereOferta;
        this.taxa = taxa;
    }

    public Comanda(){}

    public int getCod_comanda() {
        return cod_comanda;
    }

    public void setCod_comanda(int cod_comanda) {
        this.cod_comanda = cod_comanda;
    }

    public CerereOferta getCerereOferta() {
        return cerereOferta;
    }

    public void setCerereOferta(CerereOferta cerereOferta) {
        this.cerereOferta = cerereOferta;
    }

    public Taxa getTaxa() {
        return taxa;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "cod_comanda=" + cod_comanda +
                ", cerereOferta=" + cerereOferta +
                ", taxa=" + taxa +
                '}';
    }


}
