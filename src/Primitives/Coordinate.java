package Primitives;

//A Coordinate class which has a number value
public class Coordinate {
    //empty constructor
    public Coordinate() {
        this.coordinate = 0.0;
    }

    //constructor
    public Coordinate(double coordinate) {
        this.coordinate = coordinate;
    }

    //copy constructor
    public Coordinate(Coordinate other) {
        this.coordinate = other.coordinate;
    }

    //getter
    public double getCoordinate() {
        return coordinate;
    }

    //setter
    public void setCoordinate(double coordinate) {
        this.coordinate = coordinate;
    }

    //compares to Coordinates to see if they have the same value
    public int compareTo(Coordinate other) {
        if (other.getCoordinate() == this.getCoordinate()) {
            return 1;
        }

        return 0;
    }

    //method to add the values of two Coordinates together
    public Coordinate add(Coordinate other) {
        Coordinate newCoordinate = new Coordinate(this.coordinate + other.coordinate);
        return newCoordinate;
    }

    //method to subtract the values of two Coordinates together
    public Coordinate subtract(Coordinate other) {
        Coordinate newCoordinate = new Coordinate(this.coordinate - other.coordinate);
        return newCoordinate;
    }

    //method to multiply the values of two Coordinates together
    public Coordinate multiply(Coordinate other) {
        Coordinate newCoordinate = new Coordinate(this.coordinate * other.coordinate);
        return newCoordinate;
    }

    //method to divide the values of two Coordinates together
    public Coordinate divide(Coordinate other) {

        //if divides by zero throw exception
        if(other.coordinate == 0) {
            throw new ArithmeticException("can't divide by zero");
        }
        Coordinate newCoordinate = new Coordinate(this.coordinate / other.coordinate);
        return newCoordinate;
    }

    @Override
    public String toString() {
        return "" + coordinate;
    }

    //value of the coordinate
    private double coordinate;
}
