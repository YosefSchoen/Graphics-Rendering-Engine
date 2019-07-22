package UnitTests.ElementsTests;

import Primitives.Coordinate;
import Primitives.Point3d;
import Primitives.Ray;
import Primitives.Vector;
import Elements.Camera;

import static org.junit.Assert.*;
import java.lang.System;

public class CameraTest {
    int Nx = 1000;
    int Ny = 1000;
    double x = 1.0;
    double y = 1.0;
    double screenDistance = 10.0;
    double screenWidth = 100.0;
    double screenHeight = 100.0;

    public Coordinate cX0 = new Coordinate(1);
    public Coordinate cY0 = new Coordinate(1);
    public Coordinate cZ0 = new Coordinate(0);

    public Coordinate cX1 = new Coordinate(2);
    public Coordinate cY1 = new Coordinate(1);
    public Coordinate cZ1 = new Coordinate(0);

    public Coordinate cX2 = new Coordinate(1);
    public Coordinate cY2 = new Coordinate(1);
    public Coordinate cZ2 = new Coordinate(2);

    Point3d p0 = new Point3d(cX0, cY0, cZ0);
    Point3d p1 = new Point3d(cX1, cY1, cZ1);
    Point3d p2 = new Point3d(cX2, cY2, cZ2);

    Vector vUp = new Vector(p1);
    Vector vT0 = new Vector(p2);

    Camera myCamera = new Camera(p0, vUp, vT0);
    @org.junit.Test
    public void constructRayThroughPixel() {
        Ray myRay = myCamera.constructRayThroughPixel(Nx, Ny, x, y, screenDistance, screenWidth, screenHeight);

        //testing the ray's staring point
        assertEquals("", 1, myRay.getP0().getX().getCoordinate(), 1e-10);
        assertEquals("", 1, myRay.getP0().getY().getCoordinate(), 1e-10);
        assertEquals("", 0, myRay.getP0().getZ().getCoordinate(), 1e-10);

        //testing the ray's vector's head point
        assertEquals("", 0.8018247240, myRay.getDirection().getHead().getX().getCoordinate(), 1e-10);
        assertEquals("", -0.5343583583, myRay.getDirection().getHead().getY().getCoordinate(), 1e-10);
        assertEquals("", 0.2674663656, myRay.getDirection().getHead().getZ().getCoordinate(), 1e-10);

        printTest(myRay);

    }

    void printTest(Ray P) {
        System.out.println("Starting Point");
        System.out.println("x = " + P.getP0().getX().getCoordinate());
        System.out.println("y = " + P.getP0().getY().getCoordinate());
        System.out.println("Z = " + P.getP0().getZ().getCoordinate());

        System.out.println("Vector Head");
        System.out.println("x = " + P.getDirection().getHead().getX().getCoordinate());
        System.out.println("y = " + P.getDirection().getHead().getY().getCoordinate());
        System.out.println("z = " + P.getDirection().getHead().getZ().getCoordinate());
    }
}