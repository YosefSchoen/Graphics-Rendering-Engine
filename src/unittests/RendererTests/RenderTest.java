package RendererTests;

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class RenderTest {
    Color color = new Color(0, 0, 0);

            String sceneName = "scene test"; //scene parameter
            Color background = new Color(0, 0, 0); //scene parameter

                //Color amColor = new Color(150, 50, 250); //ambientLight parameter
                Color amColor = new Color(10, 10, 10); //ambientLight parameter
                double Ka = 3; //ambientLight parameter
            AmbientLight ambientLight = new AmbientLight(amColor, Ka); //scene parameter
                DirectionalLight directionalLight = new DirectionalLight(new Color(0, 255, 255), new Vector(-.5, -.5, 0)); //light parameter
                PointLight pointLight = new PointLight(new Color(0, 255, 0), new Point3d(1, 2, -40), .5, .5, .5); //light parameter
                SpotLight spotLight = new SpotLight(new Color(255, 255, 255), new Point3d(), 50, 50, 50, new Vector(0, 0, -1)); //light parameter
            List<LightSource> lights = new ArrayList<>(); //scene parameter

                Material spm = new Material(0.5, 0.5, 0.5, 0, 2);
                Sphere sphere1 = new Sphere(3, new Point3d(0, 0, -49)); //geometry parameter
                Sphere sphere2 = new Sphere(3, new Point3d(3, 3, -100)); //geometry parameter
                    Point3d pTr = new Point3d(2, 2, -3); //triangle parameter
                Triangle triangle = new Triangle(new Point3d(30, 0, -49), new Point3d(0, 30, -49), new Point3d(30, 30, -49), new Material(), new Color(0, 255, 0)); //geometry parameter
            List<Geometry> geometries = new ArrayList<Geometry>(); //scene parameter

                Point3d p0 = new Point3d(); //camera parameter
                Vector vUp = new Vector(new Point3d(0, 1, 0)); //camera parameter
                Vector vTo = new Vector(new Point3d(0, 0, -1)); //camera parameter
            Camera camera = new Camera(p0, vUp, vTo); //scene parameter

            double screenDistance = 200; //scene parameter
        Scene scene = new Scene(sceneName, background, ambientLight, lights, geometries, camera, screenDistance); //Renderer parameters
            String imageName = "test"; //ImageWriters parameter
            int width = 100; //ImageWriters parameter
            int height = 100; //ImageWriters parameter
            int Nx = 500; //ImageWriters parameter
            int Ny = 500; //ImageWriters parameter
        ImageWriter imageWriter = new ImageWriter(imageName, width, height, Nx, Ny); //Renderer parameters
    Renderer renderer = new Renderer(scene, imageWriter);

    @org.junit.Test
    public void RenderTest() {
        //lights.add(directionalLight);
        lights.add(pointLight);
        //lights.add(spotLight);

        sphere1.setMaterial(new Material(0.5, 0.5, 0.5, 0, 2));
        sphere1.setEmission(new Color(0, 0, 255));

        sphere2.setEmission(new Color(100, 0, 0));

        //triangle.setEmission(new Color(255, 0, 0));

        Point3d directionPoint = new Point3d(0, 1.5, -1);
        Point3d planePoint = new Point3d(0, 0, -60);
        Vector direction = new Vector(directionPoint);
        Plane plane2 = new Plane(planePoint, direction, new Material(), new Color(10, 10, 100));

        geometries.add(sphere1);
        geometries.add(sphere2);
        geometries.add(triangle);
        //geometries.add(new Plane(triangle.getP1(), triangle.getNormal(null)));
        //geometries.add(plane2);

        renderer.renderImage();
    }

    @org.junit.Test
    public  void test2() {
        Camera c = new Camera(new Point3d(), new Vector(0, -1, 0), new Vector(0, 0, -1));

        Sphere sphere = new Sphere(25, new Point3d(0, 0, -50));
        sphere.setEmission(new Color(0, 255, 0));
        Triangle t1 = new Triangle(new Point3d(100, 0, -49), new Point3d(0, 100, -49), new Point3d(100, 100, -49), new Material(), new Color(0, 0, 255));
        Triangle t2 = new Triangle(new Point3d(-100, 0, -49), new Point3d(0, 100, -49), new Point3d(-100, 100, -49), new Material(), new Color(0, 255, 255));
        Triangle t3 = new Triangle(new Point3d(100, 0, -49), new Point3d(0, -100, -49), new Point3d(100, -100, -49), new Material(), new Color(255, 0, 255));
        Triangle t4 = new Triangle(new Point3d(-100, 0, -49), new Point3d(0, -100, -49), new Point3d(-100, -100, -49), new Material(), new Color(255, 255, 0));

        AmbientLight am = new AmbientLight(new Color(100, 100, 100), 1);

        List<Geometry> geometrieslist = new ArrayList<>();
        geometrieslist.add(sphere);
        geometrieslist.add(t1);
        geometrieslist.add(t2);
        geometrieslist.add(t3);
        geometrieslist.add(t4);

        PointLight pl = new PointLight(new Color(255, 0, 0), new Point3d(50, 50, -60), 0.5, 0.5, 0.5);

        List<LightSource> lightlist = new ArrayList<>();
        lightlist.add(pl);

        Scene s = new Scene(sceneName, new Color(75, 127, 190), am, lightlist, geometrieslist, c, 50);
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
}
