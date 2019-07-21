package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.List;

//an abstract class for all geometries to be able to join in a list also, it has the material and emission color for the geometry
public abstract class Geometry {

    //empty constructor
    public Geometry() {
        this.material = new Material();
        this.emission = new Color(0, 0, 0);
    }

    //constructor
    public Geometry(Material material, Color emission) {
        this.material = material;
        this.emission = emission;
    }

    //constructor without emission
    public Geometry(Color emission) {
        this.material = new Material();
        this.emission = emission;
    }

    //copy constructor
    public Geometry(Geometry other) {
        this.material = other.material;
        this.emission = other.emission;
    }

    //find intersections will be implemented differently by each geometry
    public abstract List<Point3d> findIntersections(Ray P);

    //also get normal
    public abstract Vector getNormal(Point3d P);

    //getters
    public Material getMaterial() {
        return material;
    }

    public Color getEmission() {
        return emission;
    }

    //setters
    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setEmission(Color emission) {
        this.emission = emission;
    }

    //the material for a geometry and its "base" emission color
    private Material material;
    private Color emission;
}

