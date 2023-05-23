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
    public static float[][] idknamesafteritworks(int sum,int width,int height,long seed){
        //measures distance from point to the inicPoints and for the shortest one it remembers its "id"
        int[][] inicPoints = InicialPoints(sum,width,height,seed);
        float[][] result= new float[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                double distance = 42069.42069f;
                int index = -1;
                for (int k = 0; k < sum; k++){
                    double xDiff = i - inicPoints[k][0];
                    double yDiff = j - inicPoints[k][1];
                    if ( Math.sqrt(xDiff * xDiff + yDiff * yDiff) < distance){

                        distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
                        index = k;

                    }
                }
                result[i][j] = (float) index/sum;
            }
        }


        return result;
    }
}
