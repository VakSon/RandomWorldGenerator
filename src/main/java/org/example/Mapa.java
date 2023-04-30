package org.example;

public class Mapa {
    public int vyska = 600;
    public int sirka = 800;
    public String seed = null;
    public int octaveCount = 8;
    public float[][] Heightmap = null;
    void setter(int s, int v, int o, String see){
        vyska = v;
        sirka = s;
        octaveCount = o;
        seed = see;
    }
    void Build_Heighmap(){
//        Heightmap = perlin_noise.GenerateSmoothNoise(perlin_noise.GenerateWhiteNoise(sirka,vyska,garbagefunctions.Seed2Long(seed)),8);
        Heightmap = perlinNoise.WholeNoise(seed,sirka,vyska,octaveCount);
    }
}
