package Primitives;

//material hold the material for the 4 light types in the renderer and the shininess
public class Material {

    //empty constructor
    public Material() {
        this.Kd = 0.1;
        this.Ks = 0.1;
        this.Kr = 0.1;
        this.Kt = 0.1;
        this.nShininess = 1;
    }

    //constructor
    public Material(double Kd, double Ks, double Kr, double Kt, int nShininess) {
        this.Kd = Kd;
        this.Ks = Ks;
        this.Kr = Kr;
        this.Kt = Kt;
        this.nShininess = nShininess;
    }

    //copy constructor
    public Material(Material other) {
        this.Kd = other.Kd;
        this.Ks = other.Ks;
        this.Kr = other.Kr;
        this.Kt = other.Kt;
        this.nShininess = other.nShininess;
    }

    //getters
    public double getKd() {
        return Kd;
    }

    public double getKs() {
        return Ks;
    }

    public double getKr() {
        return Kr;
    }

    public double getKt() {
        return Kt;
    }

    public int getNShininess() {
        return nShininess;
    }

    //setters
    public void setKd(double Kd) {
        this.Kd = Kd;
    }

    public void setKs(double Ks) {
        this.Ks = Ks;
    }

    public void setKr(double Kr) {
        this.Kr = Kr;
    }

    public void setKt(double Kt) {
        this.Kt = Kt;
    }

    public void setNShininess(int nShininess) {
        this.nShininess = nShininess;
    }


    //values are K diffuse, K specular, K reflective , and K refraction
    private double Kd;
    private double Ks;
    private double Kr;
    private double Kt;

    //object shininess used in specular light calculation
    private int nShininess;
}
