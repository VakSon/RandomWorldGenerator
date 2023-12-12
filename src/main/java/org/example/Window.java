package org.example;

//import net.ouska.utils.IO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Window extends Component {

    static int vyska = Settings.sizeY;

    static int sirka = Settings.sizeX;
    float[][] heightMap;
    Frame frame = new Frame("Main");

    float seaLevel = Settings.seaLevel;


    public void paint() {
        Graphics g = this.getGraphics();
        if (this.heightMap != null){
            BufferedImage image = new BufferedImage(sirka,vyska,BufferedImage.TYPE_INT_RGB);
            Color lowGround = new Color(171, 192, 73);
            Color highGround = new Color(0, 66, 15);
            Color lowSea = new Color(7, 71, 94);
            Color highSea = new Color(15, 156, 204);
            for (int i=0;i<sirka;i++){
                for (int j=0;j<vyska;j++){
                    //dsfsf
                    int red = 6;
                    int blue = 66;
                    int green = 112;
                    if (heightMap[i][j] >= seaLevel){
                        float hieghtValue = (heightMap[i][j]-seaLevel)/(1-seaLevel);
                        red = (int) (lowGround.getRed() * (1 - hieghtValue) + highGround.getRed() * hieghtValue);
                        green = (int) (lowGround.getGreen() * (1 - hieghtValue) + highGround.getGreen() * hieghtValue);
                        blue = (int) (lowGround.getBlue() * (1 - hieghtValue) + highGround.getBlue() * hieghtValue);
                    }else {
                        float hieghtValue = heightMap[i][j]/seaLevel;
                        red = (int) (lowSea.getRed() * (1 - hieghtValue) + highSea.getRed() * hieghtValue);
                        green = (int) (lowSea.getGreen() * (1 - hieghtValue) + highSea.getGreen() * hieghtValue);
                        blue = (int) (lowSea.getBlue() * (1 - hieghtValue) + highSea.getBlue() * hieghtValue);
                    }
                    int p = (red<<16) | (green<<8) | blue;
                    image.setRGB(i,j,p);

                }
            }
            g.drawImage(image, (getWidth()-sirka)/2, (getHeight()-vyska)/2, this);
        }

    }
    public Window() throws IOException {
        frame.add(this);
        frame.setSize(sirka, vyska);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);


    }
    public void update (String title,float[][] printable)
    {
        heightMap = printable;
        paint ();
        frame.setTitle(title);
    }
}
