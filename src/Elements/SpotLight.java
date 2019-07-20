package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import Utilities.Utilities;

import java.awt.*;

public class SpotLight extends PointLight{
    public SpotLight() {
        super();
        this.direction = new Vector();
    }

    public SpotLight(Color color, Point3d position, double Kc, double Kl, double Kq, Vector direction) {
        super(color, position, Kc, Kl, Kq);
        this.direction = direction;
    }

    public SpotLight(SpotLight other) {
        super(other);
        this.direction = other.direction;
    }

    @Override
    public Color getIntensity(Point3d P) {
        double distance = getPosition().distance(P);
        double scalar = getKc() + (getKl() * distance) + (getKq() * Math.pow(distance, 2));
        direction = direction.normalize();
        scalar = scalar * direction.dotProduct(getL(P));
        //Vector L = new Vector(getPosition());
        //Vector L = getL(P);

        Color newColor = Utilities.multiplyToColor(scalar, color);

        return newColor;
    }

    public Vector getL(Point3d point){
        Vector L = new Vector(point.subtract(getPosition()).normalize());
        return L;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    private Vector direction;
}
