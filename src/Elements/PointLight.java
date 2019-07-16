package Elements;

import Primitives.Point3d;
import Elements.LightSource;
import Primitives.Vector;

import java.awt.*;


public class PointLight extends Light implements LightSource {
    public PointLight() {
        super();
        this.position = new Point3d();
        this.Kc = 0.5;
        this.Kl = 0.5;
        this.Kq = 0.5;
    }

    public PointLight(Color color, Point3d position, double Kc, double Kl, double Kq) {
        super(color);
        this.position = position;
        this.Kc = clamp(Kc);
        this.Kl = clamp(Kl);
        this.Kq = clamp(Kq);
    }

    public PointLight(PointLight other) {
        super(other);
        this.position = other.position;
        this.Kc = other.Kc;
        this.Kl = other.Kl;
        this. Kq = other.Kq;
    }


    public Color getIntesity(Point3d point) {
        double distance = position.distance(point);
        double factor = (Kc + Kl*distance + Kq *distance*distance);

        int red = this.color.getRed();
        int green = this.color.getGreen();
        int blue = this.color.getBlue();

        return new Color(clamp((int)(red / factor)), clamp((int)(green / factor)), clamp((int)(blue / factor)));
    }


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

    public Vector getL(Point3d point) {
        return new Vector(position.subtract(point));
    }

    //needs to be made
    public Color getIntensity(Point3d P) {
        double scalar = Kc + (Kl * position.distance(P)) + (Kq * Math.pow(position.distance(P), 2));

        int redValue = (int)(this.color.getRed() * scalar);
        int greenValue = (int)(this.color.getGreen() * scalar);
        int blueValue = (int)(this.color.getBlue() * scalar);
        Color newColor = new Color(clamp(redValue), clamp(greenValue), clamp(blueValue));

        return newColor;
    }

    public void setPosition(Point3d position) {
        this.position = new Point3d(position);
    }

    public void setKc(double kc) {
        Kc = kc;
    }

    public void setKl(double kl) {
        Kl = kl;
    }

    public void setKq(double kq) {
        Kq = kq;
    }


    private Point3d position;
    private double Kc;
    private double Kl;
    private double Kq;
}
