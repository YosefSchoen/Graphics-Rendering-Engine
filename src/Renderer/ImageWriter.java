/**
 * Note from Yosef B
 * I have absolutely no idea what Nx and Ny are.
 * I'm currently electing to just ignore them.
 *
 * Nx is number of pixels in the x direction
 * Ny is the same but in the y direction
 */
package Renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.imageio.stream.*;

public class ImageWriter {
    // ***************** Constructors ********************** //
    public ImageWriter(String imageName, int width, int height, int Nx, int Ny) {
        _imageName = imageName; // Hold the name of the output file (without the extension)
        _imageWidth = width;    // Hold the horizontal resolution (I think)
        _imageHeight = height;  // Hold the vertical resolution (I think)
        _Nx = Nx;
        _Ny = Ny;

        // Hold the actual image (before it is written to disk)
        //_image = new BufferedImage(_imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);
        _image = new BufferedImage(_Nx, _Ny, BufferedImage.TYPE_INT_RGB);
    }

    public ImageWriter (ImageWriter imageWriter) {
        this(	imageWriter._imageName,
                imageWriter._imageWidth, imageWriter._imageHeight,
                imageWriter._Nx, imageWriter._Ny);
    }

    // ***************** Getters/Setters ********************** //

    public int getWidth()  { return _imageWidth;  }
    public int getHeight() { return _imageHeight; }

    public int getNy() { return _Ny; }
    public int getNx() { return _Nx; }

    public void setNy(int _Ny) { this._Ny = _Ny; }
    public void setNx(int _Nx) { this._Nx = _Nx; }

    // ***************** Operations ******************** //

    public void writeToimage(){
        File ouFile = new File(PROJECT_PATH + "/" + _imageName + ".jpg");
        try {
            javax.imageio.ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(1f);
            jpgWriter.setOutput(new FileImageOutputStream(ouFile));
            jpgWriter.write(null,new IIOImage(_image, null, null), jpgWriteParam);
            //ImageIO.write(_image, "jpg", ouFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePixel(int xIndex, int yIndex, int r, int g, int b){
        int rgb = new Color(r, g, b).getRGB();
        _image.setRGB(xIndex, yIndex, rgb);
    }

    public void writePixel(int xIndex, int yIndex, int[] rgbArray){
        int rgb = new Color(rgbArray[0], rgbArray[1], rgbArray[2]).getRGB();
        _image.setRGB(xIndex, yIndex, rgb);
    }

    public void writePixel(int xIndex, int yIndex, Color color){
        _image.setRGB(xIndex, yIndex, color.getRGB());
    }

    private int _imageWidth, _imageHeight;
    private int _Nx, _Ny;

    final String PROJECT_PATH = System.getProperty("user.dir"); // Write the image to the user's home dir (I think)
    //final String PROJECT_PATH = "../"; // Write the image to the parent directory, not sure where it'll end up though

    private BufferedImage _image;

    private String _imageName;

}