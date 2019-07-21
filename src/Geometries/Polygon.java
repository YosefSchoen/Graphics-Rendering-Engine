package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.List;

//a polygon is a flat shape with n sides
public abstract class Polygon extends Geometry implements FlatGeometry {

    //empty constructor
    public Polygon() {
        super();
        numOfSides = 0;
    }

    //constructor
    public Polygon(int numOfSides) {
        super();
        this.numOfSides = numOfSides;
    }

    //alternative constructor
    public Polygon(int numOfSides, Material material, Color color) {
        super(material, color);
        this.numOfSides = numOfSides;
    }

    //copy constructor
    public Polygon(Polygon other) {
        super(other);
        this.numOfSides = other.numOfSides;
    }

    //getter
    public int getNumOfSides() {return numOfSides;}

    //setter
    public void setNumOfSides(int numOfSides) {this.numOfSides = numOfSides;}

    //implemented by triangle and other polygons
    public abstract List<Point3d> findIntersections(Ray P);
    public abstract Vector getNormal(Point3d P);

    private int numOfSides;
}
