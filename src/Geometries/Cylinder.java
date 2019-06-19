package Geometries;

import Primitives.Point3d;
import Primitives.Ray;
import Primitives.Vector;

import java.util.List;

//Cylinder class
public class Cylinder extends RadialGeometry{
    //empty constructor
    Cylinder() {
        this.axisRay = new Ray();
    }

    //constructor
    Cylinder(Ray axisRay) {
        this.axisRay = axisRay;
    }

    //copy constructor
    Cylinder(Cylinder other) {
        this.axisRay = other.axisRay;
    }

    //getter
    public Ray getAxisRay() {
        return axisRay;
    }

    public Vector getNormal(Point3d P) {
        return new Vector();
    }
    //setter
    public void setAxisRay(Ray axisRay) {
        this.axisRay = axisRay;
    }

    //ray which the cylinder rotates around
    private Ray axisRay;

    public List<Point3d> findIntersections(Ray P) {
        return null;
    }
}
