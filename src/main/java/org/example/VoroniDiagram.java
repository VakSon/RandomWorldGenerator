package org.example;

import java.util.Random;
public class VoroniDiagram {
    static int[][] InicialPoints(int sum, int width,int height,long seed){
        // generates InicialPoints x and y values
        Random rand = new Random(seed);
        int[][] result = new int[sum][2];

        for (int i = 0; i < sum; i++){
            result[i][0] = rand.nextInt(width);
            result[i][1] = rand.nextInt(height);
        }

        return result;
    }
    static float[][] Velocity(int sum, int numOfExamples,String seed){
        // generates velocity of InicialPoints(sum) in 360 degrees using perlin noise
        float[][] result = perlinNoise.WholeNoise(seed,sum,numOfExamples,5);

        return result;
    }
    public static float[][] idknamesafteritworks(int sum,int width,int height,String seed){
        //measures distance from point to the inicPoints and for the shortest one it remembers its "id"
        int[][] inicPoints = InicialPoints(sum,width,height,garbagefunctions.Seed2Long(seed));
        //adds for each InicialPoint set of speed for diferent directions for not linear borders between areas.
        float[][] velocity = Velocity(sum,512,seed);
        double idkfucknames = 512/(Math.PI*2);
        float[][] result= new float[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                double time = 42069.42069f;
                int index = -1;
                for (int k = 0; k < sum; k++){

                    double xDiffer =Math.abs(i - inicPoints[k][0]); /*v 0.1.2*/
                    double xDiff =xDiffer>width/2 ?width-xDiffer :xDiffer;
                    double yDiff = j - inicPoints[k][1];
                    double angle = Math.atan2(yDiff, xDiff);
                    if (angle < 0) {
                        angle += 2 * Math.PI; // Převod úhlu na rozsah 0 až 2π
                    }
                    if ( Math.sqrt(xDiff * xDiff + yDiff * yDiff)/(1+(velocity[k][(int) (angle*idkfucknames)]/3)) < time){

                        time = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
                        index = k;

                    }
                }
                result[i][j] = (float) index/sum;
            }
        }


        return result;
    }
}
