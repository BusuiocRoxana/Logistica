package disertatie.com.disertatie.entities;

import java.util.Date;

/**
 * Created by Roxana on 5/13/2017.
 */

public class CerereOferta {

    private int cod_cerere_oferta;
    private Material material;
    private double cantitate;
    private Date termen_limita_raspuns;
    private Furnizor furnizor;
    private boolean status;
    private double pret;
    private Date data_livrare;

    public CerereOferta(int cod_cerere_oferta, Material material, double cantitate, Date termen_limita_raspuns, Furnizor furnizor, boolean status, double pret, Date data_livrare) {
        this.cod_cerere_oferta = cod_cerere_oferta;
        this.material = material;
        this.cantitate = cantitate;
        this.termen_limita_raspuns = termen_limita_raspuns;
        this.furnizor = furnizor;
        this.status = status;
        this.pret = pret;
        this.data_livrare = data_livrare;
    }

    public CerereOferta(Material material, double cantitate, Date termen_limita_raspuns, Furnizor furnizor,
                        boolean status, double pret, Date data_livrare) {
        this.cod_cerere_oferta = cod_cerere_oferta;
        this.material = material;
        this.cantitate = cantitate;
        this.termen_limita_raspuns = termen_limita_raspuns;
        this.furnizor = furnizor;
        this.status = status;
        this.pret = pret;
        this.data_livrare = data_livrare;
    }


    public CerereOferta(){}

    public int getCod_cerere_oferta() {
        return cod_cerere_oferta;
    }

    public void setCod_cerere_oferta(int cod_cerere_oferta) {
        this.cod_cerere_oferta = cod_cerere_oferta;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public Date getTermen_limita_raspuns() {
        return termen_limita_raspuns;
    }

    public void setTermen_limita_raspuns(Date termen_limita_raspuns) {
        this.termen_limita_raspuns = termen_limita_raspuns;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Date getData_livrare() {
        return data_livrare;
    }

    public void setData_livrare(Date data_livrare) {
        this.data_livrare = data_livrare;
    }

    public double calculeazaValoare(double pret, double cantitate){
        double valoare = pret*cantitate;
        return valoare;
    }
}
