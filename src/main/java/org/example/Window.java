package org.example;

import net.ouska.utils.IO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;


public class Window extends JComponent{

    static int vyska = Main.vyska;
    static int sirka = Main.sirka;

    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(sirka,vyska,BufferedImage.TYPE_INT_RGB);
        int[][] HM = Main.GetPixels(sirka,vyska);
        for (int i=0;i<sirka;i++){
            for (int j=0;j<vyska;j++){
                int t = HM[i][j];
                int p = (t<<16) | (t<<8) | t;
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
