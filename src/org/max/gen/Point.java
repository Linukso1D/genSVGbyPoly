package org.max.gen;

import java.util.concurrent.ThreadLocalRandom;

import static org.max.gen.Helper.rDouble;

public class Point implements Cloneable {
    public int X;
    public int Y;

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    public Point() {
    }

    public void SetRandom() {
        X = ThreadLocalRandom.current().nextInt(0, Helper.Width);
        Y = ThreadLocalRandom.current().nextInt(0, Helper.Height);
    }

    public void Mutate(Workarea workarea) {

        if (rDouble(1.0) <= Helper.MutationPointMoveMaxChance)
        {
            SetRandom();
            workarea.IsChange = true;
        }

        if (rDouble(1.0) <= Helper.MutationPointMoveMiddleChance)
        {
            X = Math.min(Math.max(0, X + Helper.rInt(-Helper.MiddleRange, Helper.MiddleRange + 1)), Helper.Width);
            Y = Math.min(Math.max(0, Y + Helper.rInt(-Helper.MiddleRange, Helper.MiddleRange + 1)), Helper.Height);
            workarea.IsChange = true;
        }

        if (rDouble(1.0) <= Helper.MutationPointMoveNearChance)
        {
            X = Math.min(Math.max(0, X + Helper.rInt(-Helper.NearRange, Helper.NearRange + 1)), Helper.Width);
            Y = Math.min(Math.max(0, Y + Helper.rInt(-Helper.NearRange, Helper.NearRange + 1)), Helper.Height);
            workarea.IsChange = true;
        }
    }

    @Override
    protected Object clone() {
        return new Point(X,Y);
    }
}
