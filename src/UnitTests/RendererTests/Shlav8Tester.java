package UnitTests.RendererTests;


import Elements.AmbientLight;
import org.junit.Test;
import java.awt.Color;
import Elements.PointLight;
import Elements.SpotLight;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Material;
import Primitives.Point3d;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Renderer;
import Scene.Scene;

public class Shlav8Tester {
	@Test
	public void pointLightTest1(){
		
		Scene scene = new Scene();
		scene.setScreenDistance(100);
		scene.setBackground(new Color(60, 60, 60));



		double radius = 800;
		Point3d center = new Point3d(0.0 ,0.0 ,-1000.0);
		Sphere sphere = new Sphere(radius, center);

		Color emissions = new Color(0,0,100);
		sphere.setEmission(emissions);

		Material m = new Material(0.01, 0.01, 0.01, 0.01, 1);
        //Material m = new Material(1, 1, 1, 1, 1);
		m.setNShininess(2);
		sphere.setMaterial(m);

        scene.addGeometry(sphere);

		Color amColor = new Color(50, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.5);
        scene.setAmbientLight(ambientLight);

		Color colorPL = new Color(255, 150, 255);
		Point3d pointPL = new Point3d(-200, -200, -150);
		PointLight pointLight = new PointLight(colorPL, pointPL, 0.1, 0.1, 0.1);
		scene.addLight(pointLight);

		ImageWriter imageWriter = new ImageWriter("Point Test1", 500, 500, 500, 500);
		
		Renderer render = new Renderer(scene, imageWriter);
		
		render.renderImage();
        //render.printGrid(50);
        //imageWriter.writeToimage();
	}


	@Test
	public void pointLightTest2(){
		Scene scene = new Scene();
		scene.setScreenDistance(100);
		scene.setBackground(new Color(60, 60, 60));

		double radius = 800;
		Point3d center = new Point3d(0,0, -1000);
		Sphere sphere = new Sphere (radius, center);

		Color emissionsS = new Color(0,0,100);
		sphere.setEmission(emissionsS);

		Material m = new Material();
		m.setNShininess(2);
		sphere.setMaterial(m);
		
		Point3d pointT11 = new Point3d(3500, 3500, -2000);
		Point3d pointT12 = new Point3d(-3500, -3500, -1000);
		Point3d pointT13 = new Point3d(3500, -3500, -2000);

		Triangle triangle = new Triangle(pointT11, pointT12,  pointT13, new Material(), new Color(0, 0, 0));

		Color emissionsT1 = new Color(0,0,0);
		triangle.setEmission(emissionsT1);

		Point3d pointT21 = new Point3d(3500, 3500, -2000);
		Point3d pointT22 = new Point3d(-3500, 3500, -1000);
		Point3d pointT23 = new Point3d( -3500, -3500, -1000);

		Triangle triangle2 = new Triangle(pointT21, pointT22, pointT23, new Material(), new Color(0, 0, 0));

		Color emissionsT2 = new Color(0,0,0);
		triangle2.setEmission(emissionsT2);

		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

        Color amColor = new Color(50, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.2);
        scene.setAmbientLight(ambientLight);

		Color colorPL = new Color(60,60,100);
		Point3d pointPL = new Point3d(200,200, -100);
		PointLight pointLight = new PointLight(colorPL, pointPL, 0.1, 0.1, 0.1);
		scene.addLight(pointLight);

		
		ImageWriter imageWriter = new ImageWriter("Point Test2", 500, 500, 500, 500);
		Renderer render = new Renderer(scene, imageWriter);
		
		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}
	
	

	@Test
	public void spotLightTest1(){
		Scene scene = new Scene();
		scene.setScreenDistance(100);
        scene.setBackground(new Color(60, 60, 60));

		double radius = 800;
		Point3d center = new Point3d(0,0, -1000);
		Sphere sphere = new Sphere (radius, center);

		Color color = new Color(0,0,100);
		sphere.setEmission(color);

		Material m = new Material();
		m.setNShininess(20);
		sphere.setMaterial(m);

		scene.addGeometry(sphere);

        Color amColor = new Color(50, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.5);
        scene.setAmbientLight(ambientLight);

		Color colorSL = new Color(255, 100, 100);
		Point3d pointSL = new Point3d(-200, -200, -150);
		Vector vectorSL =  new Vector(2, 2, -3);
		SpotLight spotLight = new SpotLight(colorSL, pointSL, 0.1, 0.1, 0.1, vectorSL);
		scene.addLight(spotLight);
	
		ImageWriter imageWriter = new ImageWriter("Spot Test1", 500, 500, 500, 500);
		
		Renderer render = new Renderer(scene, imageWriter);
		
		render.renderImage();
        //render.printGrid(50);
        //imageWriter.writeToimage();
	}
	
	@Test
	public void spotLightTest2(){
		Scene scene = new Scene();
		scene.setScreenDistance(200);
        scene.setBackground(new Color(60, 60, 60));

		double radius = 500;
		Point3d center = new Point3d(0,0,-1000);
		Sphere sphere = new Sphere (radius, center);

		Color color = new Color(0,0,100);
		sphere.setEmission(color);
		Material m = new Material();
		m.setNShininess(2);
		sphere.setMaterial(m);
		scene.addGeometry(sphere);

		Point3d pointT1 = new Point3d(-125, -225, -260);
		Point3d pointT2 = new Point3d(-225, -125, -260);
		Point3d pointT3 = new Point3d(-225, -225, -270);

		Triangle triangle = new Triangle(pointT1, pointT2, pointT3, new Material(), new Color(0, 0, 0));

		Color color1 = new Color (0, 0, 100);
		triangle.setEmission(color1);

		Material m1 = new Material();
		m1.setNShininess(2);
		triangle.setMaterial(m);
		scene.addGeometry(triangle);

        Color amColor = new Color(150, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.5);
        scene.setAmbientLight(ambientLight);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3d(-200, -200, -150), 0.1, 0.1, 0.1,   new Vector(2, 2, -3)));

		ImageWriter imageWriter = new ImageWriter("Spot Test2", 500, 500, 500, 500);

		Renderer render = new Renderer(scene, imageWriter);



		render.renderImage();
		//render.printGrid(50);
		//imageWriter.writeToimage();
	}

	@Test
	public void spotLightTest3(){
		
		
		Scene scene = new Scene();
		scene.setScreenDistance(100);
        scene.setBackground(new Color(0, 0, 0));

		Triangle triangle = new Triangle(new Point3d(3500, 3500, -2000), new Point3d( -3500, -3500, -1000), new Point3d(  3500, -3500, -2000), new Material(), new Color(250, 0, 0));
		Color emissions1 = new Color(0,100,100);
		triangle.setEmission(emissions1);

		Triangle triangle2 = new Triangle(new Point3d(3500, 3500, -2000), new Point3d( -3500,  3500, -1000), new Point3d( -3500, -3500, -1000), new Material(), new Color(0, 0, 0));
		Color emissions2 = new Color(0,0,0);
		triangle2.setEmission(emissions2);

		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

        Color amColor = new Color(150, 50, 50);
        AmbientLight ambientLight = new AmbientLight(amColor, 0.5);
        scene.setAmbientLight(ambientLight);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3d(200, 200, -100), 0.1, 0.1, 0.1, new Vector(-2, -2, -3)));
	
		
		ImageWriter imageWriter = new ImageWriter("Spot Test3", 500, 500, 500, 500);
		
		Renderer render = new Renderer(scene, imageWriter);
		
		render.renderImage();
	    render.printGrid(50);
		imageWriter.writeToimage();
		
	}
	
	
}
