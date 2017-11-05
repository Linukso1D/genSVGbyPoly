package org.max.gen;

import java.util.concurrent.ThreadLocalRandom;

public class Helper
    {
        public static int Width = 0;
        public static int Height = 0;

        public static int MinPointsPerPolygon = 3;
        public static int MaxPointsPerPolygon = 10;

        public static int MinPolygons = 0;
        public static int MaxPolygons = 100;

        public static int NearRange = 3;
        public static int MiddleRange = 20;

        public static double MutationPointMoveMaxChance = 0.0007;
        public static double MutationPointMoveMiddleChance = 0.0007;
        public static double MutationPointMoveNearChance = 0.0007;

        public static double MutationBrushAChance = 0.0007;
        public static double MutationBrushRChance = 0.0007;
        public static double MutationBrushGChance = 0.0007;
        public static double MutationBrushBChance = 0.0007;

        public static double MutationPolygonAddPointChance = 0.0007;
        public static double MutationPolygonDelPointChance = 0.0007;

        public static double MutationWorkareaAddPolygonChance = 0.0014;
        public static double MutationWorkareaDelPolygonChance = 0.0007;

        private Helper() {}

        public static int rInt(int start, int end) {
            return ThreadLocalRandom.current().nextInt(start, end);
        }

        public static double rDouble(double max) {
            return ThreadLocalRandom.current().nextDouble(max);
        }
    }

