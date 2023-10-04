package org.example;

import java.io.IOException;


class Main{
    static int vyska = Settings.sizeY;
    static int sirka = Settings.sizeX;
    static Mapa mapa;

    static Window w;

        public static void main(String[]args) throws IOException {
        //mapa = new Mapa();
        //mapa.Build_Heighmap();

        w = new Window();
        w.okno();

        Lithosphere l = new Lithosphere();
        System.out.println("Lithosphere is created.");
        w.update(w.getGraphics(),"Done",Lithosphere.Img());

        }
    }

