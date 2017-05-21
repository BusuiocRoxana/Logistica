package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by Roxana on 5/21/2017.
 */

public class Receptie implements Serializable{

    private int cod_receptie;
    private Comanda comanda;
    private double cantitate_receptionata;
    private String data_receptie;

    public Receptie(int cod_receptie, Comanda comanda, double cantitate_receptionata, String data_receptie) {
        this.cod_receptie = cod_receptie;
        this.comanda = comanda;
        this.cantitate_receptionata = cantitate_receptionata;
        this.data_receptie = data_receptie;
    }

    public Receptie(){}

    public int getCod_receptie() {
        return cod_receptie;
    }

    public void setCod_receptie(int cod_receptie) {
        this.cod_receptie = cod_receptie;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public double getCantitate_receptionata() {
        return cantitate_receptionata;
    }

    public void setCantitate_receptionata(double cantitate_receptionata) {
        this.cantitate_receptionata = cantitate_receptionata;
    }

    public String getData_receptie() {
        return data_receptie;
    }

    public void setData_receptie(String data_receptie) {
        this.data_receptie = data_receptie;
    }

    @Override
    public String toString() {
        return "Receptie{" +
                "cod_receptie=" + cod_receptie +
                ", comanda=" + comanda +
                ", cantitate_receptionata=" + cantitate_receptionata +
                ", data_receptie='" + data_receptie + '\'' +
                '}';
    }
}
