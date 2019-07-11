package Elements;

import Primitives.Point3d;
import Primitives.Vector;
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

        //Vector L = new Vector(getPosition());
        Vector L = getL(P);
        setDirection(direction.normalize());

        int redValue = (int)((this.color.getRed() * (direction.dotProduct(L))) / scalar);
        int greenValue = (int)((this.color.getGreen() * (direction.dotProduct(L)))/ scalar);
        int blueValue = (int)((this.color.getBlue() * (direction.dotProduct(L)))/ scalar);

        Color newColor = new Color(clamp(redValue), clamp(greenValue), clamp(blueValue));

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
