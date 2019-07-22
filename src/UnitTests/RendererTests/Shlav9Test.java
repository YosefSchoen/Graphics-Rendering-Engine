package UnitTests.RendererTests;

import org.junit.Test;

import java.awt.Color;


import Elements.SpotLight;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Renderer;
import Scene.Scene;



public class Shlav9Test {
        @Test
        public void recursiveTest(){

            Scene scene = new Scene();
            scene.setScreenDistance(300);

            Sphere sphere = new Sphere(500, new Point3d(0.0, 0.0, -1000));
            Color sColor = new Color(0, 0, 100);
            sphere.setEmission(sColor);
            Material m=new Material();
            m.setNShininess(20);
            m.setKt(0.5);
            sphere.setMaterial(m);
            scene.addGeometry(sphere);

            Sphere sphere2 = new Sphere(250, new Point3d(0.0, 0.0, -1000));
            Color sColor2 = new Color(100, 20, 20);
            sphere2.setEmission(sColor2);
            Material m2=new Material();
            m2.setNShininess(20);
            m2.setKt(0);
            sphere2.setMaterial(m2);
            scene.addGeometry(sphere2);

            scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3d(-200, -200, -150) , 0.1, 0.00001, 0.000005, new Vector(2, 2, -3)));

            ImageWriter imageWriter = new ImageWriter("Recursive Test", 500, 500, 500, 500);

            Renderer renderer = new Renderer(scene, imageWriter);

            renderer.renderImage();

        }


        @Test
        public void recursiveTest2(){

            Scene scene = new Scene();
            scene.setScreenDistance(300);

            Sphere sphere = new Sphere(300, new Point3d(-550, -500, -1000));
            Color sColor = new Color(0, 0, 100);
            sphere.setEmission(sColor);
            Material m=new Material();
            m.setNShininess(20);
            m.setKt(0.5);
            sphere.setMaterial(m);
            scene.addGeometry(sphere);

            Sphere sphere2 = new Sphere(150, new Point3d(-550, -500, -1000));
            Color sColor2 = new Color(100, 20, 20);
            sphere2.setEmission(sColor2);
            Material m2=new Material();
            m2.setNShininess(20);
            m2.setKt(0);
            sphere2.setMaterial(m2);
            scene.addGeometry(sphere2);

            Triangle triangle = new Triangle(new Point3d(1500, -1500, -1500), new Point3d( -1500,  1500, -1500), new Point3d(  200,  200, -375));
            Color tColor = new Color(20, 20, 20);
            triangle.setEmission(tColor);
            Material m3=new Material();
            m3.setKr(1);
            triangle.setMaterial(m3);
            scene.addGeometry(triangle);

            Triangle triangle2 = new Triangle(new Point3d(1500, -1500, -1500), new Point3d( -1500,  1500, -1500), new Point3d( -1500, -1500, -1500));
            Color tColor2 = new Color(20, 20, 20);
            triangle2.setEmission(tColor2);
            Material m4=new Material();
            m4.setKr(0.5);
            triangle2.setMaterial(m4);
            scene.addGeometry(triangle2);

            scene.addLight(new SpotLight(new Color(255, 100, 100),  new Point3d(200, 200, -150), 0, 0.00001, 0.000005, new Vector(-2, -2, -3)));

            ImageWriter imageWriter = new ImageWriter("Recursive Test 2", 500, 500, 500, 500);

            Renderer renderer = new Renderer(scene, imageWriter);

            renderer.renderImage();

        }

        @Test
        public void recursiveTest3(){

            Scene scene = new Scene();
            scene.setScreenDistance(300);

            Sphere sphere = new Sphere(300, new Point3d(0, 0, -1000));
            Color sColor = new Color(0, 0, 100);
            sphere.setEmission(sColor);
            Material m=new Material();
            m.setNShininess(20);
            m.setKt(0.5);
            sphere.setMaterial(m);
            scene.addGeometry(sphere);

            Sphere sphere2 = new Sphere(150, new Point3d(0, 0, -1000));
            Color sColor2 = new Color(100, 20, 20);
            sphere2.setEmission(sColor2);
            Material m1=new Material();
            m1.setNShininess(20);
            m1.setKt(0);
            sphere2.setMaterial(m1);
            scene.addGeometry(sphere2);

            Triangle triangle = new Triangle(new Point3d(2000, -1000, -1500), new Point3d(-1000,  2000, -1500), new Point3d(700,  700, -375));
            Color tColor = new Color(20, 20, 20);
            triangle.setEmission(tColor);
            Material m2=new Material();
            m2.setKr(1);
            triangle.setMaterial(m2);
            scene.addGeometry(triangle);

            Triangle triangle2 = new Triangle(new Point3d(2000, -1000, -1500), new Point3d(-1000,  2000, -1500), new Point3d(-1000, -1000, -1500));
            Color tColor2 = new Color(20, 20, 20);
            triangle2.setEmission(tColor2);
            Material m3=new Material();
            m3.setKr(0.5);
            triangle2.setMaterial(m3);
            scene.addGeometry(triangle2);

            scene.addLight(new SpotLight(new Color(255, 100, 100),  new Point3d(200, 200, -150), 0, 0.00001, 0.000005, new Vector(-2, -2, -3)));

            ImageWriter imageWriter = new ImageWriter("Recursive Test 3", 500, 500, 500, 500);

            Renderer renderer = new Renderer(scene, imageWriter);

            renderer.renderImage();
        }
    }
