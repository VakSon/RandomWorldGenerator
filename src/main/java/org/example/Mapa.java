package org.example;

public class Mapa {
    public int vyska = 600;
    public int sirka = 800;
    public String seed = null;
    public int octaveCount = 8;
    public float[][] heightMap = null;
    public float[][] temperatureMap = null;
    int equator = vyska/2;
    void setter(int sirka, int vyska, int octaves, String seed){
        this.vyska = vyska;
        this.sirka = sirka;
        this.octaveCount= octaves;
        this.seed = seed;
        equator = vyska/2;
    }
    void Init(){
        Build_Heighmap();
        Build_TemperatureMap();
    }
    void Build_Heighmap(){
//        Heightmap = perlin_noise.GenerateSmoothNoise(perlin_noise.GenerateWhiteNoise(sirka,vyska,garbagefunctions.Seed2Long(seed)),8);
//        heightMap = perlinNoise.WholeNoise(seed,sirka,vyska,octaveCount);
        heightMap = VoroniDiagram.idknamesafteritworks(15,sirka,vyska,seed);
    }
    void Build_TemperatureMap(){
        temperatureMap = garbagefunctions.TempMap(vyska,sirka,equator);
    }
}
