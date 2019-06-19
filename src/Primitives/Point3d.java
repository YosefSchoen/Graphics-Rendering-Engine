package Primitives;


//an extension of a 2d point by adding a third coordinate
public class Point3d extends Point2d{

    //public final Point3d ZERO = new Point3d(new Coordinate(0), new Coordinate(0), new Coordinate(0));

    //empty constructor calls empty constructor for its 2d part and makes a new 3d coordinate
    public Point3d() {
        super();
        this.z = new Coordinate();
    }

    Point3d(Point2d xy, Coordinate z) {
        super(xy.getX(), xy.getY());
        this.z = z;
    }

    //constructor calls a 2d constructor for the 2d part and initializes the 3d coordinate
    public Point3d(Coordinate x, Coordinate y, Coordinate z) {
        super(x, y);
        this.z = z;
    }

    public Point3d(double xVal, double yVal, double zVal) {
        super(xVal, yVal);
        this.z = new Coordinate(zVal);
    }

    //copy constructor copies the 2d part and then the z coordinate
    public Point3d(Point3d other) {
        super(other);
        this.z = other.z;
    }

    //getter
    public Coordinate getZ() {
        return z;
    }

    //setter
    public void setZ(Coordinate z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            return (super.equals(obj) && ((Point3d)obj).z == this.z);
        }

        return false;
    }

    //method to compare two Point 3ds
    public int compareTo(Point3d other) {
        //calls the compareTo method of the Point 2d parent (x and y coordinates)
        if (super.compareTo(other) == 0) {
            return 0;
        }

        //calls the z coordinates compareTo method
        if (this.z.compareTo(other.z) == 0) {
            return 0;
        }

        return 1;
    }

    //method to add the values of two Point 3ds together
    public Point3d add(Vector other) {
        //calls the Point 2d add method on the x and y then the add method on the z coordinate
        Point3d newPoint3d = new Point3d(super.add(other.getHead()), this.z.add(other.getHead().getZ()));
        return newPoint3d;
    }

    //method to subtract the values of two Point 3ds together
    public Vector subtract(Point3d other) {
        //calls the Point 2d subtract method on the x and y then the subtract method on the z coordinate
        Point3d newPoint3d = new Point3d(super.subtract(other), this.z.subtract(other.getZ()));
        Vector newVector = new Vector(newPoint3d);
        return newVector;
    }

    //method to multiplies the values of two Point 3ds together
    public Point3d multiply(Point3d other) {
        //calls the Point 2d multiply method on the x and y then the multiply method on the z coordinate
        Point3d newPoint3d = new Point3d(super.multiply(other), this.z.multiply(other.getZ()));
        return newPoint3d;
    }

    //method to divide the values of two Point 3ds together
    public Point3d divide(Point3d other) {
        //calls the Point 2d divide method on the x and y then the divide method on the z coordinate
        Point3d newPoint3d = new Point3d(super.divide(other), this.z.divide(other.getZ()));
        return newPoint3d;
    }


    //method to calculate the distance between two points
    public double distance(Point3d point) {
        double xVal = this.getX().getCoordinate() - point.getX().getCoordinate();
        double yVal = this.getY().getCoordinate() - point.getY().getCoordinate();
        double zVal = this.getZ().getCoordinate() - point.getZ().getCoordinate();

        return Math.sqrt(Math.pow(xVal, 2) + Math.pow(yVal, 2) + Math.pow(zVal, 2));
    }

    //third coordinate for a 3d point
    private Coordinate z;
}
