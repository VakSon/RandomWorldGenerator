package org.example;

public class Mapa {
    public int SizeY;
    public int sizeX;
    public long seed;
    public int octaveCount;
    public float[][] heightMap;
    public float[][] temperatureMap;
    int equator = SizeY /2;
    public Mapa(){
        this.SizeY = Settings.sizeY;
        this.sizeX = Settings.sizeX;
        this.octaveCount= Settings.octaves;
        this.seed = Settings.seed;
    }
    void Init(){
        Build_Heighmap();
        Build_TemperatureMap();
    }
    void Build_Heighmap(){
//        Heightmap = perlin_noise.GenerateSmoothNoise(perlin_noise.GenerateWhiteNoise(sirka,vyska,garbagefunctions.Seed2Long(seed)),8);
//        heightMap = perlinNoise.WholeNoise(seed,sirka,vyska,octaveCount);
        heightMap = VoroniDiagram.GenerateFloat(15, sizeX, SizeY,seed);
    }
    void Build_TemperatureMap(){
        temperatureMap = garbagefunctions.TempMap(SizeY, sizeX,equator);
    }
}
