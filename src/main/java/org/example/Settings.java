package org.example;

public class Settings {
    //basic
    public static int sizeY = 600;
    public static int sizeX = 800;
    public static String seedString = "fdjaofheakcves";
    public final static long seed = garbagefunctions.Seed2Long(seedString);
    //perlin

    public static int octaves = 7;
    public static float persistance = 0.55f;

    //plates
    public static int plateSum = 30;


    //hydraulic erosion
    public static int dropsNum = 50000;

}
