package Primitives;

//Point class for an x y plane, has 2 coordinates
public class Point2d {

    //empty constructor makes to new empty coordinates
    public Point2d() {
        this.x = new Coordinate();
        this.y = new Coordinate();
    }

    //constructor
    public Point2d(Coordinate x, Coordinate y) {
        this.x = x;
        this.y = y;
    }

    public Point2d(double xVal, double yVal) {
        this.x = new Coordinate(xVal);
        this.y = new Coordinate(yVal);
    }

    //copy constructor
    public Point2d(Point2d other) {
        this.x = other.x;
        this.y = other.y;
    }

    //getters
    public Coordinate getX() {
        return x;
    }

    public Coordinate getY() {
        return y;
    }

    //setters
    public void setX(Coordinate x) {
        this.x = x;
    }

    public void setY(Coordinate y) {
        this.y = y;
    }

    //compares two Points2ds to see if they are equal
    public int compareTo(Point2d other) {
        //calls the compareTo method of the points x and y coordinates
        if (this.x.compareTo(other.x) == 0) {
            return 0;
        }
        if(this.y.compareTo(other.y) == 0) {
            return 0;
        }

        return 1;
    }

    //method to add the values of two Point 2ds together
    public Point2d add(Point2d other) {
        //calls the x and y coordinates add methods
        Point2d newPoint2d = new Point2d(this.x.add(other.x), this.y.add(other.y));
        return newPoint2d;
    }

    //method to subtracts the values of two Point 2ds together
    public Point2d subtract(Point2d other) {
        //calls the x and y coordinates subtract methods
        Point2d newPoint2d = new Point2d(this.x.subtract(other.x), this.y.subtract(other.y));
        return newPoint2d;
    }

    //method to multiplies the values of two Point 2ds together
    public Point2d multiply(Point2d other) {
        //calls the x and y coordinates multiply methods
        Point2d newPoint2d = new Point2d(this.x.multiply(other.x), this.y.multiply(other.y));
        return newPoint2d;
    }

    //method to divides the values of two Point 2ds together
    public Point2d divide(Point2d other) {
        //calls the x and y coordinates divide methods
        Point2d newPoint2d = new Point2d(this.x.divide(other.x),  this.y.divide(other.y));
        return newPoint2d;
    }





    //2 coordinates which make a 2d point
    private Coordinate x;
    private Coordinate y;
}
