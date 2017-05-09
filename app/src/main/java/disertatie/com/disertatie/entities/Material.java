package disertatie.com.disertatie.entities;

/**
 * Created by Roxana on 5/9/2017.
 */
public class Material {
    private int cod_material;
    private String denumire_material;
    private double stoc_curent;
    private double stoc_minim;

    public Material(String denumire_material, double stoc_curent, double stoc_minim) {
        this.denumire_material = denumire_material;
        this.stoc_curent = stoc_curent;
        this.stoc_minim = stoc_minim;
    }

    public int getCod_material() {
        return cod_material;
    }

    public void setCod_material(int cod_material) {
        this.cod_material = cod_material;
    }

    public String getDenumire_material() {
        return denumire_material;
    }

    public void setDenumire_material(String denumire_material) {
        this.denumire_material = denumire_material;
    }

    public double getStoc_curent() {
        return stoc_curent;
    }

    public void setStoc_curent(double stoc_curent) {
        this.stoc_curent = stoc_curent;
    }

    public double getStoc_minim() {
        return stoc_minim;
    }

    public void setStoc_minim(double stoc_minim) {
        this.stoc_minim = stoc_minim;
    }
}
