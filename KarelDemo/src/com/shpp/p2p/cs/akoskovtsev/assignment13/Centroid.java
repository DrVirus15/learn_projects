package com.shpp.p2p.cs.akoskovtsev.assignment13;

public class Centroid {
    private double a, r, g, b;

    public Centroid(double a, double r, double g, double b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public void update(double sumA, double sumR, double sumG, double sumB, int count) {
        if (count > 0) {
            a = sumA / count;
            r = sumR / count;
            g = sumG / count;
            b = sumB / count;
        }
    }
}
