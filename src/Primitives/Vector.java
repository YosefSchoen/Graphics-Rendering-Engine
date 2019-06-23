package Primitives;
import java.lang.Math;

//a Vector class
public class Vector {

    //empty constructor
    public Vector() {
        this.head = new Point3d();
    }

    //constructor
    public Vector(Point3d head) {
        this.head = head;
    }



    //i don't know if this is what rendered wants?
    public Vector(double xVal, double yVal, double zVal) {
        head = new Point3d(xVal, yVal, zVal);
    }

    //copy constructor
    public Vector(Vector other) {
        this.head = other.head;
    }

    //getter
    public Point3d getHead() {
        return head;
    }

    //setter
    public void setHead(Point3d head) {
        this.head = head;
    }

    //compares the heads of two vectors to see if they are the same vector
    public int compareTo(Vector other) {
        if (this.head.compareTo(other.head) == 0) {
            return 0;
        }

        return 1;
    }

    //add method to add two vectors
    public Vector add(Vector other) {
        Vector newVector = new Vector(this.head.add(other));
        return newVector;
    }

    //subtract method to subtract 2 vectors
    public Vector subtract(Vector other) {
        Vector newVector = new Vector(this.head.subtract(other.getHead()));
        return newVector;
    }

    //method to multiply the vector by a scalar
    public Vector scalarMultiply(Double other) {
        Coordinate Scalar = new Coordinate(other);
        Point3d pScalar = new Point3d(Scalar, Scalar, Scalar);
        Vector scaledVector = new Vector(this.head.multiply(pScalar));
        return scaledVector;
    }

    //method to divide the vector by a scalar
    public Vector scalarDivide(double other) {
        Coordinate Scalar = new Coordinate(other);
        Point3d pScalar = new Point3d(Scalar, Scalar, Scalar);
        Vector scaledVector = new Vector(this.head.divide(pScalar));
        return scaledVector;
    }

    //method to find the the vector which is the cross product of two vectors
    public Vector crossProduct(Vector other) {
        //equation for cross product
        Coordinate xValue = this.head.getY().multiply(other.head.getZ()).subtract(this.head.getZ().multiply(other.head.getY()));
        Coordinate yValue = this.head.getZ().multiply(other.head.getX()).subtract(this.head.getX().multiply(other.head.getZ()));
        Coordinate zValue = this.head.getX().multiply(other.head.getY()).subtract(this.head.getY().multiply(other.head.getX()));

        Point3d newHead = new Point3d(xValue, yValue, zValue);
        Vector newVector = new Vector(newHead);
        return newVector;
    }

    //method to find the magnitude(length) of a vector
    public double length() {
        double xValue = this.head.getX().getCoordinate();
        double yValue = this.head.getY().getCoordinate();
        double zValue = this.head.getZ().getCoordinate();

        //distance formula
        xValue = Math.pow(xValue, 2);
        yValue = Math.pow(yValue, 2);
        zValue = Math.pow(zValue, 2);

        return Math.sqrt(xValue + yValue + zValue);
    }

    //method to make the length 1 and keep the direction of the vector the same
    public Vector normalize() {
        // Avoid divide by zero
        if(this.length() != 0) {
            Coordinate scalar = new Coordinate(this.length());
            Point3d p = new Point3d(scalar, scalar, scalar);
            //this.head = this.head.divide(p);
            Vector p1 = new Vector(this.head.divide(p));
            return new Vector(p1);
        }
        // If the vector has no length, then just make it in to this
        else {
            throw new IllegalArgumentException("Cannot normalize the zero vector. (Divide by zero Error)");
        }
    }

    //method to find the scalar that is the dot product of two vectors
    public double dotProduct(Vector other) {
        Coordinate xValue = this.head.getX().multiply(other.getHead().getX());
        Coordinate yValue = this.head.getY().multiply(other.getHead().getY());
        Coordinate zValue = this.head.getZ().multiply(other.getHead().getZ());

        Coordinate newCoordinate = new Coordinate(xValue.add(yValue.add(zValue)));
        return newCoordinate.getCoordinate();

    }

    //starting point of a vector
    private Point3d head;
}
