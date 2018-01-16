package org.max.gen;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Polygon implements Cloneable, Serializable {

    public List<Point> Points;
    public Brush Brush;

    public void SetRandom()
    {
        Points = new ArrayList<Point>();

        Point center = new Point();
        center.SetRandom();

        for (int i = 0; i < Helper.MinPointsPerPolygon; i++)
        {
            Point point = new Point();
            point.X = Math.min(Math.max(0, center.X + Helper.rInt(-3, 4)), Helper.Width);
            point.Y = Math.min(Math.max(0, center.X + Helper.rInt(-3, 4)), Helper.Height);
            Points.add(point);
        }

        Brush = new Brush();
        Brush.SetRandom();
    }

    public void Mutate(Workarea workarea)
    {
        if (Helper.rDouble(1.0) <= Helper.MutationPolygonAddPointChance)
        {
            AddPoint();
            workarea.IsChange = true;
        }

        if (Helper.rDouble(1.0)  <= Helper.MutationPolygonDelPointChance)
        {
            DelPoint();
            workarea.IsChange = true;
        }

        Brush.Mutate(workarea);
        Points.forEach((Point p) ->p.Mutate(workarea));
    }

    private void AddPoint()
    {
        if (Points.size() < Helper.MaxPointsPerPolygon)
        {
            Point point = new Point();
            int index = Helper.rInt(1, Points.size() - 1);
            Point p1 = Points.get(index - 1);
            Point p2 = Points.get(index);
            point.X = (p1.X + p2.X) / 2;
            point.Y = (p1.Y + p2.Y) / 2;
            Points.add(index, point);
        }
    }

    private void DelPoint()
    {
        if (Points.size() > Helper.MinPointsPerPolygon)
        {
            int index = Helper.rInt(0, Points.size());
            Points.remove(index);
        }
    }

    public void Draw(Graphics g)
    {
            int npoints = Points.size();
            int xpoints[] = new int[npoints];
            int ypoints[] = new int[npoints];

            for (int i = 0; i<npoints; i++) {
                xpoints[i] = Points.get(i).X;
                ypoints[i] = Points.get(i).Y;
            }

            java.awt.Polygon p = new java.awt.Polygon(xpoints,ypoints,npoints);

            g.setColor(new Color (Brush.R, Brush.G, Brush.B,Brush.A));
          //  g.drawPolygon(p);
            g.fillPolygon(p);
          //  g.dispose();

    }

    @Override
    protected Object clone() {
        Polygon newpolygon = new Polygon();
        newpolygon.Brush = (org.max.gen.Brush) Brush.clone();
        newpolygon.Points = new ArrayList<>();
        for (Point p : Points) {
            newpolygon.Points.add((Point) p.clone());
        }
     //   Points.forEach((Point p) -> newpolygon.Points.add((Point) p.clone()));
        return newpolygon;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "Points=" + Points +
                ", Brush=" + Brush +
                '}';
    }
}
