package UnitTests.GeometriesTests;

import Primitives.Coordinate;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;
import Geometries.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


public class SphereTest {
    Color color = new Color(0, 0, 0);
    public Coordinate cX0 = new Coordinate(0);
    public Coordinate cY0 = new Coordinate(0);
    public Coordinate cZ0 = new Coordinate(0);

    public Coordinate cX1 = new Coordinate(100);
    public Coordinate cY1 = new Coordinate(80);
    public Coordinate cZ1 = new Coordinate(150);

    public Coordinate cX2 = new Coordinate(200);
    public Coordinate cY2 = new Coordinate(50);
    public Coordinate cZ2 = new Coordinate(0);

    public Point3d p0 = new Point3d(cX0, cY0, cZ0);
    public Point3d p1 = new Point3d(cX1, cY1, cZ1);
    public Point3d p2 = new Point3d(cX2, cY2, cZ2);

    public Vector direction = new Vector(p1);
    public Ray myRay = new Ray(p0, direction);
    public Sphere mySphere = new Sphere(180, p2);


    @org.junit.Test
    public void findIntersections() {
        List<Point3d> myIntersections = new ArrayList<Point3d>();
        myIntersections = mySphere.findIntersections(myRay);

        System.out.println(myIntersections.size());

        if (myIntersections.size() > 0) {
            //assertEquals("", 1.7071067812, myIntersections.get(0).getX().getCoordinate(), 1e-10);
            //assertEquals("", 1.7071067812, myIntersections.get(0).getY().getCoordinate(), 1e-10);
            //assertEquals("", 0, myIntersections.get(0).getZ().getCoordinate(), 1e-10);

            System.out.println("intersection 1");
            System.out.println("x1 = " + myIntersections.get(0).getX().getCoordinate());
            System.out.println("y1 = " + myIntersections.get(0).getY().getCoordinate());
            System.out.println("z1 = " + myIntersections.get(0).getZ().getCoordinate());

        }

        if (myIntersections.size() > 1) {
            //assertEquals("", 0.2928932188 , myIntersections.get(1).getX().getCoordinate(), 1e-10);
            //assertEquals("", 0.2928932188 , myIntersections.get(1).getY().getCoordinate(), 1e-10);
            //assertEquals("", 0, myIntersections.get(1).getZ().getCoordinate(), 1e-10);

            System.out.println("intersection 2");
            System.out.println("x2 = " + myIntersections.get(1).getX().getCoordinate());
            System.out.println("y2 = " + myIntersections.get(1).getY().getCoordinate());
            System.out.println("z2 = " + myIntersections.get(1).getZ().getCoordinate());


            double a = myIntersections.get(1).getX().getCoordinate();
            double b = myIntersections.get(1).getY().getCoordinate();
            double c = myIntersections.get(1).getZ().getCoordinate();
            double sum = a + b + c;
            System.out.println("sum = " + sum);
        }
        print(myIntersections);

    }

    public void print(List<Point3d> myIntersections) {
        for (int i = 0; i < myIntersections.size(); i++) {
            System.out.println("Point " + i + 1);
            System.out.println("x val = " + myIntersections.get(i).getX().getCoordinate());
            System.out.println("y val = " + myIntersections.get(i).getY().getCoordinate());
            System.out.println("z val = " + myIntersections.get(i).getZ().getCoordinate());
        }
    }
}


/*

*/