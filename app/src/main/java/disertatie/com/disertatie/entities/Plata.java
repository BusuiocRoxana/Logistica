package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by I335495 on 5/24/2017.
 */

public class Plata implements Serializable {

    private int cod_plata;
    private Factura factura;
    private double suma_platita;
    private String data_plata;

    public Plata(){}

    public Plata(int cod_plata, Factura factura, double suma_platita, String data_plata) {
        this.cod_plata = cod_plata;
        this.factura = factura;
        this.suma_platita = suma_platita;
        this.data_plata = data_plata;
    }

    public int getCod_plata() {
        return cod_plata;
    }

    public void setCod_plata(int cod_plata) {
        this.cod_plata = cod_plata;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public double getSuma_platita() {
        return suma_platita;
    }

    public void setSuma_platita(double suma_platita) {
        this.suma_platita = suma_platita;
    }

    public String getData_plata() {
        return data_plata;
    }

    public void setData_plata(String data_plata) {
        this.data_plata = data_plata;
    }

    @Override
    public String toString() {
        return "Plata{" +
                "cod_plata=" + cod_plata +
                ", factura=" + factura +
                ", suma_platita=" + suma_platita +
                ", data_plata='" + data_plata + '\'' +
                '}';
    }
}
