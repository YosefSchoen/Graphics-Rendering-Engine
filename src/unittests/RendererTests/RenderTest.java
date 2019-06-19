package unittests.RendererTests;

import Elements.*;
import Geometries.Geometry;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Coordinate;
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
                Color amColor = new Color(150, 50, 250); //ambientLight parameter
                double Ka = 3; //ambientLight parameter
            AmbientLight ambientLight = new AmbientLight(); //scene parameter
                DirectionalLight directionalLight = new DirectionalLight(); //light parameter
                PointLight pointLight = new PointLight(); //light parameter
                SpotLight spotLight = new SpotLight(); //light parameter
            List<LightSource> lights = new ArrayList<>(); //scene parameter
                        Coordinate cS1x = new Coordinate(); //sphere1 parameter
                        Coordinate cS1y = new Coordinate(); //sphere1 parameter
                        Coordinate cS1z = new Coordinate(); //sphere1 parameter
                    Point3d pS1 = new Point3d(cS1x, cS1y, cS1z); //sphere1 parameter
                Sphere sphere1 = new Sphere(10, pS1); //geometry parameter
                        Coordinate cS2x = new Coordinate(); //sphere2 parameter
                        Coordinate cS2y = new Coordinate(); //sphere2 parameter
                        Coordinate cS2z = new Coordinate(); //sphere2 parameter
                    Point3d pS2 = new Point3d(cS2x, cS2y, cS2z); //sphere2 parameter
                Sphere sphere2 = new Sphere(7, pS2); //geometry parameter
                        Coordinate cTrx = new Coordinate(); //triangle parameter
                        Coordinate cTry = new Coordinate(); //triangle parameter
                        Coordinate cTrz = new Coordinate(); //triangle parameter
                    Point3d pTr = new Point3d(cTrx, cTry, cTrz); //triangle parameter
                Triangle triangle = new Triangle(); //geometry parameter
            List<Geometry> geometries = new ArrayList<Geometry>(); //scene parameter
                    Coordinate c0x = new Coordinate(); //p0 parameter
                    Coordinate c0y = new Coordinate(); //p0 parameter
                    Coordinate c0z = new Coordinate(); //p0 parameter
                Point3d p0 = new Point3d(c0x, c0x, c0z); //camera parameter
                        Coordinate cUpx = new Coordinate(); //vUP parameter
                        Coordinate cUpy = new Coordinate(); //vUP parameter
                        Coordinate cUpz = new Coordinate(); //vUP parameter
                    Point3d pUp = new Point3d(cUpx, cUpy, cUpz);
                Vector vUp = new Vector(pUp); //camera parameter
                        Coordinate cTox = new Coordinate(); //vTo parameter
                        Coordinate cToy = new Coordinate(); //vTo parameter
                        Coordinate cToz = new Coordinate(); //vTo parameter
                    Point3d pTo = new Point3d(cTox, cToy, cToz);
                Vector vTo = new Vector(pTo); //camera parameter
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


    public RenderTest() {
        lights.add(directionalLight);
        lights.add(pointLight);
        lights.add(spotLight);

        geometries.add(sphere1);
        geometries.add(sphere2);
        geometries.add(triangle);

        renderer.renderImage();
    }
}