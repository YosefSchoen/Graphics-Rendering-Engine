package unittests;

//These classes are needed to test vectors
import Primitives.Coordinate;
import Primitives.Point3d;
import Primitives.Vector;

//J unit tester using assert equals
import static org.junit.Assert.*;
import java.lang.System;

//210.4, -138.55, 69.85
//2, 1, 0

public class VectorTest {
    //three Coordinates for Point p0
    public Coordinate cX0 = new Coordinate(-1);
    public Coordinate cY0 = new Coordinate(-8);
    public Coordinate cZ0 = new Coordinate(49);

    //three Coordinates for Point p1
    public Coordinate cX1 = new Coordinate(-1);
    public Coordinate cY1 = new Coordinate(-6);
    public Coordinate cZ1 = new Coordinate(-1);

    //Points p0 and p1 will be the heads of our test vectors v0, v1
    public Point3d p0 = new Point3d(cX0, cY0, cZ0);
    public Point3d p1 = new Point3d(cX1, cY1, cZ1);

    //test for the compareTo operator
    @org.junit.Test
    public void compareTo() {
        System.out.println("CompareTo Test");

        //making to vectors which have different heads
        Vector v0 = new Vector(p0);
        Vector v1 = new Vector(p1);

        printTests("v0", v0);
        printTests("v1", v1);

        //testing to see that 0 is returned because v0 does not equal v1
        assertEquals("", 0, v0.compareTo(v1), 1e-10);
        System.out.println(" v0 != v1: " + v0.compareTo(v1));

        //setting v1's head to be v0
        v1.setHead(p0);
        printTests("new v1", v1);

        //testing to see that 1 is returned because v0 should equal v1
        assertEquals("", 1, v0.compareTo(v1), 1e-10);
        System.out.println(" v0 == v1: " + v0.compareTo(v1));


    }

    //test for the add operator
    @org.junit.Test
    public void add() {
        System.out.println("Add Test");

        Vector v0 = new Vector(p0);
        Vector v1 = new Vector(p1);

        //creating double of the 3 coordinates of the vector's heads added together
        double xValue = p0.getX().getCoordinate() + p1.getX().getCoordinate();
        double yValue = p0.getY().getCoordinate() + p1.getY().getCoordinate();
        double zValue = p0.getZ().getCoordinate() + p1.getZ().getCoordinate();

        //checking if the add method gives the same value for each
        assertEquals("", xValue, v0.add(v1).getHead().getX().getCoordinate(),1e-10);
        assertEquals("", yValue, v0.add(v1).getHead().getY().getCoordinate(),1e-10);
        assertEquals("", zValue, v0.add(v1).getHead().getZ().getCoordinate(),1e-10);

        printTests("v0", v0);
        printTests("v1", v1);
        printTests("v0 + v1", v0.add(v1));
    }

    //this test is the same as add but for subtraction
    @org.junit.Test
    public void subtract() {
        System.out.println("Subtract Test");

        Vector v0 = new Vector(p0);
        Vector v1 = new Vector(p1);

        double xValue = p0.getX().getCoordinate() - p1.getX().getCoordinate();
        double yValue = p0.getY().getCoordinate() - p1.getY().getCoordinate();
        double zValue = p0.getZ().getCoordinate() - p1.getZ().getCoordinate();

        assertEquals("", xValue, v0.subtract(v1).getHead().getX().getCoordinate(),1e-10);
        assertEquals("", yValue, v0.subtract(v1).getHead().getY().getCoordinate(),1e-10);
        assertEquals("", zValue, v0.subtract(v1).getHead().getZ().getCoordinate(),1e-10);

        printTests("v0", v0);
        printTests("v1", v1);
        printTests("v0 - v1", v0.subtract(v1));
    }

    //testing for multiplying by a scalar
    @org.junit.Test
    public void scalarMultiply() {
        System.out.println("Multiply Test");

        Vector v0 = new Vector(p0);

        //c is our scalar
        double c = 6.0;

        //creating a double of each coordinates scaled by c
        double xScaled = p0.getX().getCoordinate() * c;
        double yScaled = p0.getY().getCoordinate() * c;
        double zScaled = p0.getZ().getCoordinate() * c;

        //testing if these doubles equal to the scalarMultiplication method
        assertEquals("", xScaled, v0.scalarMultiply(c).getHead().getX().getCoordinate(),1e-10);
        assertEquals("", yScaled, v0.scalarMultiply(c).getHead().getY().getCoordinate(),1e-10);
        assertEquals("", zScaled, v0.scalarMultiply(c).getHead().getZ().getCoordinate(),1e-10);

        printTests("v0", v0);

        System.out.println();
        printTests( c + " * v0", v0.scalarMultiply(c));
    }

    //this test is the same as as multiplying but for division
    @org.junit.Test
    public void scalarDivide() {
        System.out.println("Divide Test");

        Vector v0 = new Vector(p0);

        double c = 6.0;
        double xScaled = p0.getX().getCoordinate() / c;
        double yScaled = p0.getY().getCoordinate() / c;
        double zScaled = p0.getZ().getCoordinate() / c;

        assertEquals("", xScaled, v0.scalarDivide(c).getHead().getX().getCoordinate(),1e-10);
        assertEquals("", yScaled, v0.scalarDivide(c).getHead().getY().getCoordinate(),1e-10);
        assertEquals("", zScaled, v0.scalarDivide(c).getHead().getZ().getCoordinate(),1e-10);

        printTests("v0", v0);
        printTests("v0 / " + c, v0.scalarDivide(6.0));
    }

    //testing the cross product of two vectors
    @org.junit.Test
    public void crossProduct() {
        System.out.println("Cross Product Test");

        Vector v0 = new Vector(p0);
        Vector v1 = new Vector(p1);

        //creating doubles for each new coordinate of the crossed vectors
        double xCross = (p0.getY().getCoordinate() * p1.getZ().getCoordinate()) - (p0.getZ().getCoordinate() * p1.getY().getCoordinate());
        double yCross = (p0.getZ().getCoordinate() * p1.getX().getCoordinate()) - (p0.getX().getCoordinate() * p1.getZ().getCoordinate());
        double zCross = (p0.getX().getCoordinate() * p1.getY().getCoordinate()) - (p0.getY().getCoordinate() * p1.getX().getCoordinate());

        //testing if they are equal to the crossProduct method
        assertEquals("", xCross, v0.crossProduct(v1).getHead().getX().getCoordinate(),1e-10);
        assertEquals("", yCross, v0.crossProduct(v1).getHead().getY().getCoordinate(),1e-10);
        assertEquals("", zCross, v0.crossProduct(v1).getHead().getZ().getCoordinate(),1e-10);

        printTests("v0", v0);
        printTests("v1", v1);
        printTests("v0 x v1", v0.crossProduct(v1));
    }

    //test to find the length of a vector
    @org.junit.Test
    public void length() {
        System.out.println("Length Test");

        Vector v0 = new Vector(p0);

        double xSquared = Math.pow(p0.getX().getCoordinate(), 2);
        double ySquared = Math.pow(p0.getY().getCoordinate(), 2);
        double zSquared =  Math.pow(p0.getZ().getCoordinate(), 2);
        double length = Math.sqrt(xSquared + ySquared + zSquared);

        assertEquals("", length, v0.length(),1e-10);

        printTests("v0", v0);
        System.out.println("|v0| = " + v0.length());
    }

    @org.junit.Test
    public void normalize() {
        System.out.println("Normalize Test");

        Vector v0 = new Vector(p0);
        v0.normalize();

        assertEquals("", 1, v0.length(),1e-10);

        printTests("v0", v0);
        System.out.println("|v0| = " + v0.length());

        v0 = new Vector();

        try {
            v0.normalize();
            fail("Didn't throw divide by zero exception!");
        }

        catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void dotProduct() {
        System.out.println("Dot Product Test");

        Vector v0 = new Vector(p0);
        Vector v1 = new Vector(p1);

        double xDot = p0.getX().getCoordinate() * p1.getX().getCoordinate();
        double yDot = p0.getY().getCoordinate() * p1.getY().getCoordinate();
        double zDot = p0.getZ().getCoordinate() * p1.getZ().getCoordinate();
        double dotProduct = xDot + yDot + zDot;

        assertEquals("", dotProduct, v0.dotProduct(v1), 1e-10);

        printTests("v0", v0);
        printTests("v1", v1);
        System.out.println("v0 * v1 = " + v0.dotProduct(v1));
    }

    public void printTests(String str,Vector v){
        System.out.println(str);
        System.out.println(" x = " + v.getHead().getX().getCoordinate());
        System.out.println(" y = " + v.getHead().getY().getCoordinate());
        System.out.println(" z = " + v.getHead().getZ().getCoordinate());
    }
}
