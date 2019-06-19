package Primitives;

//a Ray class for an line with a starting point
public class Ray {

    //empty constructor
    public Ray() {
        this.p0 = new Point3d();
        this .direction = new Vector();
    }

    //constructor
    public Ray(Point3d p0, Vector direction) {
        this.p0 = p0;
        this.direction = direction;
    }

    //copy constructor
    public Ray(Ray other) {
        this.p0 = other.p0;
        this.direction = other.direction;
    }

    //getters
    public Point3d getP0() {
        return p0;
    }

    public Vector getDirection() {
        return direction;
    }

    //setters
    public void setP0(Point3d p0) {
        this.p0 = p0;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    //method to see if two Rays are equal
    public int compareTo(Ray other) {
        //comparing their points
        if (this.p0.compareTo(other.p0) == 0) {
            return 0;
        }

        //comparing their Vectors
        if (this.direction.compareTo(other.direction) == 0) {
            return 0;
        }

        return 1;
    }

    //starting point
    private Point3d p0;

    //direction from the staring point
    private Vector direction;
}
