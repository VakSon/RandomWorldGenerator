package org.example.lithosphere;

import org.example.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lithosphere {
    static int sizeX = Settings.sizeX;
    static int sizeY = Settings.sizeY;
    static Random random = new Random(Settings.seed);
    static List<Plate> plateList = new ArrayList<>();
    static LithospherePoint[][] lithospherePoints = new LithospherePoint[sizeX][sizeY];

    int plateSum = 2;
    void createPlates(){
        for (int plate = 0; plate < plateSum; plate++){
            plateList.add(new Plate(plate*400,(plate+1)*400));
        }
    }
    void run(){
        for (float MilYears = 0; MilYears < 808; MilYears += 1.2){
            update();
        }
    }
    void update(){
        //move points on plate
        for (Plate p : plateList){
            p.update();
        }
        //evaluate what point should stay on that position
        for (int x = 0;x<sizeX;x++){
            for (int y = 0; y< sizeY; y++){
                lithospherePoints[x][y].evaluate();
            }
        }

    }
}
