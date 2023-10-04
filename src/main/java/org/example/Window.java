package org.example;

//import net.ouska.utils.IO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Window extends JComponent{

    static int vyska = Settings.sizeY;
    float[][] x;
    static int sirka = Settings.sizeX;
    JFrame frame = new JFrame("Main");


    public void paint(Graphics g) {
        if (this.x != null){
            BufferedImage image = new BufferedImage(sirka,vyska,BufferedImage.TYPE_INT_RGB);
            Color start = new Color(255, 212, 121);
            Color end = new Color(0, 55, 10);
            for (int i=0;i<sirka;i++){

                for (int j=0;j<vyska;j++){
//                float realValue = (float) 1 - ((HM[i][j] - min) / (max - min));
                    //float realValue = (max * (1 - (distance) + (distance) * min));
                    float realValue = x[i][j];
                    int red = 6;
                    int blue = 66;
                    int green = 112;
                    if (/*realValue >= 0.55*/true){
                        red = (int) (start.getRed() * (1 - realValue) + end.getRed() * realValue);
                        green = (int) (start.getGreen() * (1 - realValue) + end.getGreen() * realValue);
                        blue = (int) (start.getBlue() * (1 - realValue) + end.getBlue() * realValue);
                    }
                    int p = (red<<16) | (green<<8) | blue;
                    image.setRGB(i,j,p);

                }
            }
            g.drawImage(image, (getWidth()-sirka)/2, (getHeight()-vyska)/2, this);
        }

    }
    public void okno() throws IOException {
        frame.getContentPane().add(this);
        frame.setSize(sirka, vyska);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
    public void update (Graphics g,String title,float[][] printable)
    {
        x = printable;
        paint (g);
        frame.setTitle(title);
    }
}
