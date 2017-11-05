package org.max.gen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class testGen {

    private static Workarea workarea;
    private static Color[][] sourceColors;
    private Thread thread;
    private static int num;
    private static int step;
    private static boolean isRunning = false;
    private static double fitness;
    private static String path;


    public static void main(String args[]) throws IOException {
        File file= new File("C:\\Users\\User\\Pictures\\maxresdefault.jpg");
        path = "C:\\Users\\User\\Pictures\\maxresdef\\";
        BufferedImage image = ImageIO.read(file);

        Helper.Width = image.getWidth();
        Helper.Height = image.getHeight();
        sourceColors = new Color[image.getWidth()][image.getHeight()];


        for (int i = 0; i < image.getWidth(); i++)
        {
            for (int j = 0; j < image.getHeight(); j++)
            {
                int  clr=  image.getRGB(i,j);
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;
                Color c1 = new Color(red, green , blue);
                sourceColors[i][j] = c1;
            }
        }

        workarea = null;
        num = 0;
        step = 1;
        fitness = Double.MAX_VALUE;


        if (workarea == null)
        {
            workarea = new Workarea();
            workarea.SetRandom();
        }

        if (sourceColors.length>1) {
            isRunning = true;
        }


        while (isRunning)
        {
            Workarea newarea;
                newarea = (Workarea) workarea.clone();
            newarea.Mutate();

            if (newarea.IsChange)
            {
                double newfitness = newarea.Fitness(sourceColors);

                if (newfitness <= fitness)
                {
                        workarea = newarea;
                    fitness = newfitness;

                    if (step++ % 5 == 0)
                    {
                        System.out.println("fitness: "+fitness + " polygons: "+workarea.Polygons.size());
                        Save();
                        step = 1;
                    }
                }
            }
        }
    }


    public static void Save()
    {
        BufferedImage img = workarea.Draw();
        try
        {
            ImageIO.write(img, "jpg", new File(path + String.valueOf(num++)+".jpg"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
