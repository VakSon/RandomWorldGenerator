package org.example;

import java.util.Random;

public class HydraulicErosion {

    int sizeX;
    int sizeY;
    float[][] heightMap;

    int dropsNum = Settings.dropsNum;
    int maxLifeTime = 30;
    float evaporateSpeed = 0.1f;
    float waterVolume = 1f;


    public HydraulicErosion(float[][] heightMap){
        sizeX = heightMap.length;
        sizeY = heightMap[0].length;
        this.heightMap = heightMap;
        erode();
    }
    public void erode(){
        Random r = new Random(Settings.seed);
        for(int drops = 0; drops < dropsNum;drops++){
            droplet(r.nextInt(sizeX),r.nextInt(sizeY));
        }
    }
    public void droplet(int startingX ,int startingY){
        //represents how many tiles it will go for one lifetime cyclus
        float speed = 5f;


        for (int lifetime = 0; lifetime<maxLifeTime;lifetime++){

            //for it

        }


    }
}
