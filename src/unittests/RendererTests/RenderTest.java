package unittests.RendererTests;

import Elements.*;
import Geometries.Geometry;
import Geometries.Plane;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Coordinate;
import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Renderer.Renderer;
import Scene.Scene;
import Renderer.ImageWriter;
import javafx.geometry.Point3D;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class RenderTest {
    int width = 500; //ImageWriters parameter
    int height = 500; //ImageWriters parameter
    int Nx = 500; //ImageWriters parameter
    int Ny = 500; //ImageWriters parameter

    double Ka = 0.3; //ambientLight parameter
    double screenDistance = 200; //scene parameter
    double kcP = 0.5; //point light parameter
    double klP = 0.5; //point light parameter
    double kqP = 0.5; //point light parameter
    double kcS = 50; //spot light parameter
    double klS = 50; //spot light parameter
    double kqS = 50; //spot light parameter
    double s1Radius = 10; //sphere1 parameters
    double s2Radius = 50; //sphere2 parameters


    String imageName = "test"; //ImageWriters parameter
    String sceneName = "scene test"; //scene parameter

    Point3d p0 = new Point3d(); //camera parameter
    Point3d poPoint = new Point3d(1, 2, -40); //point light parameter
    Point3d spPoint = new Point3d(); //spot light parameter
    Point3d s1Center = new Point3d(0, 0, -49); //sphere1 parameters
    Point3d s2Center = new Point3d(3, 3, -100); //sphere2 parameters
    Point3d tP1 = new Point3d(30, 0, -49);
    Point3d tP2 = new Point3d(0, 30, -49);
    Point3d tP3 = new Point3d(30, 30, -49);

    Vector vUp = new Vector(0, 1, 0); //camera parameter
    Vector vTo = new Vector(0, 0, -1); //camera parameter
    Vector diVector = new Vector(-0.5, -0.5, 0); //directional light parameter
    Vector spVector = new Vector(0, 0, -1); //spot light parameter

    Color background = new Color(20, 20, 20); //scene parameter
    Color amColor = new Color(100, 100, 100); //ambientLight parameter
    Color diColor = new Color(0, 255, 255); //directional light parameter
    Color poColor = new Color(0, 255, 0); //point light parameter
    Color spColor = new Color(255, 255, 255); //spot light parameter

    List<LightSource> lights = new ArrayList<>(); //scene parameter
    AmbientLight ambientLight = new AmbientLight(amColor, Ka); //scene parameter
    DirectionalLight directionalLight = new DirectionalLight(diColor, diVector); //light parameter
    PointLight pointLight = new PointLight(poColor, poPoint, kcP, klP, kqP);; //light parameter
    SpotLight spotLight = new SpotLight(spColor, spPoint, kcS, klS, kqS, spVector); //light parameter

    List<Geometry> geometries = new ArrayList<Geometry>(); //scene parameter
    Sphere sphere1 = new Sphere(s1Radius, s1Center); //geometry parameter
    Sphere sphere2 = new Sphere(s2Radius, s2Center); //geometry parameter
    Triangle triangle = new Triangle(tP1, tP2, tP3); //geometry parameter

    Camera camera = new Camera(p0, vUp, vTo); //scene parameter
    Scene scene = new Scene(sceneName, background, ambientLight, lights, geometries, camera, screenDistance); //Renderer parameters
    ImageWriter imageWriter = new ImageWriter(imageName, width, height, Nx, Ny); //Renderer parameters
    Renderer renderer = new Renderer(scene, imageWriter);

    @org.junit.Test
    public void RenderTest() {
        lights.add(directionalLight);
        lights.add(pointLight);
        lights.add(spotLight);

        sphere1.setMaterial(new Material(0.5, 0.5, 0.5, 0, 2));
        sphere1.setEmission(new Color(0, 0, 60));


        sphere2.setMaterial(new Material(0.5, 0.5, 0.5, 0, 2));
        sphere2.setEmission(new Color(100, 0, 0));

        triangle.setEmission(new Color(255, 0, 0));

        Point3d directionPoint = new Point3d(0, 1.5, -1);
        Point3d planePoint = new Point3d(0, 0, -60);
        Vector direction = new Vector(directionPoint);
        Plane plane2 = new Plane(planePoint, direction);
        plane2.setMaterial(new Material());
        plane2.setEmission(new Color(10, 10, 100));

        geometries.add(sphere1);
        geometries.add(sphere2);
        geometries.add(triangle);
        //geometries.add(new Plane(triangle.getP1(), triangle.getNormal(null)));
        //geometries.add(plane2);

        renderer.getScene().setAmbientLight(ambientLight);
        renderer.renderImage();
    }

    @org.junit.Test
    public  void test2() {
        Camera c = new Camera(new Point3d(), new Vector(0, -1, 0), new Vector(0, 0, -1));

        Sphere sphere = new Sphere(25, new Point3d(0, 0, -50));
        sphere.setEmission(new Color(100, 0, 0));
        Triangle t1 = new Triangle(new Point3d(100, 0, -49), new Point3d(0, 100, -49), new Point3d(100, 100, -49), new Material(), new Color(0, 0, 100));
        Triangle t2 = new Triangle(new Point3d(-100, 0, -49), new Point3d(0, 100, -49), new Point3d(-100, 100, -49), new Material(), new Color(0, 100, 100));
        Triangle t3 = new Triangle(new Point3d(100, 0, -49), new Point3d(0, -100, -49), new Point3d(100, -100, -49), new Material(), new Color(100, 0, 100));
        Triangle t4 = new Triangle(new Point3d(-100, 0, -49), new Point3d(0, -100, -49), new Point3d(-100, -100, -49), new Material(), new Color(100, 100, 0));

        AmbientLight am = new AmbientLight(new Color(100, 100, 100), 0.2);

        List<Geometry> geometrieslist = new ArrayList<>();
        geometrieslist.add(sphere);
        geometrieslist.add(t1);
        geometrieslist.add(t2);
        geometrieslist.add(t3);
        geometrieslist.add(t4);

        PointLight pl = new PointLight(new Color(255, 50, 150), new Point3d(50, 50, -60), 0.5, 0.5, 0.5);

        List<LightSource> lightlist = new ArrayList<>();
        lightlist.add(pl);

        Scene s = new Scene(sceneName, new Color(60, 60, 60), am, lightlist, geometrieslist, c, 50);
        ImageWriter im = new ImageWriter(imageName + "1", 500, 500, 500, 500);
        Renderer ren = new Renderer(s, im);

        ren.renderImage();
    }
    
    @org.junit.Test
    public void floorTest() {
        AmbientLight ambientLight = new AmbientLight(new Color(255, 255, 255), 1.0);
        Camera camera = new Camera();
        List<Geometry> geoList = new ArrayList<Geometry>();
        Plane floor = new Plane(new Point3d(-10, -3, -1), new Point3d(10, -3, -1), new Point3d(0, -3, -12), new Material(), new Color(200, 200, 200));
        geoList.add(floor);
//		Triangle floor1 = new Triangle(new Point3d(-1.5, -3, 0), new Point3d(1.5, -3, 0), new Point3d(1.5, -3, 3), new Material(), new Color(200, 200, 200));
//		geoList.add(floor1);
//		Triangle floor2 = new Triangle(new Point3d(-1.5, -3, 0), new Point3d(-1.5, -3, 3), new Point3d(1.5, -3, 3), new Material(), new Color(200, 200, 200));
//		geoList.add(floor2);
        List<LightSource> lightList = new ArrayList<LightSource>();
        PointLight pointlight = new PointLight(new Color(255, 0, 0), new Point3d(-25, 25, -20.0), 0.9, 0.9, 0.9);
        //lightList.add(pointlight);
        PointLight greenlight = new PointLight(new Color(0, 255, 0), new Point3d(0, 0, -1), 0.5, 0.5, 0.5);
        lightList.add(greenlight);
        Scene scene = new Scene("Floor test", new Color(30, 30, 30), ambientLight, lightList, geoList, camera, 10.0);
        ImageWriter imageWriter = new ImageWriter("floorTest5", 1000, 1000, 100, 100);
        //Creates a Render instance to pull it all together
        Renderer render = new Renderer(scene, imageWriter);

        //Records what the camera sees
        render.renderImage();
        //Prints it all to a file
        imageWriter.writeToimage();
    }

    @Test
    public void dovidAndYoTest() {
        Scene scene = new Scene();
        scene.setScreenDistance(100);
        scene.setBackground(new Color(60, 60, 60));

        Color emissions1 = new Color(20, 20, 100);
        Material m1 = new Material(0.1, 0.1, 0.1, 0.1, 4);
        Point3d center1 = new Point3d(3, 4, -500);

        Sphere sphere1 = new Sphere(300, center1);
        sphere1.setEmission(emissions1);
        sphere1.setMaterial(m1);

        Color emissions2 = new Color(80, 80, 200);
        Material m2 = new Material(0.1, 0.1, 0.1, 0.1, 4);
        Point3d center2 = new Point3d(200, 4, -200);

        Sphere sphere2 = new Sphere(50, center2);
        sphere2.setEmission(emissions2);
        sphere2.setMaterial(m2);

        scene.addGeometry(sphere1);
        scene.addGeometry(sphere2);

        AmbientLight ambientLight = new AmbientLight(new Color(150, 150, 150), 0.1);

        Color PLColor = new Color(150, 150, 150);
        Point3d PLPosition = new Point3d(30, 20, -200);
        PointLight pointLight = new PointLight(PLColor, PLPosition, 0.1, 0.1, 0.1);

        Color SLColor = new Color(150, 150, 150);
        Point3d SLPosition = new Point3d(80, 20, -130);
        Vector SLDirection = new Vector(-2, 3, -1);
        SpotLight spotLight = new SpotLight(SLColor, SLPosition, 0.1, 0.1, 0.1, SLDirection);

        scene.setBackground(new Color(60, 60, 60));
        scene.setAmbientLight(ambientLight);
        scene.addLight(pointLight);
        scene.addLight(spotLight);

        ImageWriter imageWriter = new ImageWriter("MyTest", 500, 500, 500, 500);
        Renderer render = new Renderer(scene, imageWriter);
        render.renderImage();
    }

    @Test
    public void softShadowTest() {
        Scene scene = new Scene();
        scene.setScreenDistance(100);
        scene.setBackground(new Color(60, 60, 60));

        Point3d p1 = new Point3d(-100, -100, -50);
        Point3d p2 = new Point3d(100, -100, -50);
        Point3d p3 = new Point3d(0, 100, -50);
        Triangle triangle = new Triangle(p1, p2, p3);
        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.1, 0.1, 0.1, 0.1, 6));
        scene.addGeometry(triangle);

        Color amColor = new Color(50, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.5);
        scene.setAmbientLight(ambientLight);

        Color colorSL = new Color(255, 100, 100);
        Point3d pointSL = new Point3d(-200, -200, -150);
        Vector vectorSL =  new Vector(2, 2, -3);
        SpotLight spotLight = new SpotLight(colorSL, pointSL, 0.1, 0.1, 0.1, vectorSL);
        scene.addLight(spotLight);

        ImageWriter imageWriter = new ImageWriter("softShadowTest", 500, 500, 500, 500);
        Renderer render = new Renderer(scene, imageWriter);
        render.renderImage();
    }
}
