package org.example;

import java.io.IOException;
import java.util.Random;


class Main{
    static int sizeY = Settings.sizeY;
    static int sizeX = Settings.sizeX;


    static Window w;

        public static void main(String[]args) throws IOException {
            //All settings are in Settings.java
            //create window
            w = new Window();

            float[][] heightMap = new float[sizeX][sizeY];
            //perlin with predetermined params
            heightMap = PerlinNoise.preSettedNoise();
            //perlin with freedom to chose params
            heightMap = PerlinNoise.wholeNoise(sizeX,sizeY,Settings.seed, new int[]{0, 1, 2, 3});

            //Diamond Square
            heightMap = new DiamondSquare(sizeX,sizeY,new Random(Settings.seed)).getHeighMap();

            //hydraulic erosion with already generated heightmap as input
            heightMap = new HydraulicErosion(heightMap).heightMap;

            //update window to show output
            w.update("Name of Window", heightMap);


        }
    }

