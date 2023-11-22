package org.example;

import java.io.IOException;


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
        Window d = new Window();
        d.okno();
        d.update(d.getGraphics(), "hydraulic",h.heightMap);
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

