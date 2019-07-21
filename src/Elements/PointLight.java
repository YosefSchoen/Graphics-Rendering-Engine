package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import Utilities.Utilities;

import java.awt.*;


//a type of light that has a position and radiates equally around it with attenuation
public class PointLight extends Light implements LightSource {

    //empty constructor
    public PointLight() {
        super();
        this.position = new Point3d();
        this.Kc = 0.5;
        this.Kl = 0.5;
        this.Kq = 0.5;
    }

    //constructor
    public PointLight(Color color, Point3d position, double Kc, double Kl, double Kq) {
        super(color);
        this.position = position;
        this.Kc = clamp(Kc);
        this.Kl = clamp(Kl);
        this.Kq = clamp(Kq);
    }

    //copy constructor
    public PointLight(PointLight other) {
        super(other);
        this.position = other.position;
        this.Kc = other.Kc;
        this.Kl = other.Kl;
        this. Kq = other.Kq;
    }

    //getters
    public Point3d getPosition() {
        return new Point3d(position);
    }

    public double getKc() {
        return Kc;
    }

    public double getKl() {
        return Kl;
    }

    public double getKq() {
        return Kq;
    }

    //check this
    public Vector getL(Point3d point) {
        Vector L = new Vector(position.subtract(point));
        return L;
    }

    //the intensity varies based on distance from the light
    public Color getIntensity(Point3d P) {

        //attenuation factors
        double distance = position.distance(P);
        double scalar = Kc + Math.pow(Kl, distance) + Math.pow(Kq, Math.pow(distance, 2));

        //scale the color by the attenuation factor
        Color newColor = Utilities.multiplyToColor(scalar, color);

        return newColor;
    }


    //setters
    public void setPosition(Point3d position) {
        this.position = new Point3d(position);
    }

    public void setKc(double kc) {
        this.Kc = kc;
    }

    public void setKl(double kl) {
        this.Kl = kl;
    }

    public void setKq(double kq) {
        this.Kq = kq;
    }


    //position of the light
    private Point3d position;

    //attenuation factors K const, K linear, and K quadratic
    private double Kc;
    private double Kl;
    private double Kq;
}
