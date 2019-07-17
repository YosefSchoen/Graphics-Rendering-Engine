package Elements;

import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

//the camera will shoot rays and see where they intersect on the various geometries, it will color the pixel of the intersection points black
public class Camera {

    //empty constructor
    public Camera() {
        this.p0 = new Point3d();
        this.vUp = new Vector(0, 1, 0);
        this.vTo = new Vector(0, 0, -1);
        this.vRight = new Vector(1, 0, 0);
    }

    //constructor
    public Camera(Point3d p0, Vector vUp, Vector vTo) {
        this.p0 = p0;
        this.vUp = vUp;
        this.vTo = vTo;
        this.vRight = (this.vTo.crossProduct(this.vUp));
    }

    //copy constructor
    public Camera(Camera other) {
        this.p0 = other.p0;
        this.vUp = other.vUp;
        this.vTo = other.vTo;
        this.vRight = other.vRight;
    }

    //getters
    public Point3d getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }


    //setters
    public void setP0(Point3d p0) {
        this.p0 = p0;
    }

    public void setvUp(Vector vUp) {
        this.vUp = vUp;
    }

    public void setvTo(Vector vTo) {
        this.vTo = vTo;
    }

    public void setvRight(Vector vRight) {
        this.vRight = vRight;
    }

    //this method will find build a ray through to the pixel we want
    public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y, double screenDistance, double screenWidth, double screenHeight) {
        //image center
        Point3d pC = this.p0.add(this.vTo.scalarMultiply(screenDistance));

        //Ratio (pixel width and height)
        double Rx = screenWidth / Nx;
        double Ry = screenHeight / Ny;

        //setting x center and y center
        double xI = (Rx * (x - (Nx / 2))) + (Rx / 2);
        double yJ = (Ry * (y - (Ny / 2))) + (Ry / 2);

        //setting center
        Point3d p = pC;

        //adjusting center only if xi is not zero
        if(xI != 0 ) {
            p = p.add(this.vRight.scalarMultiply(xI));
        }

        //same for yj
        if (yJ != 0) {
            p = p.add(this.vUp.scalarMultiply(-yJ));
        }

        //setting up
        Vector v = new Vector(p.subtract(this.p0));
        v = v.normalize();

        Ray rayThroughPixel = new Ray(this.p0, v);
        return rayThroughPixel;
    }


    //p0 is the origin point of the camera
    private Point3d p0;

    //these are the vectors going up, toward, and rightward of the screen from p0
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
}
