package org.example.lithosphere;

public class PlatePoint {
    int x;
    int y;
    Plate parrentPointer;
    public PlatePoint(int x, int y, Plate parrent) {
        this.x = x;
        this.y = y;
        this.parrentPointer = parrent;
    }

    public void updatePosition() {
        Lithosphere.lithospherePoints[x][y].pointsOnPosition.remove(this);
        //move
        x += parrentPointer.vectorX;
        y += parrentPointer.vectorY;
        //wrap
        x = wrap(x,Lithosphere.sizeX);
        y = wrap(y,Lithosphere.sizeY);

        Lithosphere.lithospherePoints[x][y].pointsOnPosition.add(this);
    }

    private int wrap(int value, int max) {
        return (x<0) ? x+max : x%max;
    }
}
