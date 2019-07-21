package Elements;

import Primitives.Point3d;
import Primitives.Vector;

import java.awt.*;

//an interface for some of the light types to be able to list them together
public interface LightSource {
    Color getIntensity(Point3d point3d);
    Vector getL(Point3d point3d);
}
