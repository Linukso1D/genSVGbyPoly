package org.max.gen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Workarea implements Cloneable, Serializable {
    public List<Polygon> Polygons;

    public boolean IsChange;

    public void SetRandom() {
        Polygons = new ArrayList<>();

        for (int i = 0; i < Helper.MinPolygons; i++)
        {
            AddPolygon();
        }

        IsChange = true;
    }

    public void Mutate()
    {
        if (Helper.rDouble(1.0) <= Helper.MutationWorkareaAddPolygonChance)
        {
            AddPolygon();
            IsChange = true;
        }

        if (Helper.rDouble(1.0) <= Helper.MutationWorkareaDelPolygonChance)
        {
            DelPolygon();
            IsChange = true;
        }

        Polygons.forEach(p -> p.Mutate(this));
    }

    private void AddPolygon()
    {
        if (Polygons.size() < Helper.MaxPolygons)
        {
            Polygon polygon = new Polygon();
            polygon.SetRandom();
            Polygons.add(polygon);
        }
    }

    private void DelPolygon()
    {
        if (Polygons.size() > Helper.MinPolygons)
        {
            int index = Helper.rInt(0, Polygons.size());
            Polygons.remove(index);
        }
    }

    public double Fitness(Color[][] colors)
    {
        double fitness = 0;
        BufferedImage img = Draw();


        for (int i = 0; i < Helper.Width; i++)
        {
            for (int j = 0; j < Helper.Height; j++)
            {

                int clr =  img.getRGB(i,j);
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;

                Color c1 = new Color(red,green,blue);
                Color c2 = colors[i][j];

                int r = c1.getRed() - c2.getRed();
                int g = c1.getGreen() - c2.getGreen();
                int b = c1.getBlue() - c2.getBlue();

                fitness += r * r + g * g + b * b;
            }
        }

        return fitness;
    }

    public BufferedImage Draw()
    {
        BufferedImage img = new BufferedImage(Helper.Width, Helper.Height, TYPE_INT_ARGB);

        Graphics g = img.createGraphics();
        for(Polygon p :Polygons) {
            p.Draw(g);
        }
        return img;
    }


    @Override
    public synchronized Object clone() {
        Workarea newarea = new Workarea();
        newarea.Polygons = new ArrayList<Polygon>();
        Polygons.forEach((p) -> newarea.Polygons.add((Polygon) p.clone()));
        return newarea;
    }

    @Override
    public String toString() {
        return "Workarea{" +
                "Polygons=" + Polygons +
                ", IsChange=" + IsChange +
                '}';
    }
}
