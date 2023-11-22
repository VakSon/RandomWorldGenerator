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
    public Lithosphere(){
        this.sizeY = Settings.sizeY;
        this.sizeX = Settings.sizeX;
        this.plateSum = Settings.plateSum;
        this.seed = Settings.seed;
        this.random = new Random(seed);
        pointlist = new Point[sizeX][sizeY];
        System.out.println("Lithosphere is being created");
        //generating by plate method
        // Create();


        //generating by voronoi method
        createByVoronoi();
        setPlateStuffs();
        while(true){
            update();
            Main.w.update(Main.w.frame.getGraphics(),"Cycle", img());
        }
    }




    public static float[][] img(){
        float[][] result = new float[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                if(pointlist[i][j] == null){
                    result[i][j] = 0;
                }else{
                    result[i][j] = pointlist[i][j].plateParrent.id/(float)plateSum;
                }
            }
        }
        return result;
    }


    //List of plates
    static List<Plate> plateList= new ArrayList<>();
    //Public  static List<Points> or 2d array have to decide what is faster.
    public static List<Point> pointList= new ArrayList<>();
    public static Point[][] pointlist;

    long seed;

    //Current tick

    public static int tick;




    //update void
    void update(){
        tick++;
        for (Plate plate: plateList){
            plate.update();
        }
    }
    //set direction and speed
    void setPlateStuffs() {
        for (Plate p : plateList){
            p.setDirection(random.nextDouble(Math.PI));
            p.setSpeed(1.4);
        }
    }
    //voronoi void
    void createByVoronoi(){
        int[][] voronoi = VoroniDiagram.GenerateInt(plateSum,sizeX,sizeY,seed);
        //plate generation
        for (int id = 0; id < plateSum; id++){

            Plate p = new Plate(id);
            plateList.add(p);
        }

        //creating adding points to plates
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                Point p = new Point(i,j,plateList.get(voronoi[i][j]));
                plateList.get(voronoi[i][j]).pointList.add(p);
                pointList.add(p);
                pointlist[i][j] = p;
            }
        }
        //create border
        for(Plate p : plateList){
            p.updateBorder();
        }
    }
    //
    void Create(){
        // create starting points of plateSum plates
        // [id][0] = x coordinate
        // [id][1] = y coordinate
        int[][]inicPoints = inicialPoints(plateSum);
        //create x plates
        for (int id = 0; id < plateSum; id++){

            Plate p = new Plate(id,inicPoints[id][0],inicPoints[id][1]);
            plateList.add(p);
        }
        //prototype platelist when plate has no null points arround it is removod from for cycle
        List<Plate>spdPlateList = plateList;
        //method that is adding to each plate point parrarely
        Point point;
        int x =0;
        while (spdPlateList.size()>0){
            x++;
            if(x%60 == 0){
                Main.w.update(Main.w.frame.getGraphics(),"Cycle", img());
                System.out.println(x);
            }

            here:

            for (Plate p: spdPlateList){
                point = p.Updatecreate();
                //if null plate has no points bordering null therefore remove from for cycle
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
    int[][] inicialPoints(int sum){

        int[][] result = new int[sum][2];

        for (int i = 0; i < sum; i++){
            result[i][0] = random.nextInt(sizeX);
            result[i][1] = random.nextInt(sizeY);
        }

        return result;
    }

}
