package Elements;

import Primitives.Point3d;
import Primitives.Vector;

import java.awt.*;

public interface LightSource {
    public Color getIntensity(Point3d point3d);
    public Vector getL(Point3d point3d);
}
