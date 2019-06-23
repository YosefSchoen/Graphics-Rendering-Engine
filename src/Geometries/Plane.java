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
        this.Q = new Point3d();
        this.Q1 = null;
        this.Q2 = null;
        this.N = new Vector();
    }

    //constructor
    public Plane(Point3d Q, Vector N, Material mat, Color emi) {
        this.Q = Q;
        this.Q1 = null;
        this.Q2 = null;

        this.setMaterial(mat);
        this.setEmission(emi);

        this.N = N;
    }

    public Plane(Point3d Q, Point3d Q1, Point3d Q2, Material mat, Color emi) {
        this.Q = Q;
        this.Q1 = Q1;
        this.Q2 = Q2;

        Vector t1 = this.Q1.subtract(this.Q);
        Vector t2 = this.Q2.subtract(this.Q);


        this.setMaterial(mat);
        this.setEmission(emi);

        this.N = t1.crossProduct(t2);
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
        /*
        List<Point3d> intersection = new ArrayList<Point3d>();

       double t = (this.N.dotProduct(this.Q.subtract(P.getP0()))) / this.N.dotProduct(P.getDirection());


        Point3d p1 = new Point3d(P.getP0().add(P.getDirection().scalarMultiply(t)));
        intersection.add(p1);

        return intersection;
        */
        List<Point3d> intersectionPoints = new ArrayList<Point3d>();
        Point3d P0 = P.getP0();
        Point3d Q0 = this.getQ();
        Vector N = this.getNormal(null);
        Vector V = P.getDirection();

        if(N.dotProduct(V)==0)
        {
            // ray.getDirection() is parralel to the plane
            // no intersections points
            return intersectionPoints;
        }

        //Vector u = new Vector(new Point3d(Q0.getX().subtract(P0.getX()), Q0.getY().subtract(P0.getY()), Q0.getZ().subtract(P0.getZ())));
        Vector u = new Vector(Q.subtract(P0));
        double t = (u.dotProduct(N)) / N.dotProduct(V);

        if (t >= 0) {
            Vector Vs = V.scalarMultiply(t);
            Point3d p = P0.add(Vs);
            intersectionPoints.add(p);
        }
        return intersectionPoints;
    }


    //a plane is defined by at least 3 point
    private Point3d Q;
    private Point3d Q1;
    private Point3d Q2;
    private Vector N;

}
