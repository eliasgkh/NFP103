/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saca_app;

/**
 *
 * @author Eliasgkh
 */

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;

public class Avion_Com {
    static final int INTERVALLE = 1000;//millisecondes
    static final String hostName = "localhost";
    static final int portNumber = 350;
    int id;
    String nom;
    String depart,arrivee;
    double arrivee_x,arrivee_y,arrivee_z;
    double cap,altitude;
    double vitesse_x,vitesse_y,vitesse_z;
    double position_x,position_y,position_z;
    
    public Avion_Com(){
        AvionEntity av = new AvionEntity();
         av.initialiser_avion();
         id = av.getid();
         nom = av.getnom();
         arrivee_x = av.getX();
         arrivee_y = av.getY();
         altitude = av.getAlt();
         vitesse_x = av.getvit();
         cap = av.getcap();
       
    }
    
    int modification(double vitesse,double altitude,double cap){
        this.vitesse_x = vitesse;
        this.cap = cap;
        this.altitude = altitude;
        
        int in = INTERVALLE/1000; //secondes
        position_x = arrivee_x + ((vitesse)*in);
        position_y = arrivee_y + ((vitesse)*in);
        position_z = altitude + ((vitesse)*in);
        
        return 0;
    }
    
    void traitement(Socket clientSocket){
        try{
            PrintWriter outToSaca = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader inFromSaca = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToSaca.println(id + "/" + nom + "/" + arrivee_x + "/" + arrivee_y + "/" + altitude ); //evoie id/nom/arrivee
            outToSaca.flush();

            while(true){
                String [] s = inFromSaca.readLine().split("/"); //vitesse/cap/altitude
                int res = modification(Double.parseDouble(s[2]),Double.parseDouble(s[3]),Double.parseDouble(s[4]));
                if(res == -1){
                    outToSaca.println(position_x + "/" + position_y + "/" + position_z + "/end");
                    outToSaca.flush();
                    break;
                }
                sleep(INTERVALLE);

                outToSaca.println(position_x + "/" + position_y + "/" + position_z);
                outToSaca.flush();
            }
            outToSaca.close();
            inFromSaca.close();
        }catch(Exception e){System.out.println("arret d'execution"); System.exit(0);}
    }
    
public void start ()throws Exception{
        
        try (Socket clientSocket = new Socket(hostName,portNumber)) {
            traitement(clientSocket);
        }
   
    }   
}
