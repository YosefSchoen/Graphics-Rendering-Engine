package Elements;

import Primitives.Point3d;
import Primitives.Vector;

import java.awt.*;

public interface LightSource {
    Color getIntensity(Point3d point3d);
    Vector getL(Point3d point3d);
}
