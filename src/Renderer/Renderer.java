package Renderer;

import Elements.LightSource;
import Geometries.FlatGeometry;
import Primitives.Point3d;
import Primitives.Vector;
import Primitives.Ray;
import Geometries.Geometry;
import Scene.Scene;
import Utilities.Utilities;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {
    private Scene scene;
    private ImageWriter imageWriter;
    private static final int RECURSION_LEVEL = 4;
    private static final double eps = 0.000001;
    private static  final int MAX_RAYS = 4;

    public Renderer() {
        this.scene = new Scene();
        this.imageWriter = new ImageWriter("", 0, 0, 0, 0);
    }

    public Renderer(Scene scene, ImageWriter imageWriter) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

    public Renderer(Renderer other) {
        this.scene = other.scene;
        this.imageWriter = other.imageWriter;
    }

    public Scene getScene() {
        return this.scene;
    }

    public ImageWriter getImageWriter() {
        return this.imageWriter;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
    }

    public void renderImage() {
        //need to render through every pixel in the scene
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                List<Color> raysColors = new ArrayList<>();

                for (int k = (-1 * MAX_RAYS) / 2; k <= MAX_RAYS / 2; k++) {
                    // ray will be made starting from the camera to each pixel on the scene
                    double iOffSet = (k + (imageWriter.getWidth() / imageWriter.getNx()) / MAX_RAYS);
                    double jOffSet = (k + (imageWriter.getHeight() / imageWriter.getNy()) / MAX_RAYS);



                    Ray ray = scene.getCamera().constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), i + iOffSet, j + jOffSet, scene.getScreenDistance(), imageWriter.getWidth(), imageWriter.getHeight());

                    //making a map from each geometry to its list of intersection points
                    Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(ray);

                    if (intersectionPoints.isEmpty()) {
                        Color rayColor = this.scene.getBackground();
                        raysColors.add(rayColor);

                    }

                    else {
                        Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
                        Geometry geometry = (Geometry) closestPoint.keySet().toArray()[0];
                        Point3d point = (Point3d) closestPoint.values().toArray()[0];

                        Color rayColor = calcColor(geometry, point, ray);
                        raysColors.add(rayColor);
                    }

                    Color avgRayColors = Utilities.averageColor(raysColors);
                    this.imageWriter.writePixel(i, j, avgRayColors);
                }
            }
        }

        this.imageWriter.writeToimage();
    }

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

    private Color calcColor(Geometry geometry, Point3d point, Ray inRay) {
        int level = 0;
        return calcColor(geometry, point, inRay, level);
    }

    private Color calcColor(Geometry geometry, Point3d point, Ray inRay, int level) {
        if (level ==  RECURSION_LEVEL) return new Color(0, 0, 0);

        Color ambientLight = scene.getAmbientLight().getIntensity(point);
        Color emissionLight = geometry.getEmission();
        Color diffuseLight = new Color(0, 0, 0);
        Color specularLight = new Color(0, 0, 0);
        Color reflectedLight = calcReflectionColor(geometry, point, inRay, level);
        Color refractedLight = calcRefractionColor(geometry, point, inRay, level);

        Iterator<LightSource> lights = scene.getLightsIterator();
        while (lights.hasNext()) {
            LightSource curLight = lights.next();

            if (notOccluded(curLight, point, geometry)) {
                Color diffuseColor = calcDiffusiveColor(geometry, point, curLight);
                diffuseLight = Utilities.addColors(diffuseLight, diffuseColor);

                Color specularColor = calcSpecularColor(geometry, point, curLight);
                specularLight = Utilities.addColors(specularLight, specularColor);
            }
        }

        List<Color>colors = new ArrayList<>();
        colors.add(ambientLight);
        colors.add(emissionLight);
        colors.add(diffuseLight);
        colors.add(specularLight);
        colors.add(reflectedLight);
        colors.add(refractedLight);

        Color IO = Utilities.addColors(colors);

        return IO;
    }

    private Color calcDiffusiveColor(Geometry geometry, Point3d point, LightSource curLight) {
        double Kd = geometry.getMaterial().getKd();
        Vector N = geometry.getNormal(point);
        Vector L = curLight.getL(point);
        Color intensity = curLight.getIntensity(point);

        Color diffuseColor = calcDiffusiveComp(Kd, N, L, intensity);
        return diffuseColor;
    }

    private Color calcDiffusiveComp(double Kd, Vector normal, Vector L, Color lightIntensity){
        //IL.scalarMultiply(Kd * normal.dotProduct(lightIntensity));
        double scalar = Math.abs(Kd * normal.dotProduct(L));

        Color diffuseColor = Utilities.multiplyToColor(scalar, lightIntensity);
        return diffuseColor;
    }

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

    private Color calcSpecularComp(double Ks, Vector cameraVector, Vector normal, Vector L, int nShininess, Color lightIntensity) {
        //IL.scalarMultiply(Ks * vector.dotProduct(normal));

        double rScalar = 2 * L.dotProduct(normal);
        Vector R = L.subtract(normal.scalarMultiply(rScalar));

        cameraVector = cameraVector.normalize();
        R = R.normalize();

        double scalar = Math.abs(Ks * Math.pow(cameraVector.dotProduct(R), nShininess));

        Color specularColor = Utilities.multiplyToColor(scalar, lightIntensity);
        return  specularColor;
    }

    private Color calcReflectionColor(Geometry geometry, Point3d point, Ray inRay, int level){
        // Recursive call for a reflected ray 
        Ray reﬂectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);
        Map<Geometry, Point3d> reflectedEntry = ﬁndClosesntIntersection(reﬂectedRay);

        if (!reflectedEntry.isEmpty()) {
            Geometry reflectedGeometry = (Geometry)reflectedEntry.keySet().toArray()[0];
            Point3d reflectedPoint = (Point3d)reflectedEntry.values().toArray()[0];
            Color reflectedColor = calcColor(reflectedGeometry, reflectedPoint,reﬂectedRay, level + 1);
            double Kr = geometry.getMaterial().getKr();
            reflectedColor = Utilities.multiplyToColor(Kr, reflectedColor);
            return reflectedColor;
        }

        return new Color(0, 0, 0);
    }

    private Ray constructReflectedRay(Vector normal, Point3d point, Ray inRay) {
        Vector inVector = inRay.getDirection();

        double directionScalar = 2 * inVector.dotProduct(normal);
        Vector direction = inVector.subtract(normal.scalarMultiply(directionScalar));

        point = point.add(direction.scalarMultiply(eps));
        Ray R = new Ray(point, direction);
        return R;
    }

    private Color calcRefractionColor(Geometry geometry, Point3d point, Ray inRay, int level){
        // Recursive call for a refracted ray
        Ray refractedRay = constructRefractedRay(geometry.getNormal(point), point, inRay);
        Map<Geometry, Point3d> refractedEntry = ﬁndClosesntIntersection(refractedRay);

        if (!refractedEntry.isEmpty()) {
            Geometry refractedGeometry = (Geometry)refractedEntry.keySet().toArray()[0];
            Point3d refractedPoint = (Point3d) refractedEntry.values().toArray()[0];
            Color refractedColor = calcColor(refractedGeometry, refractedPoint, refractedRay, level + 1);
            double Kt = geometry.getMaterial().getKt();
            return Utilities.multiplyToColor(Kt, refractedColor);
        }

        return new Color(0, 0, 0);
    }

    private Ray constructRefractedRay(Vector normal, Point3d point, Ray inRay) {
        Vector inVector = inRay.getDirection();

        point = point.add(inVector.scalarMultiply(eps));
        Ray R = new Ray(point, inVector);

        return R;
    }

    private boolean notOccluded(LightSource light, Point3d point, Geometry geometry) {
        Vector lightDirection = light.getL(point);
        lightDirection = lightDirection.scalarMultiply(-1.0);

        Point3d geometryPoint = new Point3d(point);

        Vector epsVector = new Vector(geometry.getNormal(point));
        epsVector = epsVector.scalarMultiply(2.0);
        geometryPoint = geometryPoint.add(epsVector);
        //geometryPoint = new Point3d(point).add(lightDirection.scalarMultiply( 0.000001 * ((geometry.getNormal(point).dotProduct(lightDirection) > 0) ? 1.0 : -1.0)));


        Ray lightRay = new Ray(geometryPoint.add(lightDirection.scalarMultiply(eps)), lightDirection);

        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(lightRay);

        if(geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }

        for (Map.Entry<Geometry, List<Point3d>> entry: intersectionPoints.entrySet()) {
            if (entry.getKey().getMaterial().getKt() != 0) {
                return false;
            }
        }

        return true;
    }

    private Map<Geometry, Point3d>ﬁndClosesntIntersection(Ray reflectedRay) {
        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(reflectedRay);
        Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
        return closestPoint;
    }

    private Map<Geometry, Point3d> getClosestPoint(Map<Geometry, List<Point3d>> intersectionPoints) {
        double distance = Double.MAX_VALUE;
        Point3d P0 = new Point3d(scene.getCamera().getP0());
        Map<Geometry, Point3d> minDistancePoint = new HashMap<Geometry, Point3d>();

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

    private Map<Geometry, List<Point3d>> getSceneRayIntersections(Ray ray) {
        Iterator<Geometry> geometries = scene.getGeometriesIterator();
        Map<Geometry, List<Point3d>> intersectionPoints = new HashMap<Geometry, List<Point3d>>();
        while (geometries.hasNext()) {
            Geometry geometry = geometries.next();
            List<Point3d> geometryIntersectionPoints = geometry.findIntersections(ray);
            if (!geometryIntersectionPoints.isEmpty()) {
                intersectionPoints.put(geometry, geometryIntersectionPoints);
            }
        }

        return intersectionPoints;
    }

    private Map<Geometry, List<Point3d>> getSceneRayIntersectionsImproved(Ray ray) {
        Iterator<Geometry> geometries = scene.getGeometriesIterator();
        Map<Geometry, List<Point3d>> intersectionPoints = new HashMap<Geometry, List<Point3d>>();
        while (geometries.hasNext()) {
            Geometry geometry = geometries.next();
            List<Point3d> geometryIntersectionPoints = geometry.findIntersections(ray);
            if (!geometryIntersectionPoints.isEmpty()) {
                intersectionPoints.put(geometry, geometryIntersectionPoints);
            }
        }

        return intersectionPoints;
    }

    private Ray getRaysThroughPixel(){return null;}
}
