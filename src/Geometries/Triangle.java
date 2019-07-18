package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


//Triangle class
public class Triangle extends Polygon implements FlatGeometry{
    //empty constructor
    public Triangle() {
        super(3);
        this.p1 = new Point3d();
        this.p2 = new Point3d();
        this.p3 = new Point3d();
    }

    //constructor
    public Triangle(Point3d p1, Point3d p2, Point3d p3) {
        super(3);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    //alternative constructor
    public Triangle(Point3d p1, Point3d p2, Point3d p3,Material material, Color color) {
        super(3, material, color);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    //copy constructor
    public Triangle(Triangle other) {
        this.p1 = other.p1;
        this.p2 = other.p2;
        this.p3 = other.p3;

        this.setMaterial(other.getMaterial());
        this.setEmission(other.getEmission());
    }

    //getters
    public Point3d getP1() {
        return p1;
    }

    public Point3d getP2() {
        return p2;
    }

    public Point3d getP3() {
        return p3;
    }

    public Vector getNormal(Point3d P) {
        Vector V1 = this.p2.subtract(this.p1);
        Vector V2 = this.p3.subtract(this.p1);
        Vector N = V1.crossProduct(V2);
        N = N.normalize();
        return new Vector(N);
    }

    //setters
    public void setP1(Point3d p1) {
        this.p1 = p1;
    }

    public void setP2(Point3d p2) {
        this.p2 = p2;
    }

    public void setP3(Point3d p3) {
        this.p3 = p3;
    }





    //method to find the intersection points of a Ray and a Triangle
    public List<Point3d> findIntersections(Ray P) {
        /*
        //the Points will be stored in a List
        List<Point3d> intersection = new ArrayList<Point3d>();

        //N is the perpendicular vector to the triangle and its plane
        Vector N = getNormal(P.getP0());

        //the plane the triangle is embedded in is any point of the triangle and its normal
        Plane trianglesPlane = new Plane(this.p1, N);

        //V1, V2, V3 are vectors from the vertices of the triangle to the ray
        Vector V1 = new Vector(P.getP0().subtract(this.p1));
        Vector V2 = new Vector(P.getP0().subtract(this.p2));
        Vector V3 = new Vector(P.getP0().subtract(this.p3));

        //normals of V1, V2, V3
        Vector N1 = new Vector(V1.crossProduct(V2));
        N1.normalize();

        Vector N2 = new Vector(V2.crossProduct(V3));
        N2.normalize();

        Vector N3 = new Vector(V3.crossProduct(V1));
        N3.normalize();

        double sign1 = N1.dotProduct(this.p1.subtract(P.getP0()));
        double sign2 = N2.dotProduct(this.p2.subtract(P.getP0()));
        double sign3 = N3.dotProduct(this.p3.subtract(P.getP0()));
        //System.out.println("" + sign1 + " " + sign2 + " " + sign3 + " \t " + N1.length() + " " + N2.length() + " " + N3.length());

        if (sign1 < 0 && sign2 < 0 && sign3 < 0) {
            intersection = trianglesPlane.findIntersections(P);
        }

        else if(sign1 > 0 && sign2 > 0 && sign3 > 0) {
            intersection = trianglesPlane.findIntersections(P);
        }

        return intersection;
        */

        //Initializes an empty list that will contain the point(s) of intersection, if any
        List<Point3d> listToReturn = new ArrayList<>();

        //Creates a plane representing the plane that the triangle lives in
        Plane planeEncompassingTriangle = new Plane(this.p1, this.p2, this.p3);
        //Finds the point that the ray would intersect into that plane at
        List<Point3d> listOfPotentialPoints = planeEncompassingTriangle.findIntersections(P);
        //if it missed the plane entirely, it has no chance of hitting the triangle and we return an empty list
        if(listOfPotentialPoints.isEmpty()) {
            return listToReturn;
        }else { //otherwise we check the point using the formula given in class to see if it's in the triangle
            Point3d potentialPoint = listOfPotentialPoints.get(0);

            //Side 1
            Vector v11 = new Vector(this.p1);
            Vector v12 = new Vector(this.p2);
            Vector n1 = v11.crossProduct(v12).scalarMultiply(1/(v11.crossProduct(v12).length()));

            double sign1 = Math.signum(((new Vector(potentialPoint)).subtract(new Vector()).dotProduct(n1)));

            //Side 2
            Vector v21 = new Vector(this.p2);
            Vector v22 = new Vector(this.p3);
            Vector n2 = v21.crossProduct(v22).scalarMultiply(1/(v21.crossProduct(v22).length()));

            double sign2 = Math.signum(((new Vector(potentialPoint)).subtract(new Vector()).dotProduct(n2)));

            //Side 3
            Vector v31 = new Vector(this.p3);
            Vector v32 = new Vector(this.p1);
            Vector n3 = v31.crossProduct(v32).scalarMultiply(1/(v31.crossProduct(v32).length()));

            double sign3 = Math.signum(((new Vector(potentialPoint)).subtract(new Vector()).dotProduct(n3)));

            //if all the signs are equal, the point is in the triangle, add it to the list
            if(sign1 == sign2 && sign2 == sign3) {
                listToReturn.add(potentialPoint);
            }

            //And return the list, empty or otherwise
            return listToReturn;
        }
    }

    //a triangle is defined by at least 3 point
    private Point3d p1;
    private Point3d p2;
    private Point3d p3;
}



