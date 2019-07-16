package Renderer;

import Elements.Light;
import Elements.LightSource;
import Primitives.Point3d;
import Primitives.Ray;
import Geometries.Geometry;
import Primitives.Vector;
import Scene.Scene;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {
    private Scene scene;
    private ImageWriter imageWriter;
    private static final int RECURSION_LEVEL = 4;

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

                // ray will be made starting from the camera to each pixel on the scene
                Ray ray = scene.getCamera().constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), i,j, scene.getScreenDistance(), imageWriter.getWidth(), imageWriter.getHeight());

                //making a map from each geometry to its list of intersection points
                Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(ray);
                if (intersectionPoints.isEmpty()) {
                    imageWriter.writePixel(i, j, this.scene.getBackground());
                }

                else {
                    Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
                    Geometry geometry = (Geometry)closestPoint.keySet().toArray()[0];
                    Point3d point = (Point3d)closestPoint.values().toArray()[0];

                    this.imageWriter.writePixel(i, j, calcColor(geometry, point, ray));
                }
            }
        }

        this.imageWriter.writeToimage();
    }

    public void printGrid(int n) {
        return;
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
                diffuseLight = addColors(diffuseLight, diffuseColor);

                Color specularColor = calcSpecularColor(geometry, point, curLight);
                specularLight = addColors(specularLight, specularColor);
            }
        }

        List<Color>colors = new ArrayList<>();
        //colors.add(ambientLight);
        //colors.add(emissionLight);
        colors.add(diffuseLight);
        //colors.add(specularLight);
        //colors.add(reflectedLight);
        //colors.add(refractedLight);

        Color IO = addColors(colors);

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
        //L = L.normalize();
        double x = Math.abs(Kd * normal.dotProduct(L));

        int redValue = (int)(x * lightIntensity.getRed());
        int greenValue = (int)(x * lightIntensity.getGreen());
        int blueValue = (int)(x * lightIntensity.getBlue());


        Color diffuseLight = new Color(Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));
        //return new Color(1, 1, 1);
        return diffuseLight;
    }

    private Color calcSpecularColor(Geometry geometry, Point3d point, LightSource curLight) {
        Vector cameraRay = new Vector(scene.getCamera().getP0());
        double Ks = geometry.getMaterial().getKs();
        Vector N = geometry.getNormal(point);
        Vector L = curLight.getL(point);
        int shininess = geometry.getMaterial().getNShininess();
        Color intensity = curLight.getIntensity(point);

        Color specularColor = calcSpecularComp(Ks, cameraRay, N, L, shininess, intensity);
        return specularColor;
    }

    private Color calcSpecularComp(double Ks, Vector cameraRay, Vector normal, Vector L, int nShininess, Color lightIntensity) {
        //IL.scalarMultiply(Ks * vector.dotProduct(normal));
        Vector R = L.subtract(normal.scalarMultiply(2 * L.dotProduct(normal)));

        int redValue = (int)(Ks * Math.pow(cameraRay.dotProduct(R), nShininess) * lightIntensity.getRed());
        int greenValue = (int)(Ks * Math.pow(cameraRay.dotProduct(R), nShininess) * lightIntensity.getGreen());
        int blueValue = (int)(Ks * Math.pow(cameraRay.dotProduct(R), nShininess) * lightIntensity.getBlue());

        Color specularLight = new Color(Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));
        return  specularLight;
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
            return multiplyToColor(Kr, reflectedColor);
        }

        return new Color(0, 0, 0);
    }

    private Ray constructReflectedRay(Vector normal, Point3d point, Ray inRay) {
        Vector direction = inRay.getDirection().subtract(normal.scalarMultiply(2 * inRay.getDirection().dotProduct(normal)));
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
            return multiplyToColor(Kt, refractedColor);
        }

        return new Color(0, 0, 0);
    }

    private Ray constructRefractedRay(Vector normal, Point3d point, Ray inRay) {
        Ray R = new Ray(point, inRay.getDirection());

        return R;
    }

    private boolean notOccluded(LightSource light, Point3d point, Geometry geometry) {
        Vector lightDirection = light.getL(point);
        lightDirection = lightDirection.scalarMultiply(-1.0).normalize();

        Point3d geometryPoint = new Point3d(point);

//        Vector epsVector = new Vector(geometry.getNormal(point));
//        epsVector = epsVector.scalarMultiply(2.0);
//        geometryPoint = geometryPoint.add(epsVector);
//        geometryPoint = new Point3d(point).add(lightDirection.scalarMultiply( 0.000001 * ((geometry.getNormal(point).dotProduct(lightDirection) > 0) ? 1.0 : -1.0)));


        Ray lightRay = new Ray(geometryPoint.add(lightDirection.scalarMultiply(0.0000001)), lightDirection);

        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(lightRay);

        //if(geometry instanceof FlatGeometry) {
        //    intersectionPoints.remove(geometry);
        //}

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

    private Map<Geometry, List<Point3d>> getSceneRayIntersections2(Ray r) {
        //Gets an iterator so we can look at all the geometries in a scene
        Iterator<Geometry> geometriesIterator = scene.getGeometriesIterator();
        //Initializes a HashMap to hold all the points of intersection and the geometries with which they're associated
        Map<Geometry, List<Point3d>> totalSceneIntersections = new HashMap<Geometry, List<Point3d>>();

        //Loops over all the geometries and for each...
        while(geometriesIterator.hasNext()) {
            Geometry geometry = geometriesIterator.next();
            //Finds the points of intersection with the given ray
            List<Point3d> geometrysIntersectionList = geometry.findIntersections(r);
            //And adds then to the master list if it's empty
            if(!geometrysIntersectionList.isEmpty()) {
                totalSceneIntersections.put(geometry, geometrysIntersectionList);
            }
        }

        return totalSceneIntersections;
    }

    private Color addColors(Color color1, Color color2) {
        int redValue = color1.getRed() + color2.getRed();
        int greenValue = color1.getGreen() + color2.getGreen();
        int blueValue = color1.getBlue() + color2.getBlue();

        Color newColor = new Color(Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));

        return newColor;
    }

    private Color addColors(List<Color> colors) {
        //int i = 0;
        Color newColor = new Color(0, 0, 0);
        //while (!colors.isEmpty()) {
        //    newColor = addColors(newColor, colors.get(i));
        //    i++;
        //}
        for (Color col : colors) {
            newColor = addColors(newColor, col);
        }
        return newColor;
    }

    private Color multiplyToColor(double K, Color color) {
        int redValue = (int)K * color.getRed();
        int greenValue = (int)K * color.getGreen();
        int blueValue = (int)K * color.getBlue();

        Color newColor = new Color (Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));

        return newColor;
    }
}
