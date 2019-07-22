package Renderer;

import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;

import Geometries.Geometry;
import Geometries.FlatGeometry;

import Elements.LightSource;
import Scene.Scene;
import Utilities.Utilities;

import java.awt.*;
import java.util.*;
import java.util.List;

//renderer class is an object which accesses and calculates all relevant data from the scene and sends it to the image writer
public class Renderer {

    //the scene holds information for all geometries and lights for the image
    private Scene scene;
    private ImageWriter imageWriter;
    private static final int RECURSION_LEVEL = 4;
    private static final double eps = Math.pow(10, -6);
    private static  final int MAX_RAYS = 6;

    //empty constructor
    public Renderer() {
        this.scene = new Scene();
        this.imageWriter = new ImageWriter("", 0, 0, 0, 0);
    }

    //constructor
    public Renderer(Scene scene, ImageWriter imageWriter) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

    //copy constructor
    public Renderer(Renderer other) {
        this.scene = other.scene;
        this.imageWriter = other.imageWriter;
    }

    //getters
    public Scene getScene() {
        return this.scene;
    }

    public ImageWriter getImageWriter() {
        return this.imageWriter;
    }

    //setters
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
    }


    //the is the main function of the class that calls all relevant helper functions to render the image
    public void renderImage() {
        //need to render through every pixel in the scene
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {

                //makes a list of the multiple rays that goes through different parts of the pixel
                List<Ray> raysThroughPixel = getRaysThroughPixel(i, j);

                //will store the colors in that pixel in a list later
                List<Color> raysColors = new ArrayList<>();

                //this will calculate the color for each ray
                for (Ray ray:raysThroughPixel) {

                    //making a map from each geometry to its list of intersection points
                    Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(ray);

                    //if the ray does not hit a geometry then the color added to the list will be the background color
                    if (intersectionPoints.isEmpty()) {
                        Color rayColor = this.scene.getBackground();
                        raysColors.add(rayColor);
                    }

                    //if it does hit a geometry,
                    else {
                        //it will find the closest point and calculate the color of that point with respect towards its geometry
                        Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
                        Geometry geometry = (Geometry)closestPoint.keySet().toArray()[0];
                        Point3d point = (Point3d)closestPoint.values().toArray()[0];
                        Color rayColor = calcColor(geometry, point, ray);
                        raysColors.add(rayColor);
                    }
                }

                //finds the average color of that pixel and sends it to the image writer
                Color avgRayColors = Utilities.averageColor(raysColors);
                this.imageWriter.writePixel(i, j, avgRayColors);
            }
        }

        //when it finishes it will write the image
        this.imageWriter.writeToimage();
    }

    //a function to print a grid for the image
    public void printGrid(int n) {
        Color color = new Color(255, 255, 255);
        for(int i = 0; i < imageWriter.getNx(); i++) {
            for(int j = 0; j < imageWriter.getNy(); j++) {
                if (i % n == 0 || j % n == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    //sends information from the render image function to the recursive version of this one starting with level = 0
    private Color calcColor(Geometry geometry, Point3d point, Ray inRay) {
        int level = 0;
        return calcColor(geometry, point, inRay, level);
    }

    //this will calculate the color of a point with respect to its geometry and ray
    private Color calcColor(Geometry geometry, Point3d point, Ray inRay, int level) {

        //base case for reflection and refraction
        if (level ==  RECURSION_LEVEL) return new Color(0, 0, 0);

        //six types of lights needed by the Phong Model

        //ambient and emission light are already defined
        Color ambientLight = scene.getAmbientLight().getIntensity(point);
        Color emissionLight = geometry.getEmission();

        //diffuse and specular light will be calculated below
        Color diffuseLight = new Color(0, 0, 0);
        Color specularLight = new Color(0, 0, 0);

        //reflected and refracted light are calculated recursively here
        Color reflectedLight = calcReflectionColor(geometry, point, inRay, level);
        Color refractedLight = calcRefractionColor(geometry, point, inRay, level);


        //diffuse and specular light will be calculated here
        //they work together with the light sources in the scene
        Iterator<LightSource> lights = scene.getLightsIterator();
        while (lights.hasNext()) {
            LightSource curLight = lights.next();

            //not Occluded will check to see if the light source is blocked and can't reach our geometry
            if (!Occluded(curLight, point, geometry)) {
                //the defuse color will be calculated and added to the the diffuse light
                Color diffuseColor = calcDiffusiveColor(geometry, point, curLight);
                diffuseLight = Utilities.addColors(diffuseLight, diffuseColor);

                //similar process for specular light
                Color specularColor = calcSpecularColor(geometry, point, curLight);
                specularLight = Utilities.addColors(specularLight, specularColor);
            }
        }

        //put all 6 lights in a list
        List<Color>colors = new ArrayList<>();
        colors.add(ambientLight);
        colors.add(emissionLight);
        colors.add(diffuseLight);
        colors.add(specularLight);
        colors.add(reflectedLight);
        colors.add(refractedLight);

        //add all of the lights and set the IO color equal to the sum and return that color
        Color IO = Utilities.addColors(colors);
        return IO;
    }

    //helper function of calcColor to send everything to calcDiffusiveComp
    private Color calcDiffusiveColor(Geometry geometry, Point3d point, LightSource curLight) {
        //getting the scalar K diffuse, the normal, the vector to the point and the color of the light
        double Kd = geometry.getMaterial().getKd();
        Vector N = geometry.getNormal(point);
        Vector L = curLight.getL(point);
        Color intensity = curLight.getIntensity(point);

        //and sending them to the function to calculate the diffuse color
        Color diffuseColor = calcDiffusiveComp(Kd, N, L, intensity);

        return diffuseColor;
    }

    //calculates the diffusive Color
    private Color calcDiffusiveComp(double Kd, Vector normal, Vector L, Color lightIntensity){
        //the scalar for a diffuse light
        double scalar = Math.abs(Kd * normal.dotProduct(L));

        //multiplying the light by the scalar
        Color diffuseColor = Utilities.multiplyToColor(scalar, lightIntensity);
        return diffuseColor;
    }

    //similar to calcDiffusiveColor but for specular light
    private Color calcSpecularColor(Geometry geometry, Point3d point, LightSource curLight) {
        Vector cameraVector = new Vector((scene.getCamera().getP0()).subtract(point));
        double Ks = geometry.getMaterial().getKs();
        Vector N = geometry.getNormal(point);
        Vector L = curLight.getL(point);
        int shininess = geometry.getMaterial().getNShininess();
        Color intensity = curLight.getIntensity(point);

        Color specularColor = calcSpecularComp(Ks, cameraVector, N, L, shininess, intensity);
        return specularColor;
    }

    //similar to calcDiffusiveComp but for specular light
    private Color calcSpecularComp(double Ks, Vector cameraVector, Vector normal, Vector L, int nShininess, Color lightIntensity) {
        //the Vector R and its scalar
        double rScalar = 2 * L.dotProduct(normal);
        Vector R = L.subtract(normal.scalarMultiply(rScalar));

        //normalizing the to vectors
        cameraVector = cameraVector.normalize();
        R = R.normalize();

        //the scalar for the specular light
        double scalar = Math.abs(Ks * Math.pow(cameraVector.dotProduct(R), nShininess));

        //multiplying the vector by its scalar
        Color specularColor = Utilities.multiplyToColor(scalar, lightIntensity);
        return  specularColor;
    }

    private Color calcReflectionColor(Geometry geometry, Point3d point, Ray inRay, int level){

        //will create the reflected ray and find a map of its intersection points with the geometry
        Ray reflectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);
        Map<Geometry, Point3d> reflectedEntry = findClosestIntersection(reflectedRay);

        //if there is a least one intersection point
        if (!reflectedEntry.isEmpty()) {
            //pull the geometry and point out of the map
            Geometry reflectedGeometry = (Geometry)reflectedEntry.keySet().toArray()[0];
            Point3d reflectedPoint = (Point3d)reflectedEntry.values().toArray()[0];

            //and recursively send them to calcColor
            Color reflectedColor = calcColor(reflectedGeometry, reflectedPoint, reflectedRay, level + 1);

            //get the scalar K reflective and scale the light by it
            double Kr = geometry.getMaterial().getKr();
            reflectedColor = Utilities.multiplyToColor(Kr, reflectedColor);
            return reflectedColor;
        }

        //if it is empty return 0
        return new Color(0, 0, 0);
    }

    //function to find the reflected ray off of a surface
    private Ray constructReflectedRay(Vector normal, Point3d point, Ray inRay) {
        Vector inVector = inRay.getDirection();

        double directionScalar = 2 * inVector.dotProduct(normal);
        Vector direction = inVector.subtract(normal.scalarMultiply(directionScalar));

        //multiply the direction by some epsilon to avoid floating point errors
        point = point.add(direction.scalarMultiply(eps));
        Ray R = new Ray(point, direction);
        return R;
    }

    //similar process for as a reflected ray bu for a refracted one
    private Color calcRefractionColor(Geometry geometry, Point3d point, Ray inRay, int level){
        // Recursive call for a refracted ray
        Ray refractedRay = constructRefractedRay(geometry.getNormal(point), point, inRay);
        Map<Geometry, Point3d> refractedEntry = findClosestIntersection(refractedRay);

        if (!refractedEntry.isEmpty()) {
            Geometry refractedGeometry = (Geometry)refractedEntry.keySet().toArray()[0];
            Point3d refractedPoint = (Point3d) refractedEntry.values().toArray()[0];
            Color refractedColor = calcColor(refractedGeometry, refractedPoint, refractedRay, level + 1);
            double Kt = geometry.getMaterial().getKt();
            return Utilities.multiplyToColor(Kt, refractedColor);
        }

        return new Color(0, 0, 0);
    }

    //function to find the refracted ray through a surface, (the normal is not being used in this project but is needed for more nuanced refraction ray)
    private Ray constructRefractedRay(Vector normal, Point3d point, Ray inRay) {
        Vector inVector = inRay.getDirection();

        //multiply the direction by some epsilon to avoid floating point errors
        point = point.add(inVector.scalarMultiply(eps));
        Ray R = new Ray(point, inVector);

        return R;
    }

    //function to see if the light source is being blocked from hitting a geometry
    private boolean Occluded(LightSource light, Point3d point, Geometry geometry) {

        //getting the light's direction and reversing it
        Vector lightDirection = light.getL(point);
        lightDirection = lightDirection.reverse();

        Point3d geometryPoint = new Point3d(point);

        //adding some epsilon vector to avoid floating point errors
        Vector epsVector = new Vector(geometry.getNormal(point));
        epsVector = epsVector.scalarMultiply(2.0);
        geometryPoint = geometryPoint.add(epsVector);

        //multiply the direction by some epsilon to avoid floating point errors and creating a ray
        Point3d rayPoint = geometryPoint.add(lightDirection.scalarMultiply(eps));
        Ray lightRay = new Ray(rayPoint, lightDirection);

        //mapping the intersections of the ray from a point to its geometry
        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(lightRay);

        //flat geometries can not self intersect
        if(geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }

        //if the refracted light's scalar Kt is 0 then the light is occlude
        for (Map.Entry<Geometry, List<Point3d>> entry: intersectionPoints.entrySet()) {
            if (entry.getKey().getMaterial().getKt() == 0) {
                return true;
            }
        }

        //if it got here it is not occluded
        return false;
    }

    //this will find the closest intersection point of the geometry from the ray
    private Map<Geometry, Point3d>findClosestIntersection(Ray reflectedRay) {

        //getting the list of all intersection points from the ray to the geometry
        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(reflectedRay);

        //finding the closest point of the intersections
        Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
        return closestPoint;
    }

    //this will find the closest point from a list of points
    private Map<Geometry, Point3d> getClosestPoint(Map<Geometry, List<Point3d>> intersectionPoints) {
        //max value for a double the distance will be compared to
        double distance = Double.MAX_VALUE;

        //point p0 is the point to check against
        Point3d P0 = new Point3d(scene.getCamera().getP0());
        Map<Geometry, Point3d> minDistancePoint = new HashMap<Geometry, Point3d>();

        //keep replacing the min distance point with closer points from the list until the list is finished being iterated over
        for (Map.Entry<Geometry, List<Point3d>> entry: intersectionPoints.entrySet()) {
            for (Point3d point:entry.getValue()) {
                if(P0.distance(point) < distance) {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(), new Point3d(point));
                    distance = P0.distance(point);
                }
            }
        }

        return minDistancePoint;
    }

    //this will find the intersection points of the geometries in the scene from the ray
    private Map<Geometry, List<Point3d>> getSceneRayIntersections(Ray ray) {
        Map<Geometry, List<Point3d>> intersectionPoints = new HashMap<Geometry, List<Point3d>>();

        //iterate over each geometry and map each geometry to a list of its intersection points
        Iterator<Geometry> geometries = scene.getGeometriesIterator();
        while (geometries.hasNext()) {
            Geometry geometry = geometries.next();
            List<Point3d> geometryIntersectionPoints = geometry.findIntersections(ray);

            if (!geometryIntersectionPoints.isEmpty()) {
                intersectionPoints.put(geometry, geometryIntersectionPoints);
            }
        }

        return intersectionPoints;
    }

    //this function improves ray tracing by making multiple rays through different parts of the pixel
    private List<Ray> getRaysThroughPixel(int x, int y){

        //the ratio of the screen dimensions to the number of pixels
        double Rx = imageWriter.getWidth() / imageWriter.getNx();
        double Ry = imageWriter.getHeight() / imageWriter.getNy();

        //store the rays in a list
        List<Ray> raysThroughPixel = new ArrayList<>();

        //i will go from -1/2 * the max to 1/2 max
        for (int i = (-1 * MAX_RAYS) / 2; i <= MAX_RAYS / 2; i++) {
            for (int j = (-1 * MAX_RAYS) / 2; j <= MAX_RAYS / 2; j++) {

                //calculating the coordinate of the offset rays
                double iOffSet = (i * Rx) / MAX_RAYS;
                double jOffSet = (j * Ry) / MAX_RAYS;

                //making a ray to that position and placing it in the list
                Ray ray = scene.getCamera().constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), x + iOffSet, y + jOffSet, scene.getScreenDistance(), imageWriter.getWidth(), imageWriter.getHeight());
                raysThroughPixel.add(ray);
            }
        }

        return raysThroughPixel;
    }
}
