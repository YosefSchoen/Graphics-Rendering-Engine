package Elements;

import Primitives.Point3d;
import Utilities.Utilities;

import java.awt.*;


//ambient light is a type of light that brightens up the scene by a single color
public class AmbientLight extends Light {

    //empty constructor
    public AmbientLight() {
        super();
        this.Ka = 1;
    }

    /**
     * Create an ambient light with a passed color and intensity
     * @param color The lights color
     * @param Ka the lights intensity
     */

    //constructor
    public AmbientLight(Color color, double Ka){
        super(color);
        this.Ka = clamp(Ka);
    }

    /**
     * Ambient light copy constructor
     * @param other an AmbientLight to copy
     */

    //copy constructor
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


    //Ka is a scalar that ranges between 0 and 1 (nothing to complete brightness)
    private double Ka;
}
