package org.example;

import java.io.IOException;
import java.util.Random;


class Main{
    static int sizeY = Settings.sizeY;
    static int sizeX = Settings.sizeX;
    static Mapa mapa;

    static Window w;

        public static void main(String[]args) throws IOException, InterruptedException {
        //Mapa mapa = new Mapa();
        //mapa.Build_Heighmap();




        w = new Window();
        w.okno();
//
//
        w.update(w.getGraphics(), "Perloun", PerlounNoise.preSettedNoise());
        HydraulicErosion h = new HydraulicErosion(PerlounNoise.preSettedNoise());
        int x = 1;
        while (true){
            x++;
            w.update(w.getGraphics(), "hydraulic" + x,h.heightMap);
            h.dropsNum = 1000;
            h.run();
            Thread.sleep(500);
        }

        /*while(true){
            w.update(w.getGraphics(), "Perloun", PerlounNoise.preSettedNoise());
            Thread.sleep(2000);

            w.update(w.getGraphics(), "Perlin", PerlinNoise.WholeNoise(Settings.seed, sizeX, sizeY, 8));
            Thread.sleep(2000);
        }*/
        //Lithosphere l = new Lithosphere();
        //System.out.println("Lithosphere is created.");
        //w.update(w.getGraphics(),"Done",Lithosphere.Img());

        }
    }

