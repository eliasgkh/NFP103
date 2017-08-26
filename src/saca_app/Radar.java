/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saca_app;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Eliasgkh
 */

public class Radar{
    
    int WIDTH = 900, HEIGHT = 270;
    
    private static final Object key = new Object();
    ArrayList<String>listAvions = new ArrayList<>();
    String[] information = {"Avion#","Nom Avion","X","Y","Altitude","Cap","Vitesse","Remarque"};
    String[][] t = new String [10][information.length];
    JTable table;
    JFrame frame;
    JFrame cframe;
    JPanel panel;
    JPanel cpanel;
    JButton close;
  //  JButton ctrl;
    
    int index = 0;
    
    public Radar(){
        frame = new JFrame ("Radar");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       
        close= new JButton ("Fermer");
        close.addActionListener((ActionEvent e) -> {
           System.exit(0);
        });
    
        /*
       ctrl = new JButton ("console de contrôle");
       ctrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent c) {
                cframe = new JFrame("console de contrôle");
                cpanel = new JPanel();
                cpanel.setPreferredSize (new Dimension(300, 400));
                cpanel.setBackground (Color.gray);
                cframe.getContentPane().add (cpanel);
                cframe.pack();
                cframe.setVisible(true);
            };
        });*/
        
        table = new JTable(t,information);
        table.setBackground(Color.white);
        table.setRowHeight(20);
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(850,223));
        panel = new JPanel();
        panel.setPreferredSize (new Dimension(WIDTH, HEIGHT));
        panel.setBackground (Color.white);
        panel.add(pane);
        panel.add(close);
    //    panel.add(ctrl);
        frame.getContentPane().add (panel);
        frame.pack(); 
        frame.setVisible(true);
    }
    
    
    void afficher(String id,String nom,String X,String Y,String altitude,String V,String cap,String remarque){
        synchronized (key) {
            boolean found =false;
           for (int i=0;i<listAvions.size();i++){
               
                if(listAvions.get(i).equals(id)){
                                      
                    table.setValueAt(nom, i, 1);
                    table.setValueAt(X, i, 2);
                    table.setValueAt(Y, i, 3);
                    table.setValueAt(altitude, i, 4);
                    table.setValueAt(cap, i, 5);
                    table.setValueAt(V, i, 6);
                    table.setValueAt(remarque, i, 7);
      
                    found=true;
                   break;
                }
           }
            if(found ==false){
                listAvions.add(id);
                table.setValueAt(id, index, 0);
                table.setValueAt(nom, index, 1);
                table.setValueAt(X, index, 2);
                table.setValueAt(Y, index, 3);
                table.setValueAt(altitude, index, 4);
                table.setValueAt(cap, index, 5);
                table.setValueAt(V, index, 6);
                table.setValueAt(remarque, index, 7);
                index++;
            }
        }
    } 
}
