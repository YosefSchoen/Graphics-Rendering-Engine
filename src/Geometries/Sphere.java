package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


//Sphere class
public class Sphere extends RadialGeometry {

    //empty constructor
    public Sphere(){
        super();
        this.center = new Point3d();
    }

    //constructor
    public Sphere(double radius, Point3d center) {
        super(radius);
        this.center = center;
    }

    //copy constructor
    public Sphere(Sphere other) {
        super(other);
        this.center = other.center;
    }

    //getters
    public Point3d getCenter() {
        return center;
    }

    public Vector getNormal(Point3d P) {
        Vector N = P.subtract(this.center);
        N.normalize();
        return N;
    }

    //setter
    public void setCenter(Point3d center) {
        this.center = center;
    }


    public List<Point3d> findIntersections(Ray P) {
        //creating a list of potential intersection Points to return
        List<Point3d> intersections = new ArrayList<Point3d>();

        //L is the length from the sphere's center at the origin point of the ray
        Vector L = this.center.subtract(P.getP0());

        //tM is scalar length of the rays vector dotted with L
        P.getDirection().normalize();
        double tM = L.dotProduct(P.getDirection());

        //d is the distance of a perpendicular from the sphere center to the ray
        double d = Math.sqrt(Math.pow(L.length(), 2) - Math.pow(tM, 2));

        //tH is the length from the point d intersects the ray and the edge of the sphere along the ray
        double tH = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(d, 2));

        //t1 and t2 are the two potential intersection points from the ray to the sphere
        double t1 = tM + tH;
        double t2 = tM - tH;

        //t1 and t2 only intersect if they are greater than zero
        if (t1 >= 0) {
            Point3d p1 = new Point3d(P.getP0().add(P.getDirection().scalarMultiply(t1)));
            intersections.add(p1);
        }

        if (t2 >= 0) {
            Point3d p2 = new Point3d(P.getP0().add(P.getDirection().scalarMultiply(t2)));
            intersections.add(p2);
        }

        return intersections;
    }
    //center point
    private Point3d center;
}
