package org.example;

import java.util.Random;

public class DiamondSquare {

    int dimension;

    final int EMPTY = -10;
    int sizeX;
    int sizeY;

    float randomness = 2f;

    float[][] heighMap;
    Random random;

    public DiamondSquare(int sizeX, int sizeY, Random random) {
        int xFitter = (int) (Math.log(sizeX) / Math.log(2));
        int yFitter = (int) (Math.log(sizeY) / Math.log(2));
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.random = random;
        //set heighmap to empty

        //divide into squares with biggest common 2 to power of n
        //for each compute
        if (xFitter == yFitter) {
            this.dimension = (int) Math.pow(2, xFitter + 1) + 1;
            this.heighMap = new float[dimension][dimension];
            Empty();
            run(0, 0);
        } else {
            this.dimension = (xFitter > yFitter) ? (int) Math.pow(2, xFitter) + 1 : (int) Math.pow(2, yFitter) + 1;
            this.heighMap = new float[(sizeX / (dimension-1)+1) * (dimension-1) + 1][(sizeY /(dimension-1)+1) * (dimension-1)+ 1];
            Empty();

            for (int x = 0; x < heighMap.length-1; x += dimension - 1) {
                for (int y = 0; y < heighMap[0].length-1; y += dimension - 1) {
                    run(x, y);
                }
            }

        }

        //find min and max to normalize
        float min = 1;
        float max = 0;
        for (int x = 0; x < heighMap.length; x++) {
            for (int y = 0; y < heighMap[0].length; y++) {
                if (heighMap[x][y] < min) min = heighMap[x][y];
                if (heighMap[x][y] > max) max = heighMap[x][y];
            }
        }
        float minoffset = -min;
        float divide = minoffset + max;
        for (int x = 0; x < heighMap.length; x++) {
            for (int y = 0; y < heighMap[0].length; y++) {
                //normalize
                heighMap[x][y] += minoffset;
                heighMap[x][y] /= divide;
            }
        }
    }

    void run(int xOffset, int yOffset) {
        setBoundarypoints(xOffset, yOffset, dimension - 1);
        int division = 1;

        while (dimension / division >= 2) {

            int inc = (dimension - 1) / division;

            for (int y = 0; y < dimension; y += inc) {

                if (inc + y >= dimension) break;

                for (int x = 0; x < dimension; x += inc) {

                    if (inc + x >= dimension) break;
                    //execute diamond square for each place until every box is filled
                    diamondSquare(x + xOffset, y + yOffset, (x + inc) + xOffset, (y + inc) + yOffset);
                }
            }
            division *= 2;
        }
    }

    void diamondSquare(int x1, int y1, int x2, int y2) {
        //diamond step
        float tl = heighMap[x1][y1];
        float tr = heighMap[x2][y1];
        float bl = heighMap[x1][y2];
        float br = heighMap[x2][y2];

        float value = (tl + tr + bl + br + noise()) / 4;
        int middleX = x1 + ((x2 - x1) / 2);
        int middleY = y1 + ((y2 - y1) / 2);
        tryToSet(middleX, middleY, value);

        //square step
        float l = (tl + bl + value + noise()) / 3;
        tryToSet(x1, middleY, l);

        float r = (tr + br + value + noise()) / 3; //right
        tryToSet(x2, middleY, r);

        float t = (tl + value + tr + noise()) / 3;
        tryToSet(middleX, y1, t);

        float b = (bl + value + br + noise()) / 3;
        tryToSet(middleX, y2, b);

    }

    private void tryToSet(int x, int y, float value) {
        //if in bounds
        if (x < heighMap.length && x >= 0 && y < heighMap[0].length && y >= 0) {
            //if is not set already
            if (heighMap[x][y] == EMPTY) {
                heighMap[x][y] = value;
            }
        }
    }

    void setBoundarypoints(int startX, int startY, int step) {
        tryToSet(startX, startY, random.nextFloat());
        tryToSet(startX + step, startY, random.nextFloat());
        tryToSet(startX, step + startY, random.nextFloat());
        tryToSet(startX + step, startY + step, random.nextFloat());
    }

    private float noise() {
        return random.nextFloat() * randomness - (randomness /2);
    }

    public float[][] getHeighMap() {
        float[][] result = new float[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                result[x][y] = heighMap[x][y];
            }
        }
        return result;
    }
    public void Empty(){
        for (int x = 0; x < heighMap.length; x++) {
            for (int y = 0; y < heighMap[0].length ; y++) {
                heighMap[x][y] = EMPTY;
            }
        }
    }
}

