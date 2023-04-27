package org.example;

public class Mapa {
    public static int vyska = 600;
    public static int sirka = 800;
    public static String seed = null;
    public static int octaveCount = 8;
    public static float[][] Heightmap = null;
    void setter(int s, int v, int o, String see){
        vyska = v;
        sirka = s;
        octaveCount = o;
        seed = see;
    }
    void Build_Heighmap(){
        Heightmap = perlin_noise.WholeNoise(seed,sirka,vyska,octaveCount);
    }
}
