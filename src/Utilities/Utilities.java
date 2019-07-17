package Utilities;

import Elements.Light;

import java.awt.*;
import java.util.List;

public class Utilities {
    static public Color addColors(Color color1, Color color2) {
        int redValue = color1.getRed() + color2.getRed();
        int greenValue = color1.getGreen() + color2.getGreen();
        int blueValue = color1.getBlue() + color2.getBlue();

        Color newColor = new Color(Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));

        return newColor;
    }

    static public Color addColors(List<Color> colors) {
        //int i = 0;
        Color newColor = new Color(0, 0, 0);
        //while (!colors.isEmpty()) {
        //    newColor = addColors(newColor, colors.get(i));
        //    i++;
        //}
        for (Color col : colors) {
            newColor = addColors(newColor, col);
        }
        return newColor;
    }

    static public Color multiplyToColor(double K, Color color) {
        int redValue = (int)(K * color.getRed());
        int greenValue = (int)(K * color.getGreen());
        int blueValue = (int)(K * color.getBlue());

        Color newColor = new Color (Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));

        return newColor;
    }
}
