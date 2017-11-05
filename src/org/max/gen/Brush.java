package org.max.gen;

import java.io.Serializable;

import static org.max.gen.Helper.rDouble;
import static org.max.gen.Helper.rInt;

public class Brush implements Cloneable, Serializable {

    public int A;
    public int R;
    public int G;
    public int B;

    public Brush(int a, int r, int g, int b) {
        this.A = a;
        this.R = r;
        this.G = g;
        this.B = b;
    }

    public Brush() {
    }

    public void SetRandom()
    {
        A = rInt(30, 61); // Is need add +1 to bound?
        R = rInt(0, 256);
        G = rInt(0, 256);
        B = rInt(0, 256);
    }


    public void Mutate(Workarea workarea) {
        if (rDouble(1.0) <= Helper.MutationBrushAChance)
        {
            A = rInt(30, 61);
            workarea.IsChange = true;
        }

        if (rDouble(1.0) <= Helper.MutationBrushRChance)
        {
            R = rInt(0, 256);
            workarea.IsChange = true;
        }

        if (rDouble(1.0) <= Helper.MutationBrushGChance)
        {
            G = rInt(0, 256);
            workarea.IsChange = true;
        }

        if (rDouble(1.0) <= Helper.MutationBrushBChance)
        {
            B = rInt(0, 256);
            workarea.IsChange = true;
        }
    }

    @Override
    protected synchronized Object clone() {
        return new Brush(A,R,G,B);
    }
}
