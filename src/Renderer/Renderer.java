package Renderer;

import Elements.Light;
import Elements.LightSource;
import Geometries.FlatGeometry;
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
    private static final int RECURSION_LEVEL = 100;

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
                //Point3d point = new Point3d();

                // ray will be made starting from the camera to each pixel on the scene
                Ray ray = scene.getCamera().constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), i,j, scene.getScreenDistance(), imageWriter.getWidth(), imageWriter.getHeight());

                //making a map from each geometry to its list of intersection points
                Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(ray);
                if (intersectionPoints.isEmpty()) {
                    imageWriter.writePixel(j, i, this.scene.getBackground());
                }
                else {
                    Map<Geometry, Point3d> closestPoint = getClosestPoint(intersectionPoints);
                    Geometry geometry = (Geometry)closestPoint.keySet().toArray()[0];
                    Point3d point3d = (Point3d)closestPoint.values().toArray()[0];

                    this.imageWriter.writePixel(j, i, calcColor(geometry, point3d, ray, 0));
                }
            }
        }
        this.imageWriter.writeToimage();
    }

    public void printGrid(int n) {
        return;
    }


    private Color calcColor(Geometry geometry, Point3d point, Ray inRay, int level) {
        if (level ==  RECURSION_LEVEL) return new Color(0, 0, 0);

        Color ambientLight = scene.getAmbientLight().getIntensity(point);
        Color emissionLight = geometry.getEmission();
        Color diffuseLight = new Color(0, 0, 0);
        Color specularLight = new Color(0, 0, 0);

        Iterator<LightSource> lights = scene.getLightsIterator();//_image = new BufferedImage(_imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);Im
        while (lights.hasNext()) {
            LightSource curLight = lights.next();
            if(!occluded(curLight, point, geometry)) {
                //fix this
                Vector cameraRay = new Vector(scene.getCamera().getP0());
                double Kd = geometry.getMaterial().getKd();
                double Ks = geometry.getMaterial().getKs();
                Vector N = geometry.getNormal(point);
                Vector L = curLight.getL(point);
                int shininess = geometry.getMaterial().getNShininess();
                Color intensity = curLight.getIntensity(point);

                //System.out.println("In light" + curLight);
                Color diffuseColor = calcDiffusiveComp(Kd, N, L, intensity);
                diffuseLight = addColors(diffuseLight, diffuseColor);

                Color specularColor = calcSpecularComp(Ks, cameraRay, N, L, shininess, intensity);
                specularLight = addColors(specularLight, specularColor);
            }
        }


        // Recursive call for a reﬂected ray 
        //Ray reﬂectedRay = constructReﬂectedRay(geometry.getNormal(point), point, inRay);
        //Map<Geometry, Point3d> reﬂectedEntry = ﬁndClosesntIntersection(reﬂectedRay);
        //Geometry reflectedGeometry = (Geometry)reﬂectedEntry.keySet().toArray()[0];
        //Point3d reflectedPoint = (Point3d)reﬂectedEntry.values().toArray()[0];
        //Color reﬂectedColor = calcColor(reflectedGeometry, reflectedPoint,reﬂectedRay, level);
        //double Kr = geometry.getMaterial().getKr();
        //Color reﬂectedLight = multiplyToColor(Kr, reﬂectedColor);


        // Recursive call for a refracted ray
        //Ray refractedRay = constructRefractedRay(geometry.getNormal(point), point, inRay);
        //Map<Geometry, Point3d> refractedEntry = ﬁndClosesntIntersection(reﬂectedRay);
        //Geometry refractedGeometry = (Geometry)reﬂectedEntry.keySet().toArray()[0];
        //Point3d refractedPoint = (Point3d)reﬂectedEntry.values().toArray()[0];
        //Color refractedColor = calcColor(refractedGeometry, refractedPoint, reﬂectedRay, level);
        //double Kt = geometry.getMaterial().getKt();
        //Color refractedLight = multiplyToColor(Kt, refractedColor);

        List<Color>colors = new ArrayList<Color>();
        colors.add(ambientLight);
        colors.add(emissionLight);
        colors.add(diffuseLight);
        colors.add(specularLight);
        //colors.add(reﬂectedLight);
        //colors.add(refractedLight);

        Color IO = addColors(colors);

        return IO;
    }

    private Color calcDiffusiveComp(double Kd, Vector normal, Vector L, Color lightIntensity){
        //IL.scalarMultiply(Kd * normal.dotProduct(lightIntensity));
        int redValue = (int)(Kd * normal.dotProduct(L) * lightIntensity.getRed());
        int greenValue = (int)(Kd * normal.dotProduct(L) * lightIntensity.getGreen());
        int blueValue = (int)(Kd * normal.dotProduct(L) * lightIntensity.getBlue());


        Color diffuseLight = new Color(Light.clamp(redValue), Light.clamp(greenValue), Light.clamp(blueValue));
        //return new Color(1, 1, 1);
        return diffuseLight;
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

    private boolean occluded(LightSource light, Point3d point, Geometry geometry) {
        Vector lightDirection = light.getL(point);
        lightDirection.scalarMultiply(-1.0);

        Point3d geometryPoint = new Point3d(point);

        Vector epsVector = new Vector(geometry.getNormal(point));
        epsVector.scalarMultiply(2.0);
        geometryPoint.add(epsVector);

        Ray lightRay = new Ray(geometryPoint, lightDirection);

        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(lightRay);

        if(geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }

        for (Map.Entry<Geometry, List<Point3d>> entry: intersectionPoints.entrySet()) {
            if (entry.getKey().getMaterial().getKt() == 0) {
                return true;
            }
            return false;
        }

        return !intersectionPoints.isEmpty();
    }

    private Ray constructReﬂectedRay(Vector normal, Point3d point, Ray inRay) {
        Vector direction = inRay.getDirection().subtract(normal.scalarMultiply(2 * inRay.getDirection().dotProduct(normal)));
        Ray R = new Ray(point, direction);
        return R;
    }

    private Ray constructRefractedRay(Vector normal, Point3d point, Ray inRay) {
        double n1 = 1;
        double n2 = 1;

        double cosThetaI = Math.cos(inRay.getDirection().dotProduct(normal) / (inRay.getDirection().length() * normal.length()));
        double cosThetaR = (n1 * Math.sin(Math.acos(cosThetaI))) / n2;

        //double cosThetaR = Math.cos(normal.scalarMultiply(-1.0).dotProduct(inRay.getDirection()) / (normal.length() * inRay.getDirection().length()));


        Vector direction = normal.subtract(inRay.getDirection()).scalarMultiply(cosThetaI - cosThetaR);

        Ray R = new Ray(point, direction);
        return R;
    }

    private Map<Geometry, Point3d>ﬁndClosesntIntersection(Ray reﬂectedRay) {
        Map<Geometry, List<Point3d>> intersectionPoints = getSceneRayIntersections(reﬂectedRay);
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
}
