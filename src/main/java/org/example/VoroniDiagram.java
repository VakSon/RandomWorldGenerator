package org.example;

import java.util.ArrayList;
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

        float[][] result = PerlinNoise.WholeNoise(seed,sum,numOfExamples,5);
        return result;
    }
    //move elsewhere its here just for now
    int[][] Movement(int[][]plates,float[][]speed,float[][]direction/*float[][] mass*/){
        ArrayList<ArrayList<Integer> > newPlates = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < plates.length; i++){
            for (int j = 0; j < plates[0].length; j++){

            }
        }
        return null;
    }
    //measures distance from point to the inicPoints and for the shortest one it remembers its "id"
    public static float[][] idknamesafteritworks(int sum, int width, int height, String seed){
        //Create inicial Points
        int[][] inicPoints = InicialPoints(sum,width,height,garbagefunctions.Seed2Long(seed));
        //adds for each InicialPoint set of speed for diferent directions for non linear borders between areas.
        float[][] velocity = Velocity(sum,512,seed);
        double idkfucknames = 512/(Math.PI*2);
        float[][] result= new float[width][height];


        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                double time = 42069.42069f;
                int index = -1;
                int p = 100;
                for (int k = 0; k < sum; k++){
//                    //calculating x and y axis distance from Inicial points and wrapping it around
//                    double xDiffer =Math.abs(i - inicPoints[k][0]); //v 0.1.2//
//                    double xDiff =xDiffer>width/2 ?width-xDiffer :xDiffer;
//                    double yDiffer = j - inicPoints[k][1];
//                    double yDiff =yDiffer>height/2 ?height-yDiffer :yDiffer;
//
//                    //manhatan distance
//                    if(Math.pow(Math.pow(xDiff,p) + Math.pow(yDiff,p),(Math.pow(p,-1))) < time){
//                        time = Math.pow(Math.pow(xDiff,p) + Math.pow(yDiff,p),(Math.pow(p,-1)));
//                        index = k;
//                    }

                    double xDiffer =Math.abs(i - inicPoints[k][0]); /*v 0.1.2*/
                    double xDiff =xDiffer>width/2 ?width-xDiffer :xDiffer;
                    double yDiffer = j - inicPoints[k][1];
                    double yDiff =yDiffer>height/2 ?height-yDiffer :yDiffer;

                    double angle = Math.atan2(yDiff, xDiff);
                    if (angle < 0) {
                        angle += 2 * Math.PI; // Převod úhlu na rozsah 0 až 2π
                    }

                    if ( Math.sqrt(xDiff * xDiff + yDiff * yDiff)/(1+(velocity[k][(int) (angle*idkfucknames)]/3)) < time){

                        time = Math.sqrt(xDiff * xDiff + yDiff * yDiff)/(1+(velocity[k][(int) (angle*idkfucknames)]/3));
                        index = k;

                    }


                }
                result[i][j] = (float) index/sum;
            }
        }


        return result;
    }

}
