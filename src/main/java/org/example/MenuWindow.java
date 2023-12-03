package org.example;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    private JLabel vysledekLab;
    private JButton sectiBut;
    private JTextField cislo1Field;
    private JTextField cislo2Field;
    private JPanel panel1;
    private JPanel panel2;

    public MenuWindow()
    {
        super("Kalkulačka");
        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        Color barva = new Color(150, 75, 130);

        panel1 = new JPanel();
        panel1.setBackground(Color.red);
        add(panel1);

        panel2 = new JPanel();
        panel2.setBackground(barva);
        add(panel2);

        cislo1Field = new JTextField("1. číslo", 5);
        panel1.add(cislo1Field);

        cislo2Field = new JTextField("2. číslo", 5);
        panel1.add(cislo2Field);

        sectiBut = new JButton("Sečti");
        panel1.add(sectiBut);

        vysledekLab = new JLabel("Výsledek je ");
        panel2.add(vysledekLab);
    }
    public static void main(String[] args) {
        MenuWindow okno = new MenuWindow();
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();
        okno.setLocationRelativeTo(null);

    }
}
