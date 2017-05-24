package disertatie.com.disertatie.entities;

import java.io.Serializable;

/**
 * Created by I335495 on 5/24/2017.
 */

public class Factura implements Serializable {
    private int cod_factura;
    private Receptie receptie;
    private double cantitate_facturata;
    private String data_factura;
  //  private boolean isPaid;

    public Factura(){}

    public Factura(int cod_factura, Receptie receptie, double cantitate_facturata, String data_factura) {
        this.cod_factura = cod_factura;
        this.receptie = receptie;
        this.cantitate_facturata = cantitate_facturata;
        this.data_factura = data_factura;

    }

    public int getCod_factura() {
        return cod_factura;
    }

    public void setCod_factura(int cod_factura) {
        this.cod_factura = cod_factura;
    }

    public Receptie getReceptie() {
        return receptie;
    }

    public void setReceptie(Receptie receptie) {
        this.receptie = receptie;
    }

    public double getCantitate_facturata() {
        return cantitate_facturata;
    }

    public void setCantitate_facturata(double cantitate_facturata) {
        this.cantitate_facturata = cantitate_facturata;
    }

    public String getData_factura() {
        return data_factura;
    }

    public void setData_factura(String data_factura) {
        this.data_factura = data_factura;
    }



    @Override
    public String toString() {
        return "Factura{" +
                "cod_factura=" + cod_factura +
                ", receptie=" + receptie +
                ", cantitate_facturata=" + cantitate_facturata +
                ", data_factura='" + data_factura + '\'' +
                '}';
    }
}
