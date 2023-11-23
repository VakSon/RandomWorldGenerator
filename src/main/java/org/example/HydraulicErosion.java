package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.lang.Double.isNaN;

public class HydraulicErosion {

    int sizeX;
    int sizeY;
    public float[][] heightMap;
    int dropsNum = 50;
    int maxLifeTime = 30;
    float evaporateSpeed = 0.01f;
    double water = 1f;
    double minSlope = 0.01f;
    double minimalWaterVolume = 0.05;
    int radius = 4;
    double inertia = 0.1;
    double erosionSpeed = 0.3f;
    double depositionSpeed = 0.1f;
    double minSedimentCapacity = 0.01f;
    double sedimentCapacityFactor = 2;

    public HydraulicErosion(float[][] heightMap){
        sizeX = heightMap.length;
        sizeY = heightMap[0].length;
        this.heightMap = heightMap;
        erode();
    }
    public void erode(){
        Random r = new Random(Settings.seed);
        for(int drops = 0; drops < dropsNum;drops++){
            droplet(r.nextDouble(sizeX),r.nextDouble(sizeY));
        }
    }
    public void droplet(double x ,double y){
        //represents how many tiles it will go for one lifetime cyclus
        double speed = 1f;
        double height = evaluateHeight(x,y);
        double[] direction = new double[2];
        double dwater = water;
        double dsediment = 0f;


        for (int lifetime = 0; lifetime<maxLifeTime;lifetime++){

            if (dwater < minimalWaterVolume){
                lifetime = maxLifeTime;
                break;
            }
            //create new direction vector
            double newDirection[] = evaluateDirection(x,y);

            direction[0] = direction[0]*inertia - newDirection[0]*( 1 - inertia);
            direction[1] = direction[1]*inertia - newDirection[1]*( 1 - inertia);

            //normalise the vector (to have length of one)
            double lengthOfDirection =Math.sqrt(direction[0]*direction[0] + direction[1]*direction[1]);

            direction[0] = direction[0]/lengthOfDirection;
            direction[1] = direction[1]/lengthOfDirection;


            x += direction[0];
            y += direction[1];

            if (x < 0 || x>sizeX || y< 0 ||y>sizeY || dwater < minimalWaterVolume){
                lifetime = maxLifeTime;
                break;
            }

            //count the erosion or sedimantation amount

            double newHeight = evaluateHeight(x,y);
            double diffHeight = newHeight-height;

            //calculate maximal sediment capacity in new pos
            double sedimentCapacity = Math.max(minSedimentCapacity, -diffHeight * speed * dwater * sedimentCapacityFactor);

            //if moving uphill or carrying more than it should
            if (dsediment > sedimentCapacity || diffHeight > 0){


                double amountToDeposit = (diffHeight > 0) ? Math.min(diffHeight, dsediment) : (dsediment - sedimentCapacity)*depositionSpeed;
                dsediment -= amountToDeposit;

                //add sediment to four nodes using bilinear interpolation (again :((( )
                heightMap[(int) wrap( x,sizeX)][(int) wrap(y, sizeY)] += amountToDeposit * (1 - (x%1)) * (1 - (y %1));
                heightMap[(int) wrap(x + 1,sizeX)][(int) wrap(y,sizeY)] += amountToDeposit * (x%1) * (1 - (y %1));
                heightMap[(int) wrap (x,sizeX)][(int) wrap(y + 1,sizeY)] += amountToDeposit * (1 - (x%1)) * ( y %1 );
                heightMap[(int) wrap (x+1,sizeX)][(int) wrap(y+1,sizeY)] += amountToDeposit * (x%1) * y %1;
            }
            else {
                //erode part of ground around droplet
                //calculate amount to erode cannot be higher than diff in height => create pit holes
                double amountToErode = Math.min((sedimentCapacity - dsediment) * erosionSpeed,-diffHeight);
                dsediment += amountToErode;
                //for all nodes in radius from NW node
                List<Double> weights = new ArrayList<>();

                List<Integer> indexes = new ArrayList<>();

                double weightSum = 0;

                int index = 0;
                for (int i = -radius; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++) {
                        //distance to x,y
                        double distance = Math.sqrt(i*i + j*j);
                        //if its in range
                        if (Math.sqrt(i*i + j*j) <= radius){
                            //weight
                            double weight = 1 - distance/radius;
                            if(x+i > 0 && x+i < sizeX && y+j > 0 && y+j < sizeY) {
                                weightSum += weight;
                                indexes.add(i + (int)(x) + (((int)(y) + j)  * sizeX));
                                weights.add(weight);

                                index++;
                            }
                        }
                    }
                }
                //normalize
                for (int i = 0; i < index;i++) {
                    weights.set(i,weights.get(i)/ weightSum);
                }
                for (int i = 0; i < index;i++){
                    int posX = indexes.get(i)%sizeX;
                    int posY = indexes.get(i)/sizeX;
                    double lul = weights.get(i);
                    if(isNaN(heightMap[posX][posY]-lul)){
                        System.out.println(posX +  "   " + posY + "   " + lul);
                    }
                    heightMap[posX][posY] -= lul;
                }
            }

            //update speed and amount of water, 10 is gravitational force
            speed = Math.sqrt(speed*speed + diffHeight * 4);
            dwater *= (1-evaporateSpeed);
        }


    }
    double[] evaluateDirection(double x,double y){

        double[] variables = offsetsAndNodeHeights(x,y);

        double offsetX = variables[0];
        double offsetY = variables[1];

        //height at node points
        double tl = variables[2];
        double tr = variables[3];
        double dl = variables[4];
        double dr = variables[5];

        double gradientX = (tr - tl) * (1 - offsetY) + (dr - dl) * offsetY;
        double gradientY = (dl - tl) * (1 - offsetX) + (dr - tl) * offsetX;


        //System.out.println("\nHeightNW " + tl +" HeightNE " + tr + "\nX " + x + " Y " + y + "\nHeightSW " + dl +" HeightSE " + dr + "\nGradient X " + gradientX + " GradientY " + gradientY + "\n");

        return new double[]{gradientX,gradientY};
    }
    double evaluateHeight(double x,double y){

        double[] variables = offsetsAndNodeHeights(x,y);

        double offsetX = variables[0];
        double offsetY = variables[1];

        //height at node points
        double tl = variables[2];
        double tr = variables[3];
        double dl = variables[4];
        double dr = variables[5];

        // Calculate height with bilinear interpolation of the heights of the nodes of the cell
        return (tl * (1 - offsetX) * (1 - offsetY) + tr * offsetX * (1 - offsetY) + dl * (1 - offsetX) * offsetY + dr * offsetX * offsetY);
    }
    double[] offsetsAndNodeHeights(double x,double y){
        double tl,tr,dl,dr;


        double offsetX = x - (int)x;
        double offsetY = y - (int)y;

        //height at node points
        tl = heightMap[(int)wrap(x,sizeX)][(int)(wrap(y,sizeY))];
        tr = heightMap[(int)wrap(x+1,sizeX)][(int)wrap(y,sizeY)];
        dl = heightMap[(int)wrap(x,sizeX)][(int)wrap(y+1,sizeY)];
        dr = heightMap[(int)wrap(x+1,sizeX)][(int)wrap(y+1,sizeY)];

        return new double[]{offsetY, offsetY,tl,tr,dl,dr};
    }
    double wrap(double i, int max){
        return i%max;
    }


}


