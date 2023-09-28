package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lithosphere {
    //map widht
    public static  int sizeX;
    //map height
    public static int sizeY;

    //number of plates to be created
    public static int plateSum;
    //random for the whole project
    public static Random random;
    public Lithosphere(int plateSum, int sizeX, int sizeY,long seed){
        this.sizeY = sizeY;
        this.sizeX = sizeX;
        this.plateSum = plateSum;
        this.random = new Random(seed);
        pointlist = new Point[sizeX][sizeY];
        System.out.println("Lithosphere is being created");
        //I think I can generate now everything should be ready
        this.Create();
    }
    public static float[][] Img(){
        float[][] result = new float[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                result[i][j] = pointlist[i][j].plateParrent.id/(float)plateSum;
            }
        }
        return result;
    }


    //List of plates
    static List<Plate> plateList= new ArrayList<>();
    //Public  static List<Points> or 2d array have to decide what is faster.
    public static List<Point> pointList= new ArrayList<>();
    public static Point [][] pointlist;

    //Current tick

    public static int tick;




    //update void
    void Update(){
        tick++;
        for (Plate plate: plateList){
            plate.Update();
        }
    }
    //voronoi void
    //
    void Create(){
        // create starting points of plateSum plates
        // [id][0] = x coordinate
        // [id][1] = y coordinate
        int[][]inicPoints = InicialPoints(plateSum);
        //create x plates
        for (int id = 0; id < plateSum; id++){

            Plate p = new Plate(id,inicPoints[id][0],inicPoints[id][1]);
            plateList.add(p);
        }
        //prototype platelist when plate has no null points arround it is removod from for cycle
        List<Plate>spdPlateList = plateList;
        //method that is adding to each plate point parrarely
        Point point;
        while (spdPlateList.size()>0){
            here:
            for (Plate p: spdPlateList){
                point = p.Updatecreate();
                //if null no bordering free points therefore remove from for cycle
                if(point != null){
                    pointList.add(point);
                    pointlist[point.getX()][point.getY()] = point;
                }else {
                    System.out.println("desk is removed");
                    spdPlateList.remove(p);
                    break here;
                }

            }

        }
    }

    // generates InicialPoints x and y values
    int[][] InicialPoints(int sum){

        int[][] result = new int[sum][2];

        for (int i = 0; i < sum; i++){
            result[i][0] = random.nextInt(sizeX);
            result[i][1] = random.nextInt(sizeY);
        }

        return result;
    }

}
