package org.example;

public class Settings {
    //global
    public static int sizeY = 600;
    public static int sizeX = 800;

    public static float seaLevel = 0.5f;
    public static String seedString = "";
    public final static long seed = garbagefunctions.Seed2Long(seedString);

    //perlin
    public static int octaves = 7;
    public static float persistance = 0.55f;

    //diamond square
    public static float randomness = 0.5f;

    //hydraulic erosion
    public static int dropsNum = 100000;
    public static int maxLifeTime = 35;
    public static float evaporateSpeed = 0.05f;
    public static float water = 1f;
    public static int radius = 3;
    public static float inertia = 0.1f;
    public static float erosionSpeed = 0.3f;
    public static float depositionSpeed = 0.3f;
    public static float minSedimentCapacity = 0.01f;
    public static float sedimentCapacityFactor = 4;

}
