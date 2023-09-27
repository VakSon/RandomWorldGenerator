package org.example;

import java.util.ArrayList;
import java.util.List;

public class Lithosphere {
    //List of plates

    //wtf is going on??? static final

    public static  int sizeX;
    public static int sizeY;
    public Lithosphere(int plateSum, int sizeX, int sizeY){
        this.sizeY = sizeY;
        this.sizeX = sizeX;
    }


    static List<Plate> plateList= new ArrayList<>();
    //Public  static Set<Points> what is faster list or 2d array ?
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
        //create x plates

        //method that is adding to each plate point parrarely
        int sumOfPoints = plateList.size()*9;
        Point point;
        while (sumOfPoints < sizeY*sizeX){

            for (Plate p: plateList){
                point = p.Updatecreate();
                pointList.add(point);
                pointlist[point.getX()][point.getY()] = point;
            }

        }
    }

}
