package GeometriesTests;

import Geometries.Plane;
import Geometries.Triangle;
import Primitives.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest {
    Color color = new Color(0, 0, 0);
    public Coordinate cX0 = new Coordinate(1);
    public Coordinate cY0 = new Coordinate(1);
    public Coordinate cZ0 = new Coordinate(-5);

    public Coordinate cX1 = new Coordinate(-2);
    public Coordinate cY1 = new Coordinate(2);
    public Coordinate cZ1 = new Coordinate(-5);

    public Coordinate cX2 = new Coordinate(0);
    public Coordinate cY2 = new Coordinate(-4);
    public Coordinate cZ2 = new Coordinate(-5);

    public Coordinate cX3 = new Coordinate(0);
    public Coordinate cY3 = new Coordinate(0);
    public Coordinate cZ3 = new Coordinate(0);

    public Coordinate cX4 = new Coordinate(0);
    public Coordinate cY4 = new Coordinate(0);
    public Coordinate cZ4 = new Coordinate(-1);

    public Point3d p0 = new Point3d(cX0, cY0, cZ0);
    public Point3d p1 = new Point3d(cX1, cY1, cZ1);
    public Point3d p2 = new Point3d(cX2, cY2, cZ2);
    public Point3d p3 = new Point3d(cX3, cY3, cZ3);
    public Point3d p4 = new Point3d(cX4, cY4, cZ4);

    public Triangle myTriangle = new Triangle(p0, p1, p2, new Material(), new Color(0, 0, 0));

    public Vector direction = new Vector(p4);
    public Ray myRay = new Ray(p3, direction);



    Triangle tr = new Triangle(
            new Point3d(0.0,3.0,0.0),
            new Point3d(-3.0,0.0,0.0),
            new Point3d(0.0,0.0,-1.0), new Material(), new Color(0, 0, 0)
    );
    @org.junit.Test
    public void normalTest(){
        Vector n = tr.getNormal(null);
        assertEquals("", 0.5773502691896258, n.getHead().getX().getCoordinate(), 1e-10);
        assertEquals("", 0.5773502691896258, n.getHead().getY().getCoordinate(), 1e-10);
        assertEquals("", 0.5773502691896258, n.getHead().getY().getCoordinate(), 1e-10);

        double  l = n.length();
        assertEquals("", 1.0, l, 1e-10);

        //assertEquals("Vector{head=Point3d{x=0.5773502691896258, y=0.5773502691896258, z=0.5773502691896258}}",n.toString());
    }


    @org.junit.Test
    public void findIntersections() {
        List<Point3d> myIntersections = new ArrayList<Point3d>();
        myIntersections = myTriangle.findIntersections(myRay);

        if(myIntersections.size() == 0){
            System.out.println("empty");
        }
        else {
            System.out.println("Not empty");
        }

        for (int i = 0; i < myIntersections.size(); i++) {
            assertEquals("", 0, myIntersections.get(i).getX().getCoordinate(), 1e-10);
            assertEquals("", 0, myIntersections.get(i).getY().getCoordinate(), 1e-10);
            assertEquals("", -5, myIntersections.get(i).getZ().getCoordinate(), 1e-10);
        }

    }
    
    @org.junit.Test
    public void FI2(){
        //Testing a ray hitting the Triangle orthogonally
        Point3d p1 = new Point3d(1, 1, -5);
        Point3d p2 = new Point3d(-2, 2, -5);
        Point3d p3 = new Point3d(0, -4, -5);

        Color emission = new Color(0,0,0);
        //set test triangle with these points, color, and material
        Triangle testTriangle = new Triangle(p1, p2, p3, new Material(), new Color(0, 0, 0));

        //now construct the ray and its direction which will be tested
        Point3d source = new Point3d();
        Vector direction = new Vector(0,0,-1);
        Ray testRay = new Ray(source, direction);
        //set expectedValue of the intersection point and compare, if the test fails throw the failure
        Point3d expectedValue = new Point3d(0,0,-5);
        assertTrue("failed when triangle was sitting directly in front of camera", (testTriangle.findIntersections(testRay).get(0).compareTo(expectedValue) == 1));

        //Testing the ray missing Triangle entirely (Triangle is off to the side, but plane is in front)

        testRay.setDirection(new Vector(4, -2, -1));
        assertTrue("Failed under ray missing Triangle entirely (Triangle is off to the side, but plane is in front)",testTriangle.findIntersections(testRay).isEmpty());

        //Testing the ray missing the Triangle (Triangle is parallel to ray)
        testRay.setDirection(new Vector(0, 0, -1));
        p1 = new Point3d(-1, 0, -4);
        p2 = new Point3d(3, 0, -5);
        p3 = new Point3d(0, 0, 0);
        testTriangle = new Triangle(p1, p2, p3, new Material(), new Color(0, 0, 0));
        assertTrue("Failed under ray missing Triangle entirely (Triangle is parallel to ray)",testTriangle.findIntersections(testRay).isEmpty());

        //Testing the ray missing the Triangle (Triangle is behind ray)
        p1 = new Point3d(1, 1, 5);
        p2 = new Point3d(-2, 2, 5);
        p3 = new Point3d(-3, -3, 5);
        testTriangle = new Triangle(p1, p2, p3, new Material(), new Color(0, 0, 0));
        assertTrue("Failed under ray missing Triangle entirely (Triangle is behind ray)",testTriangle.findIntersections(testRay).isEmpty());

    }
}