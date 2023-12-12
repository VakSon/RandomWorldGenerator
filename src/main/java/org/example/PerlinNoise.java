package org.example;

import java.util.Arrays;
import java.util.Random;

public class PerlinNoise {

    //return 2d float array with randomly generated values from 0 to 1
    public static float[][] pureNoise(int sizeX, int sizeY, long seed){
        Random random = new Random(seed);

        float[][] result = new float[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                result[i][j] =random.nextFloat();
            }
        }
        return result;
    }


    //return 2d float array using 4 border points
    public static float[][] octaveNoise(int octave, float[][] pureNoise){
        //params
        int sizeX = pureNoise.length;
        int sizeY = pureNoise[0].length;
        int offset = (int) Math.pow(2,octave);
        //result
        float[][] result = new float[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {


            int x0 = i - i % offset;
            int x1 = (x0 + offset) % sizeX; //wrapped
            float horizontalBlend = (i % offset) / (float) offset;


            //upgrade of wraparound so that the left and right ends are continuos
            /*if(x1 < x0){
                x1 = 0;
                horizontalBlend = (float)(i - x0) / (float)(sizeX - x0);
            }*/

            for (int j = 0; j < sizeY; j++) {
                int y0 = j - j%offset;
                int y1 = (y0 + offset)%sizeY; //wrapped
                float verticalBlend = (j%offset)/(float)offset;

                float top = interpolate(pureNoise[x0][y0], pureNoise[x1][y0] ,horizontalBlend);
                float bottom = interpolate(pureNoise[x0][y1], pureNoise[x1][y1] ,horizontalBlend);
                //interpolation
                result[i][j] = interpolate(top,bottom,verticalBlend);


            }
        }

        return result;
    }



    static float interpolate(float x0, float x1, float alpha)
    {
        double cosine = (1.0 - Math.cos(alpha * Math.PI)) / 2.0;
        return (float) (x0 * (1 - cosine) + cosine * x1);
    }

    //2d array returns WholeNoise with Noise specs presetted in Settings.java
    public static float[][] preSettedNoise() {
        int[] octaves = new int[Settings.octaves];
        for (int x =1;x <=Settings.octaves;x++){
            octaves[x-1] =x;
        }
    return wholeNoise(Settings.sizeX,Settings.sizeY,Settings.seed,octaves);
    }

    //2d array returns WholeNoise(perlin noise) with specs set by user
    public static float[][] wholeNoise(int sizeX,int sizeY, long seed, int[] octaves){
        //reorganized octaves so that last one is biggest
        float[][] octaveNoise;
        float[][] result = new float[sizeX][sizeY];
        float[][] pureNoise = pureNoise(sizeX,sizeY,seed);
        float totalAmplitude = 0f;
        float amplitude = 1f;
        float persistance = Settings.persistance;

        //sort octaves needed
        Arrays.sort(octaves);

        //for each octave generate octave Noise and add it to result
        for (int x = octaves.length - 1; x >= 0; x--)
        {
            octaveNoise = octaveNoise(octaves[x],pureNoise);

            if(x <= octaves.length-2) {
                for (int z = 1; z <= octaves[x+1] - octaves[x]; z++) {
                    amplitude *= persistance;
                    totalAmplitude += amplitude;
                }
            }else{
                amplitude *= persistance;
                totalAmplitude += amplitude;
            }
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {

                        result[i][j] += octaveNoise[i][j]*amplitude;
                    }
                }
        }

        //just to get perlin noise with values between 0 and 1
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                result[i][j] /= totalAmplitude;
            }
        }


        return result;
    }
}
