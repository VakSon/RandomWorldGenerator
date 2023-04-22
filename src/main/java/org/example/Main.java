package org.example;

import java.io.IOException;


class Main{
    static int vyska = 600;
    static int sirka = 800;
    public static int[][] GetPixels(int sirka,int vyska){
        float[][] x = perlin_noise.GeneratePerlinNoise(perlin_noise.GenerateWhiteNoise(sirka,vyska),8);
        int[][] z = new int[sirka][vyska];
        for (int i = 0; i < sirka; i++){
            for (int j = 0; j < vyska; j++){
                z[i][j] = (int)(0 * (1-x[i][j]) + 255 * x[i][j]);
            }
        }
        return z;
    }

        public static void main(String[]args) throws IOException {

            Window.okno();

        }
    }

