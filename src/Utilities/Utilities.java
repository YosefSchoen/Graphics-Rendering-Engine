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

    static public Color averageColor(List<Color> colors) {
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;


        for(int i = 0; i < colors.size(); i++) {
            avgRed = avgRed + colors.get(i).getRed();
            avgGreen = avgGreen + colors.get(i).getGreen();
            avgBlue = avgBlue + colors.get(i).getBlue();
        }

        avgRed = avgRed / (colors.size());
        avgGreen = avgGreen / (colors.size());
        avgBlue = avgBlue / (colors.size());

        Color avgColor = new Color(avgRed, avgGreen, avgBlue);
        return  avgColor;
    }
}
