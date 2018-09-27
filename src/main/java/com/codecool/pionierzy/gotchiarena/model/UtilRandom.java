package com.codecool.pionierzy.gotchiarena.model;

import java.util.Random;

public class UtilRandom {

    private Random generator = new Random();

    public double doubleFromRange(double a, double b){
        return a + (b - a)*generator.nextDouble();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
