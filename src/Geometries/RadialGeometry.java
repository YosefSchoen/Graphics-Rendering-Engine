package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;

import java.awt.*;

//Radius of a shape class
public abstract class RadialGeometry extends Geometry {
   //empty constructor
    public RadialGeometry() {
        this.radius = 0;
    }

    //constructor
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    //copy constructor
    public RadialGeometry(RadialGeometry other) {
        super(other);
        this.radius = other.radius;
    }

    //getters
    public double getRadius() {
        return this.radius;
    }

    public abstract Vector getNormal(Point3d P);

    //setter
    public void setRadius(double radius) {
        this.radius = radius;
    }

    //radius of a shape
    double radius;
}
