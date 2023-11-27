package org.example;

import java.util.*;

import static java.lang.Double.isNaN;

public class HydraulicErosion {

    int sizeX;
    int sizeY;
    public float[][] heightMap;
    int dropsNum = 1000;
    int maxLifeTime = 30;
    float evaporateSpeed = 0.01f;
    float water = 1f;
    float minimalWaterVolume = 0.05f;
    int radius = 2;
    float inertia = 0.1f;
    float erosionSpeed = 0.3f;
    float depositionSpeed = 0.3f;
    float minSedimentCapacity = 0.01f;
    float minSpeed = 0.05f;
    float sedimentCapacityFactor = 2;

    public HydraulicErosion(float[][] heightMap){
        sizeX = heightMap.length;
        sizeY = heightMap[0].length;
        this.heightMap = heightMap;
        run();
    }
    public void run(){
        float totalMeshBefore = 0;
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                totalMeshBefore += heightMap[i][j];
            }
        }
        Random r = new Random(Settings.seed);
        for(int drops = 0; drops < dropsNum;drops++){
            droplet(r.nextFloat(sizeX),r.nextFloat(sizeY));
            //System.out.println();
        }
        float totalMeshAfter = 0;
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                totalMeshAfter += heightMap[i][j];
            }
        }
        System.out.println(totalMeshBefore + "     " + totalMeshAfter);
    }
    public void droplet(float x ,float y){
        //represents how many tiles it will go for one lifetime cyclus
        float speed = 1f;
        float height = evaluateHeight(x,y);
        float[] direction = new float[2];
        float dwater = water;
        float dsediment = 0f;


        for (int lifetime = 0; lifetime<maxLifeTime;lifetime++){

            //create new direction vector
            float[] newDirection = evaluateDirection(x,y);

            direction[0] = direction[0]*inertia - newDirection[0]*( 1 - inertia);
            direction[1] = direction[1]*inertia - newDirection[1]*( 1 - inertia);

            //normalise the vector (to have length of one)
            float lengthOfDirection = (float)Math.sqrt(direction[0]*direction[0] + direction[1]*direction[1]);

            if(lengthOfDirection != 0) {
                direction[0] = direction[0] / lengthOfDirection;
                direction[1] = direction[1] / lengthOfDirection;
            }

            x += direction[0];
            y += direction[1];

            if ((direction[0] ==0 && direction[1] == 0) ||x < 0 || x>sizeX || y< 0 ||y>sizeY || dwater < minimalWaterVolume){
                break;
            }

            //count the erosion or sedimentation amount

            float newHeight = evaluateHeight(x,y);
            float diffHeight = newHeight-height;

            //calculate maximal sediment capacity in new pos
            float sedimentCapacity = Math.max(minSedimentCapacity, -diffHeight * speed * dwater * sedimentCapacityFactor);

            //if moving uphill or carrying more than it should
            if (dsediment > sedimentCapacity || diffHeight > 0){


                float amountToDeposit = (diffHeight > 0) ? Math.min(diffHeight, dsediment) : (dsediment - sedimentCapacity) * depositionSpeed;
                dsediment -= amountToDeposit;

                //add sediment to four nodes using bilinear interpolation (again :((( )
                heightMap[(int) wrap( x,sizeX)][(int) wrap(y, sizeY)] += amountToDeposit * (1 - (x%1)) * (1 - (y %1));
                heightMap[(int) wrap(x + 1,sizeX)][(int) wrap(y,sizeY)] += amountToDeposit * (x%1) * (1 - (y %1));
                heightMap[(int) wrap (x,sizeX)][(int) wrap(y + 1,sizeY)] += amountToDeposit * (1 - (x%1)) * ( y %1 );
                heightMap[(int) wrap (x+1,sizeX)][(int) wrap(y+1,sizeY)] += amountToDeposit * (x%1) * (y %1);
            }
            else {
                //erode part of ground around droplet
                //calculate amount to erode cannot be higher than diff in height => create pit holes
                float amountToErode = Math.min((sedimentCapacity - dsediment) * erosionSpeed,-diffHeight);

                //for all nodes in radius from NW node
                List<Float> weights = new ArrayList<>();

                List<Integer> indexes = new ArrayList<>();

                float weightSum = 0;

                int index = 0;
                for (int i = -radius; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++) {
                        //distance to x,y
                        float distance = (float) Math.sqrt(i*i + j*j);
                        //if its in range
                        if (Math.sqrt(i*i + j*j) <= radius){
                            //weight
                            float weight = 1 - distance/radius;
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
                    double amountToErodeWeighted = weights.get(i)*amountToErode;
                    double deltaSediment = (heightMap[posX][posY] < amountToErodeWeighted) ? heightMap[posX][posY] : amountToErodeWeighted;
                    heightMap[posX][posY] -= deltaSediment;
                    dsediment += deltaSediment;
                }
            }

            //update speed and amount of water
            speed = (float) Math.sqrt(speed*speed - diffHeight * 4);
            if (isNaN(speed)) {
                speed = minSpeed;
            }
            dwater = (1-evaporateSpeed);
        }
    }
    float[] evaluateDirection(float x,float y){

        float[] variables = offsetsAndNodeHeights(x,y);

        float offsetX = variables[0];
        float offsetY = variables[1];

        //height at node points
        float tl = variables[2];
        float tr = variables[3];
        float dl = variables[4];
        float dr = variables[5];

        float gradientX = (tr - tl) * (1 - offsetY) + (dr - dl) * offsetY;
        float gradientY = (dl - tl) * (1 - offsetX) + (dr - tl) * offsetX;


        //System.out.println("\nHeightNW " + tl +" HeightNE " + tr + "\nX " + x + " Y " + y + "\nHeightSW " + dl +" HeightSE " + dr + "\nGradient X " + gradientX + " GradientY " + gradientY + "\n");

        return new float[]{gradientX,gradientY};
    }
    float evaluateHeight(float x,float y){

        float[] variables = offsetsAndNodeHeights(x,y);

        float offsetX = variables[0];
        float offsetY = variables[1];

        //height at node points
        float tl = variables[2];
        float tr = variables[3];
        float dl = variables[4];
        float dr = variables[5];

        // Calculate height with bilinear interpolation of the heights of the nodes of the cell
        return (tl * (1 - offsetX) * (1 - offsetY) + tr * offsetX * (1 - offsetY) + dl * (1 - offsetX) * offsetY + dr * offsetX * offsetY);
    }
    float[] offsetsAndNodeHeights(float x,float y){
        float tl,tr,dl,dr;


        float offsetX = x - (int)x;
        float offsetY = y - (int)y;

        //height at node points
        tl = heightMap[(int)wrap(x,sizeX)][(int)(wrap(y,sizeY))];
        tr = heightMap[(int)wrap(x+1,sizeX)][(int)wrap(y,sizeY)];
        dl = heightMap[(int)wrap(x,sizeX)][(int)wrap(y+1,sizeY)];
        dr = heightMap[(int)wrap(x+1,sizeX)][(int)wrap(y+1,sizeY)];

        return new float[]{offsetY, offsetY,tl,tr,dl,dr};
    }
    float wrap(float i, int max){
        return i%max;
    }


}


