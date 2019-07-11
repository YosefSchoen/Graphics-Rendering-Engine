package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import Elements.LightSource;
import java.awt.*;


public class DirectionalLight extends Light implements LightSource {
    public DirectionalLight() {
        super();
        this.direction = new Vector(0, 1, 0);
    }

    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }

    public DirectionalLight(DirectionalLight other) {
        super(other);
        this.direction = other.direction;
    }

    public Vector getDirection() {
        return this.direction;
    }

    public Vector getL(Point3d point) {
        return direction.normalize();
    }

    //needs to be written
    public Color getIntensity(Point3d P) {
        return color;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Color getIntensity() {
        return color;
    }

    private Vector direction;
}
