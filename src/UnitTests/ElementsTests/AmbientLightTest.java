package UnitTests.ElementsTests;

import Elements.AmbientLight;
import Primitives.Point3d;
import java.awt.*;
import static org.junit.Assert.assertEquals;


public class AmbientLightTest {
Point3d point = new Point3d(0,0,0);
Color color = new Color(100, 50, 50);
double Ka = 0.2;
AmbientLight ambientLight = new AmbientLight(color, Ka);

    @org.junit.Test
    public void getIntensityTest() {
        assertEquals("", 20, ambientLight.getIntensity(point).getRed(), 1e-10);
        assertEquals("", 10, ambientLight.getIntensity(point).getGreen(), 1e-10);
        assertEquals("", 10, ambientLight.getIntensity(point).getBlue(), 1e-10);

        System.out.println(ambientLight.getIntensity(point).getRed());
        System.out.println(ambientLight.getIntensity(point).getGreen());
        System.out.println(ambientLight.getIntensity(point).getBlue());

    }
}