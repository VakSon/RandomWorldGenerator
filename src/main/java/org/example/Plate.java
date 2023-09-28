package org.example;

import java.util.*;

public class Plate {

    public Plate(int id,int x, int y){
        this.id = id;

        //generating plate 3x3 points
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                Point p = new Point(Wrap(x+i,Lithosphere.sizeX),Wrap(y+j,Lithosphere.sizeY),this);
                pointList.add(p);
                Lithosphere.pointList.add(p);
                Lithosphere.pointlist[Wrap(x+i,Lithosphere.sizeX)][Wrap(y+j,Lithosphere.sizeY)] = p;
                if (i !=0 && j!=0){
                    border.add(p);
                }
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
    public double direction; // 0 to 2pi radians
    public int[] offset = new int[2];

    public void Update(){
        this.UpdateOffset();
        for (Point point: pointList){
            point.Move(offset);
        }
    }
    void UpdateOffset(){
        //return array with two values [0]-X and [1]-Y
        offset[1] = (int) (Math.sin(direction)*speed); //y
        offset[0] = (int) (Math.cos(direction)*speed); //x
    }
    public Point Updatecreate(){
        //get random boundary point
        //can be changed to void using break system
        for (int t = 0; t < 40; t++){
            Point p = border.get( Lithosphere.random.nextInt(border.size()) );
            //here:
            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    if(Lithosphere.pointlist[Wrap(p.getX()+i,Lithosphere.sizeX)][Wrap(p.getY()+j,Lithosphere.sizeY)] == null){
                        Point returnable = new Point(Wrap(p.getX()+i,Lithosphere.sizeX),Wrap(p.getY()+j,Lithosphere.sizeY),this);
                        this.border.add(returnable);
                        this.pointList.add(returnable);
                        if (!p.isBorder()){
                            this.border.remove(p);
                        }
                        return returnable;
                        //break here;
                    }

                }
            }

        }
        //there:
        for(Point p:border){

            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    //could be also done with Lithosphere.pointList.
                    if(Lithosphere.pointlist[Wrap(p.getX()+i,Lithosphere.sizeX)][Wrap(p.getY()+j,Lithosphere.sizeY)] == null){
                        Point returnable = new Point(Wrap(p.getX()+i,Lithosphere.sizeX),Wrap(p.getY()+j,Lithosphere.sizeY),this);
                        this.border.add(returnable);
                        this.pointList.add(returnable);
                        if (!p.isBorder()){
                            this.border.remove(p);
                        }
                        return returnable;
                        //break there;
                    }

                }
            }

        }return null;
    }
    int Wrap(int number, int max){
        int returnable = number%max;
        if (returnable < 0) {
            return returnable += max;
        }
        return returnable;
    }



}


