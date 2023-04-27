package org.example;

import java.io.IOException;


class Main{
    static int vyska = 1024;
    static int sirka = 1024;
    static Mapa mapa;

        public static void main(String[]args) throws IOException {
        mapa = new Mapa();
        mapa.setter(sirka,vyska,8,"Ibroke my pc");
        mapa.Build_Heighmap();
        Window.okno();

        }
    }

