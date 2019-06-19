package Primitives;

public class Material {
    public Material() {
        this.Kd = 0;
        this.Ks = 0;
        this.Kr = 0;
        this.Kt = 0;
        this.nShininess = 0;
    }

    public Material(double Kd, double Ks, double Kr, double Kt, int nShininess) {
        this.Kd = Kd;
        this.Ks = Ks;
        this.Kr = Kr;
        this.Kt = Kt;
        this.nShininess = nShininess;
    }

    public Material(Material other) {
        this.Kd = other.Kd;
        this.Ks = other.Ks;
        this.Kr = other.Kr;
        this.Kt = other.Kt;
        this.nShininess = other.nShininess;
    }

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

    private double Kd;
    private double Ks;
    private double Kr;
    private double Kt;
    private int nShininess;
}
