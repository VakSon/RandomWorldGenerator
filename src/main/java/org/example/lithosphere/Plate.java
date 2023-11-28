package org.example.lithosphere;

import java.util.ArrayList;
import java.util.List;

public class Plate {

    public float vectorX;
    public float vectorY;
    List<PlatePoint> platePoints = new ArrayList<>();
    public Plate(int start, int end) {
        for (int x = start;x<end;x++){
            for (int y = 0; y< Lithosphere.sizeY; y++){
                platePoints.add(new PlatePoint(x,y,this));
            }
        }
    }

    public void update() {
        for (PlatePoint p: platePoints){
            p.updatePosition();
        }
    }
}
