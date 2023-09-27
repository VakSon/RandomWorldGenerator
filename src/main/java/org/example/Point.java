package org.example;

public class Point{

    public Point(int x,int y){

    }

    @Override
    public int hashCode() {
        return y*Lithosphere.sizeX+x;
    }
    //overdrive equals
    //plate pointer
    public Plate plateParrent;
    //coordinates
    public int x;
    public int getX(){ return x;}
    public int y;
    public int getY(){ return y;}
    //weight

    //mass
    void Move(int[] offset){
        x = (x + offset[0])% Settings.sirka; // wrap needed
        y = y + offset[1] % Settings.vyska; // wrap needed
        if (isBorder()){
            plateParrent.border.add(this);
        }
    }
    int Wrap(int number, int max){
        return (number % max);
    }
    public boolean isBorder(){
        //check if its 8 neighbours are the same plate
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                //can be done with Listhosphere pointlist or plate pointlist have to decide whats better
                if (Lithosphere.pointlist[Wrap(x + i,Settings.sirka)][Wrap(y + j,Settings.vyska)].plateParrent.equals(plateParrent)){

                    return true;
                }
            }
        }
        return false;
    }

}
