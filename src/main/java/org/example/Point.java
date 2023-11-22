package org.example;

public class Point {

    public Point(int x, int y, Plate p){
        this.x = x;
        this.y = y;
        plateParrent = p;
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
    void move(int[] offset){
        int newX = (x + offset[0])% Lithosphere.sizeX; // wrap needed
        int newY = (y + offset[1]) % Lithosphere.sizeY; // wrap needed
        //collision
        //subduction
        //not empty lets check other stuff
        Point p = Lithosphere.pointlist[x][y];
        if (p != null){
            //if they belong to same plate it moves there
            if (p.plateParrent == plateParrent){
                Lithosphere.pointlist[x][y] = this;
                Lithosphere.pointlist[this.x][this.y] =null;
                //Collides with other plate
            } else{




            }
        }
        //empty jump right in
        else{
            Lithosphere.pointlist[x][y] = this;
            Lithosphere.pointlist[this.x][this.y] =null;
        }

        //Stress (posun)



        if (isBorder()){
            plateParrent.border.add(this);
        }
    }
    int Wrap(int number, int max){
        int returnable = number%max;
        if (returnable < 0) {
            return returnable += max;
        }
        return returnable;
    }
    public boolean isBorder(){
        //check if its 8 neighbours are the same plate
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                //can be done with Listhosphere pointlist or plate pointlist have to decide whats better
                if (Lithosphere.pointlist[Wrap(x + i,Lithosphere.sizeX)][Wrap(y + j,Lithosphere.sizeY)] !=null ){
                    if (Lithosphere.pointlist[Wrap(x + i,Lithosphere.sizeX)][Wrap(y + j,Lithosphere.sizeY)].plateParrent.equals(plateParrent)){

                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean isBorderingNull(){
        //check if its 8 neighbours are null
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                //can be done with Listhosphere pointlist or plate pointlist have to decide whats better
                if (Lithosphere.pointlist[Wrap(x + i,Lithosphere.sizeX)][Wrap(y + j,Lithosphere.sizeY)] ==null ){
                return true;

                }
            }

        }return false;
    }

}
