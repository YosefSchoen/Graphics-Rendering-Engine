package UnitTests.GeometriesTests;

import Geometries.Plane;
import Primitives.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PlaneTest {
    Color color = new Color(0, 0, 0);

    public Coordinate cX0 = new Coordinate(15);
    public Coordinate cY0 = new Coordinate(0);
    public Coordinate cZ0 = new Coordinate(0);

    public Coordinate cX1 = new Coordinate(0);
    public Coordinate cY1 = new Coordinate(11.25);
    public Coordinate cZ1 = new Coordinate(0);


    public Coordinate cX2 = new Coordinate(0);
    public Coordinate cY2 = new Coordinate(0);
    public Coordinate cZ2 = new Coordinate(-22.5);

    public Coordinate cX3 = new Coordinate(0);
    public Coordinate cY3 = new Coordinate(0);
    public Coordinate cZ3 = new Coordinate(0);

    public Coordinate cX4 = new Coordinate(0);
    public Coordinate cY4 = new Coordinate(0);
    public Coordinate cZ4 = new Coordinate(-4);

    public Point3d p0 = new Point3d(cX0, cY0, cZ0);
    public Point3d p1 = new Point3d(cX1, cY1, cZ1);
    public Point3d p2 = new Point3d(cX2, cY2, cZ2);
    public Point3d p3 = new Point3d(cX3, cY3, cZ3);
    public Point3d p4 = new Point3d(cX4, cY4, cZ4);

    public Plane myPlane = new Plane(p0, p1, p2, new Material(), new Color(0, 0, 0));

    public Vector direction = new Vector(p4);
    public Ray myRay = new Ray(p3, direction);
    
    @org.junit.Test
    public void intTest2() {
        // creating the expected values

        List<Point3d> answerList = new ArrayList<Point3d>();
        Point3d answerPoint = new Point3d(0, 0, -200);
        answerList.add(answerPoint);

        // building the plane

        Point3d directionPoint = new Point3d(0, 0, -1);
        Point3d planePoint = new Point3d(0, 100, -200);

        Vector direction = new Vector(directionPoint);

        Plane plane = new Plane(planePoint, direction, new Material(), new Color(0, 0, 0));

        // building the ray that will intersect the plane

        Point3d centerPoint = new Point3d(0,0,0);
        Vector vector = new Vector(0, 0, -5);
        Ray ray = new Ray(centerPoint, vector);

        // testing the findIntersection function

        List<Point3d> list = new ArrayList<Point3d>();
        list = plane.findIntersections(ray);

        for (int i = 0; i < list.size() && i < answerList.size(); i++){
            assertEquals("point " + i + " ne", answerList.get(i).getX().getCoordinate(), list.get(i).getX().getCoordinate(), 1e-10);
            assertEquals("point " + i + " ne", answerList.get(i).getY().getCoordinate(), list.get(i).getY().getCoordinate(), 1e-10);
            assertEquals("point " + i + " ne", answerList.get(i).getZ().getCoordinate(), list.get(i).getZ().getCoordinate(), 1e-10);
        }
        assertEquals(answerList.size(), list.size());
    }

    @org.junit.Test
    public void findIntersections() {
        List<Point3d> myIntersections = new ArrayList<Point3d>();

        myIntersections = myPlane.findIntersections(myRay);

        if (myIntersections.size() == 0) {
            System.out.println("empty");
        }
        else {
            System.out.println("not empty");
        }
        for (int i = 0; i < myIntersections.size(); i++) {
            //assertEquals("", -1, myIntersections.get(i).getX().getCoordinate(), 1e-10);
            //assertEquals("", 1, myIntersections.get(i).getY().getCoordinate(), 1e-10);
            //assertEquals("", 1, myIntersections.get(i).getZ().getCoordinate(), 1e-10);
        }

        print(myIntersections);
    }

    public void print(List<Point3d> myIntersections) {
        System.out.println("X = " + myIntersections.get(0).getX().getCoordinate());
        System.out.println("Y = " + myIntersections.get(0).getY().getCoordinate());
        System.out.println("Z = " + myIntersections.get(0).getZ().getCoordinate());
    }
}