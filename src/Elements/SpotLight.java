package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import Utilities.Utilities;

import java.awt.*;

//spot light a point light but the light shines in a specified direction which affects attenuation
public class SpotLight extends PointLight{

    //empty constructor
    public SpotLight() {
        super();
        this.direction = new Vector();
    }

    //constructor
    public SpotLight(Color color, Point3d position, double Kc, double Kl, double Kq, Vector direction) {
        super(color, position, Kc, Kl, Kq);
        this.direction = direction;
    }

    //copy constructor
    public SpotLight(SpotLight other) {
        super(other);
        this.direction = other.direction;
    }


    @Override
    //getters
    //get intensity is affected by the attenuation factors and the direction of the light over a distance
    public Color getIntensity(Point3d P) {

        //attenuation factors
        double distance = getPosition().distance(P);
        double scalar = getKc() + (getKl() * distance) + (getKq() * Math.pow(distance, 2));
        direction = direction.normalize();
        scalar = scalar * direction.dotProduct(getL(P));
        //Vector L = new Vector(getPosition());
        //Vector L = getL(P);

        //scale color by attenuation factor
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

    //setter
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    //the direction the light will shine in
    private Vector direction;
}
