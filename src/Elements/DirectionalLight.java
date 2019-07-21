package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import java.awt.*;

//directional light is a type of light that shines in a specific direction with no attenuation
public class DirectionalLight extends Light implements LightSource {

    //empty constructor
    public DirectionalLight() {
        super();
        this.direction = new Vector(0, 1, 0);
    }

    //constructor
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }

    //copy constructor
    public DirectionalLight(DirectionalLight other) {
        super(other);
        this.direction = other.direction;
    }

    //getters
    public Vector getDirection() {
        return this.direction;
    }

    public Vector getL(Point3d point) {
        return direction.normalize();
    }

    //no attenuation to reduce the color over distance
    public Color getIntensity(Point3d P) {
        return color;
    }

    //setter
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    private Vector direction;
}
