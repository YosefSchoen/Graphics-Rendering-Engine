package Elements;

import Primitives.Point3d;
import Utilities.Utilities;

import java.awt.*;

public class AmbientLight extends Light {
    public AmbientLight() {
        super();
        this.Ka = 1;
    }

    /**
     * Create an ambient light with a passed color and intensity
     * @param color The lights color
     * @param Ka the lights intensity
     */
    public AmbientLight(Color color, double Ka){
        super(color);
        this.Ka = clamp(Ka);
    }

    /**
     * Ambient light copy constructor
     * @param other an AmbientLight to copy
     */
    public AmbientLight(AmbientLight other) {
        super(other);
        this.Ka = other.Ka;
    }

    /**
     * Get the ambient intensity scalar
     * @return a double containing a value in the range [0, 1]
     */
    public double getKa() {
        return Ka;
    }

    /**
     * Returns the color value of the light scaled by the intensity
     * @return the scaled java color
     */
    public Color getIntensity(Point3d point) {
        Color newColor = Utilities.multiplyToColor(Ka, color);
        return newColor;
    }

    /**
     * Sets the intensity scalar
     * @param ka a double in the range [0, 1]
     */
    public void setKa(double ka) {
        Ka = clamp(ka);
    }



    private double Ka;
}
