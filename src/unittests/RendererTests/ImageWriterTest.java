package unittests.RendererTests;

import Renderer.ImageWriter;

import java.lang.System;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;
import javax.imageio.stream.*;


public class ImageWriterTest {
    @org.junit.Test
    public void writeImageTest() {
        String imageName = "Image Test";
        int imageWidth = 50;
        int imageHeight = 50;
        int Nx = 1000;
        int Ny = 1000;

        ImageWriter testImage = new ImageWriter(imageName, imageWidth, imageHeight, Nx, Ny);
    }

    // Write image with lines
    @org.junit.Test
    public void linesTest() {
        System.out.println("HI!");
        String imageName = "Line Test";
        int imageWidth = 300;
        int imageHeight = 300;

        ImageWriter testImage = new ImageWriter(imageName, imageWidth, imageHeight, 500, 500);

        int numX = 4;
        int numY = 4;

        int deltX = imageWidth / (numX + 1);
        int deltY = imageHeight / (numY + 1);

        int xCount = deltX;
        int yCount = deltY;

        for(int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                if(x == xCount) {
                    testImage.writePixel(x, y, 255, 255, 255);
                    xCount += deltX;
                }
                else if(y == yCount) {
                    testImage.writePixel(x, y, 255, 255, 255);
                }
                else {
                    testImage.writePixel(x, y, 50, 50, 0);
                }
            }
            if (y == yCount) {
                yCount += deltY;
            }
            xCount = deltX;
        }

        testImage.writeToimage();
    }
}
