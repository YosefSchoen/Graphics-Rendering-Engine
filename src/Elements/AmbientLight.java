package Elements;

import Primitives.Point3d;

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
        this.Ka = Ka;
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
        int newRed;
        int newGreen;
        int newBlue;

        if (color.getRed() * Ka > 255) { newRed  = 255; }
        else { newRed = (int)(color.getRed() * Ka); }

        if (color.getGreen() * Ka > 255) { newGreen = 255; }
        else {newGreen = (int)(color.getGreen() * Ka); }

        if (color.getBlue() * Ka > 255) { newBlue = 255; }
        else {newBlue = (int)(color.getBlue() * Ka); }


        Color newColor = new Color(newRed, newGreen, newBlue);
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
