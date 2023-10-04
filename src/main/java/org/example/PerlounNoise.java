package org.example;

import java.util.Random;

public class PerlounNoise {

    //return 2d float array with randomly generated values from 0 to 1
    public static float[][] PureNoise(int sizeX, int sizeY, long seed){
        Random random = new Random(seed);

        float[][] result = new float[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                result[i][j] = random.nextFloat();
            }
        }
        return result;
    }

    //return 2d float array using 4 border points
    public static float[][] OctaveNoise(int octave,float[][] pureNoise){
        //params
        int sizeX = pureNoise.length;
        int sizeY = pureNoise[0].length;
        int offset = (int) Math.pow(2,octave);
        //result
        float[][] result = new float[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            int x0 = i - i%offset;
            int x1 = (x0 + offset)%sizeX; //wrapped

            for (int j = 0; j < sizeY; j++) {
                int y0 = j - j%offset;
                int y1 = (y0 + offset)%sizeY; //wrapped

                


            }
        }

        return result;
    }



    static float Interpolate(float x0, float x1, float alpha)
    {
        double cosine = (1.0 - Math.cos(alpha * Math.PI)) / 2.0;
        return (float) (x0 * (1 - cosine) + cosine * x1);
    }

    //2d array returns WholeNoise with Noise specs presetted in Settings.java
    public static float[][] PreSettedNoise() {
        int[] octaves = new int[Settings.octaves];
        for (int x =0;x <Settings.octaves;x++){
            octaves[x] =x;
        }
    return WholeNoise(Settings.sizeX,Settings.sizeY,Settings.seed,octaves);
    }

    //2d array returns WholeNoise(perlin noise) with specs set by user
    public static float[][] WholeNoise(int sizeX,int sizeY, long seed, int[] octaves){
        float[][][] octavesNoise = new float[octaves.length][sizeX][sizeY];
        float[][] result = new float[sizeX][sizeY];
        float[][] pureNoise = PureNoise(sizeX,sizeY,seed);
        for(int i = 0; i < octaves.length; i++){
            octavesNoise[i] = OctaveNoise(octaves[i],pureNoise);
        }

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for(int x = 0; x < octaves.length; x++){
                    result[i][j] += octavesNoise[x][i][j];                }
            }
        }


        return result;
    }
}
