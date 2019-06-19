package unittests.GeometriesTests;

import Geometries.Plane;
import Geometries.Triangle;
import Primitives.Coordinate;
import Primitives.Point3d;
import Primitives.Ray;
import Primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest {
    Color color = new Color(0, 0, 0);
    public Coordinate cX0 = new Coordinate(5);
    public Coordinate cY0 = new Coordinate(2);
    public Coordinate cZ0 = new Coordinate(5);

    public Coordinate cX1 = new Coordinate(-3);
    public Coordinate cY1 = new Coordinate(3);
    public Coordinate cZ1 = new Coordinate(5);

    public Coordinate cX2 = new Coordinate(4);
    public Coordinate cY2 = new Coordinate(-4);
    public Coordinate cZ2 = new Coordinate(4);

    public Coordinate cX3 = new Coordinate(0);
    public Coordinate cY3 = new Coordinate(0);
    public Coordinate cZ3 = new Coordinate(0);

    public Coordinate cX4 = new Coordinate(0.9);
    public Coordinate cY4 = new Coordinate(1.9);
    public Coordinate cZ4 = new Coordinate(4.9);

    public Point3d p0 = new Point3d(cX0, cY0, cZ0);
    public Point3d p1 = new Point3d(cX1, cY1, cZ1);
    public Point3d p2 = new Point3d(cX2, cY2, cZ2);
    public Point3d p3 = new Point3d(cX3, cY3, cZ3);
    public Point3d p4 = new Point3d(cX4, cY4, cZ4);

    public Triangle myTriangle = new Triangle(p0, p1, p2);

    public Vector direction = new Vector(p4);
    public Ray myRay = new Ray(p3, direction);
    @org.junit.Test
    public void findIntersections() {
        List<Point3d> myIntersections = new ArrayList<Point3d>();
        myIntersections = myTriangle.findIntersections(myRay);


        for (int i = 0; i < myIntersections.size(); i++) {
            assertEquals("", -1, myIntersections.get(i).getX().getCoordinate(), 1e-10);
            assertEquals("", 1, myIntersections.get(i).getY().getCoordinate(), 1e-10);
            assertEquals("", 1, myIntersections.get(i).getZ().getCoordinate(), 1e-10);
        }

    }
}