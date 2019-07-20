package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.List;

public abstract class Polygon extends Geometry implements FlatGeometry{
    public Polygon() {
        super();
        numOfSides = 0;
    }

    public Polygon(int numOfSides) {
        super();
        this.numOfSides = numOfSides;
    }

    public Polygon(int numOfSides, Material material, Color color) {
        super(material, color);
        this.numOfSides = numOfSides;
    }

    public Polygon(Polygon other) {
        super(other);
        this.numOfSides = other.numOfSides;
    }

    public int getNumOfSides() {return numOfSides;}
    public void setNumOfSides(int numOfSides) {this.numOfSides = numOfSides;}


    public abstract List<Point3d> findIntersections(Ray P);
    public abstract Vector getNormal(Point3d P);

    private int numOfSides;
}
