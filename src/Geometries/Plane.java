package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Plane class
public class Plane extends Geometry implements FlatGeometry{
    //empty constructor
    public Plane() {
        super();
        this.Q = new Point3d();
        this.Q1 = null;
        this.Q2 = null;
        this.N = new Vector();
    }

    //constructor with normal
    public Plane(Point3d Q, Vector N) {
        this.Q = Q;
        this.Q1 = null;
        this.Q2 = null;
        this.N = N;
    }

    //alternative constructor with normal
    public Plane(Point3d Q, Vector N, Material material, Color color) {
        super(material, color);
        this.Q = Q;
        this.Q1 = null;
        this.Q2 = null;
        this.N = N;
    }

    //constructor with 3 points
    public Plane(Point3d Q, Point3d Q1, Point3d Q2) {
        this.Q = Q;
        this.Q1 = Q1;
        this.Q2 = Q2;

        Vector t1 = this.Q1.subtract(this.Q);
        Vector t2 = this.Q2.subtract(this.Q);
        this.N = t1.crossProduct(t2).normalize();
    }

    //alternative constructor 3 points
    public Plane(Point3d Q, Point3d Q1, Point3d Q2, Material material, Color color) {
        super(material, color);
        this.Q = Q;
        this.Q1 = Q1;
        this.Q2 = Q2;

        Vector t1 = this.Q1.subtract(this.Q);
        Vector t2 = this.Q2.subtract(this.Q);
        this.N = t1.crossProduct(t2).normalize();
    }

    //copy constructor
    public Plane(Plane other) {
        super(other);
        this.Q = other.Q;
        this.Q1 = other.Q1;
        this.Q2 = other.Q2;

        this.setMaterial(other.getMaterial());
        this.setEmission(other.getEmission());

        this.N = other.N;
    }

    //getters
    public Point3d getQ() {
        return Q;
    }

    public Vector getNormal(Point3d P) {
        return N;
    }

    //setters
    public void setQ(Point3d Q) {
        this.Q = Q;
    }

    public void setN(Vector N) {
        this.N = N;
    }

    //find intersections of camera ray P and a plane
    public List<Point3d> findIntersections(Ray P) {
        List<Point3d> intersectionPoints = new ArrayList<Point3d>();
        Point3d P0 = P.getP0();
        Vector N = this.getNormal(null);
        Vector V = P.getDirection();

        if (N.dotProduct(V) == 0) {
            // ray.getDirection() is parallel to the plane
            // no intersections points
            return intersectionPoints;
        }

        Vector u = new Vector(Q.subtract(P0));
        double t = (u.dotProduct(N)) / N.dotProduct(V);

        if (t >= 0) {
            Vector Vs = V.scalarMultiply(t);
            Point3d p = P0.add(Vs);
            intersectionPoints.add(p);
        }
        return intersectionPoints;
    }


    //a plane is defined by at least 3 point or a point an a normal
    private Point3d Q;
    private Point3d Q1;
    private Point3d Q2;
    private Vector N;

}
