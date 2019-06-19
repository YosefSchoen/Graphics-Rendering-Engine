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

    public Geometry(Color emission, Material material) {
        this.emission = emission;
        this.material = material;
    }

    public Geometry(Color emission) {
        this.emission = emission;
        this.material = new Material();
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

