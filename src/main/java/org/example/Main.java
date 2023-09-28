package org.example;

import java.io.IOException;


class Main{
    static int vyska = Settings.vyska;
    static int sirka = Settings.sirka;
    static Mapa mapa;

        public static void main(String[]args) throws IOException {
        //mapa = new Mapa();
        //mapa.setter(sirka,vyska,8,"Ibrle my pc");
        //mapa.Build_Heighmap();
        Lithosphere l = new Lithosphere(Settings.plateSum,sirka,vyska,garbagefunctions.Seed2Long("Iffzdtf"));
        System.out.println("Lithosphere is created.");
        Window.okno();

        }
    }

