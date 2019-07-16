package Geometries;

import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import java.awt.*;
import java.util.List;


public abstract class Geometry {
    public Geometry() {
        this.material = new Material();
        this.emission = new Color(0, 0, 0);
    }

    public Geometry(Material material, Color emission) {
        this.material = material;
        this.emission = emission;
    }

    public Geometry(Color emission) {
        this.material = new Material();
        this.emission = emission;
    }

    public Geometry(Geometry other) {
        this.material = other.material;
        this.emission = other.emission;
    }

    public abstract List<Point3d> findIntersections(Ray P);

    public abstract Vector getNormal(Point3d P);

    public Material getMaterial() {
        return material;
    }

    public Color getEmission() {
        return emission;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setEmission(Color emission) {
        this.emission = emission;
    }

    private Material material;
    private Color emission;
}

