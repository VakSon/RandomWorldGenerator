package org.example;

public class Mapa {
    public int SizeY = 600;
    public int sizeX = 800;
    public String seed = null;
    public int octaveCount = 8;
    public float[][] heightMap = null;
    public float[][] temperatureMap = null;
    int equator = SizeY /2;
    void setter(int sirka, int vyska, int octaves, String seed){
        this.SizeY = vyska;
        this.sizeX = sirka;
        this.octaveCount= octaves;
        this.seed = seed;
        //equator = vyska/2;
    }
    void Init(){
        Build_Heighmap();
        Build_TemperatureMap();
    }
    void Build_Heighmap(){
//        Heightmap = perlin_noise.GenerateSmoothNoise(perlin_noise.GenerateWhiteNoise(sirka,vyska,garbagefunctions.Seed2Long(seed)),8);
//        heightMap = perlinNoise.WholeNoise(seed,sirka,vyska,octaveCount);
        heightMap = VoroniDiagram.idknamesafteritworks(15, sizeX, SizeY,seed);
    }
    void Build_TemperatureMap(){
        temperatureMap = garbagefunctions.TempMap(SizeY, sizeX,equator);
    }
}
