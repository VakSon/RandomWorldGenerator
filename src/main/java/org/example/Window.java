package org.example;

import net.ouska.utils.IO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;


public class Window extends JComponent{

    static int vyska = Main.vyska;
    Mapa mapa = Main.mapa;
    static int sirka = Main.sirka;

    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(sirka,vyska,BufferedImage.TYPE_INT_RGB);
        Color start = new Color(255, 212, 121);
        Color end = new Color(0, 55, 10);
        float[][] HM = mapa.Heightmap;
        for (int i=0;i<sirka;i++){
            for (int j=0;j<vyska;j++){
                int red = (int)(start.getRed() * (1-HM[i][j]) + end.getRed() * HM[i][j]);
                int green = (int)(start.getGreen() * (1-HM[i][j]) + end.getGreen() * HM[i][j]);
                int blue = (int)(start.getBlue() * (1-HM[i][j]) + end.getBlue() * HM[i][j]);
                int p = (red<<16) | (green<<8) | blue;
                image.setRGB(i,j,p);

            }
        }
        g.drawImage(image, (getWidth()-sirka)/2, (getHeight()-vyska)/2, this);
    }
    public static void okno() throws IOException {
        JFrame frame = new JFrame("Main");
        frame.getContentPane().add(new Window());
        frame.setSize(sirka, vyska);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}