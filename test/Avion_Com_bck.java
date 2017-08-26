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

public class Avion_Com_bck {
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
    
    public Avion_Com_bck(int id,String nom,String depart,String arrivee, double arrivee_x,double arrivee_y,double arrivee_z){
        this.id =id;
        this.nom = nom;
        this.depart = depart;
        this.arrivee = arrivee;
        this.arrivee_x = arrivee_x;
        this.arrivee_y = arrivee_y;
        this.arrivee_z = arrivee_z;
        position_x = 0;
        position_y = 0;
        position_z = 0;
        vitesse_x = 0;
        vitesse_y = 0;
        vitesse_z = 0;
        cap = 0;
        altitude = 0;
        
    }
    
    int modification(double vitesse_x,double vitesse_y,double vitesse_z,double cap,double altitude){
        this.vitesse_x = vitesse_x;
        this.vitesse_y = vitesse_y;
        this.vitesse_z = vitesse_z;
        this.cap = cap;
        this.altitude = altitude;
        
        int in = INTERVALLE/1000; //secondes
        position_x = position_x + ((vitesse_x)*in);
        position_y = position_y + ((vitesse_y)*in);
        position_z = position_z + ((vitesse_z)*in);
        
        if((position_x == arrivee_x) && (position_y == arrivee_y) && (position_z == arrivee_z) ){
            //arrivee a destination
            return -1;
        }
        return 0;
    }
    
    void traitement(Socket clientSocket){
        try{
            PrintWriter outToSaca = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader inFromSaca = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToSaca.println(id + "/" + nom + "/" + arrivee_x + "/" + arrivee_y + "/" + arrivee_z ); //evoie id/nom/arrivee
            outToSaca.flush();

            while(true){
                String [] s = inFromSaca.readLine().split("/"); //vitesse/cap/altitude
                int res = modification(Double.parseDouble(s[0]),Double.parseDouble(s[1]),Double.parseDouble(s[2]),Double.parseDouble(s[3]),Double.parseDouble(s[4]));
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
        
        Socket clientSocket = new Socket(hostName,portNumber);
        traitement(clientSocket);
        clientSocket.close();
   
    }   
}
