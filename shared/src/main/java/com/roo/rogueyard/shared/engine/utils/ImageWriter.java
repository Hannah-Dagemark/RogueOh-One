package com.roo.rogueyard.shared.engine.utils;
// Source - https://stackoverflow.com/a/17489575
// Posted by Richard Tingle, modified by community. See post 'Timeline' for change history
// Retrieved 2026-11-24, License - CC BY-SA 3.0

/* Some random image writer help function I found to be able to proof check my map generation */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageWriter {
    //just convinence methods for debug

    public static void greyWriteImage(double[][] data){
        //this takes and array of doubles between 0 and 1 and generates a grey scale image from them

        BufferedImage image = new BufferedImage(data.length,data[0].length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < data[0].length; y++)
        {
            for (int x = 0; x < data.length; x++)
            {
                if (data[x][y]>1){
                    data[x][y]=1;
                }
                if (data[x][y]<0){
                    data[x][y]=0;
                }
                Color col=new Color((float)data[x][y],(float)data[x][y],(float)data[x][y]);
                image.setRGB(x, y, col.getRGB());
            }
        }

        try {
            // retrieve image
            File outputfile = new File("saved.png");
            outputfile.createNewFile();

            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            //o no!
        }
    }

    public static void main(String[] args){

        //generates 100 by 100 data points within the specified range

        double iStart=0;
        double iEnd=500;
        double jStart=0;
        double jEnd=500;

        int width = 750;
        int height = 750;
        double distanceTaper = 7;

        double[][] result=new double[width][height];

        double scaleFactor = 60;

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                double iF = ((double)i / width)-0.5;
                double jF = ((double)j / height)-0.5;
                double distanceSquared = Math.max(iF*iF + jF*jF - 0.05, 0);
                double x=(iStart+i*((iEnd-iStart)/(width*scaleFactor)));
                double y=(jStart+j*((jEnd-jStart)/(height*scaleFactor)));
                result[i][j]=((SimplexNoise.noise(x,y, 51515)) + distanceSquared*distanceTaper > 0.25 ? 1 : 0);

            }
        }

        ImageWriter.greyWriteImage(result);
    }
}
