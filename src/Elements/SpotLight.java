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
        super(color, position, clamp(Kc), clamp(Kl), clamp(Kq));
        this.direction = direction;
    }

    public SpotLight(SpotLight other) {
        super(other);
        this.direction = other.direction;
    }

    @Override
    public Color getIntensity(Point3d P) {
        double scalar = getKc() + (getKl()*P.distance(getPosition())) + (getKq()*Math.pow(P.distance(getPosition()), 2));
        scalar = scalar * direction.dotProduct(getL(P));
        //Vector L = new Vector(getPosition());
        //Vector L = getL(P);
        setDirection(direction.normalize());

        Color newColor = Utilities.multiplyToColor(scalar, color);

        return newColor;
    }

    public Vector getL(Point3d point){
        return new Vector(point.subtract(getPosition())).normalize();
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    private Vector direction;
}
