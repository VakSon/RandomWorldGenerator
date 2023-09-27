package org.example;

import java.util.*;

public class Plate {

    public Plate(int id,int x, int y){
        this.id = id;

        //generating plate 3x3 points
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                Point p = new Point(Wrap(x+i,Lithosphere.sizeX),Wrap(y+j,Lithosphere.sizeY));
            }
        }


    }


    public final int id;
    //set of points
    public List<Point> pointList = new ArrayList<Point>();
    //should
    public List<Point> border = new ArrayList<Point>();
    //Age
    public int Age;
    //Z- axis depth?
    public int Depth;
    //mass
    public int mass;
    //movement offset
    public double speed;
    public double direction;
    public int[] offset = new int[2];

    public void Update(){
        this.getOffset();
        for (Point point: pointList){
            point.Move(offset);
        }
    }
    int[] getOffset(){

        int x = (int) (Math.sin(direction)*speed);
        int y = (int) (Math.cos(direction)*speed);

        return null;
    }
    public Point Updatecreate(){
        //get random boundary point
        //can be changed to void using break system
        Point p = border.get( (int) (Math.random()%border.size()) );
        //here:
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if(Lithosphere.pointlist[p.getX()+i][p.getY()+j] == null){
                    Point returnable = new Point(p.getX()+i,p.getY()+j);
                    this.border.add(returnable);
                    this.pointList.add(returnable);
                    if (!p.isBorder()){
                        this.border.remove(p);
                    }
                    return returnable;
                    //break here;
                }

            }
        }return null;
    }
    int Wrap(int number, int max){
        return (number % max);
    }



}


