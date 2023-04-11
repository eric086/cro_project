package com.mk.openai.util;

import java.util.Arrays;

public class DistanceCalculator {
    public static double cosine(double[] u, double[] v, double[] w) {
        // Compute dot product of u and v
        double dotProduct = 0.0;
        for (int i = 0; i < u.length; i++) {
            dotProduct += u[i] * v[i];
        }

        // Compute L2 norm of u and v
        double uNorm = 0.0;
        double vNorm = 0.0;
        for (int i = 0; i < u.length; i++) {
            uNorm += u[i] * u[i];
            vNorm += v[i] * v[i];
        }
        uNorm = Math.sqrt(uNorm);
        vNorm = Math.sqrt(vNorm);

        // Compute cosine distance
        double distance = 1.0 - dotProduct / (uNorm * vNorm);

        return distance;
    }
}
