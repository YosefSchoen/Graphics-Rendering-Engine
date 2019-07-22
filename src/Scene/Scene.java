package Scene;

import Geometries.Geometry;
import Elements.LightSource;
import Elements.AmbientLight;
import Elements.Camera;

import java.awt.Color;
import java.util.*;

//the scene class will hold all of the lights and geometries in the scene
public class Scene {

    //empty constructor
    public Scene() {
        this.sceneName = "";
        this.background = new Color(60, 60, 60);
        this.ambientLight = new AmbientLight();
        this.lights = new ArrayList<>();
        this.geometries = new ArrayList<>();
        this.camera = new Camera();
        this.screenDistance = 0;
    }

    //constructor
    public Scene(String sceneName, Color background, AmbientLight ambientLight,List<LightSource> lights, List<Geometry> geometries, Camera camera, double screenDistance) {
        this.sceneName = sceneName;
        this.background = background;
        this.ambientLight = ambientLight;
        this.lights  = lights;
        this.geometries = geometries;
        this.camera = camera;
        this.screenDistance = screenDistance;
    }

    //copy constructor
    public Scene(Scene other) {
        this.sceneName = other.sceneName;
        this.background = other.background;
        this.ambientLight = other.ambientLight;
        this.lights = other.lights;
        this.geometries = other.geometries;
        this.camera = other.camera;
        this.screenDistance = other.screenDistance;
    }

    //getters
    public String getSceneName() {
        return sceneName;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Iterator<LightSource> getLightsIterator() {
        return lights.iterator();
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

    public Iterator<Geometry> getGeometriesIterator() {
        return this.geometries.iterator();
    }

    public Camera getCamera() {
        return camera;
    }

    public double getScreenDistance() {
        return screenDistance;
    }


    //setters
    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setScreenDistance(double screenDistance) {
        this.screenDistance = screenDistance;
    }

    //add a geometry to the list geometries
    public void addGeometry(Geometry newGeometry) {
        geometries.add(newGeometry);
    }

    //add a light source to the list lights
    public void  addLight(LightSource newLightSource) {lights.add(newLightSource);}


    //every scene has a name, background color, ambient light, list of other light sources, list of geometries, a camera, and screen distance
    private String sceneName;
    private Color background;
    private AmbientLight ambientLight;
    private List<LightSource> lights;
    private List<Geometry> geometries;
    private Camera camera;
    private double screenDistance;
}
