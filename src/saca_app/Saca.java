/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saca_app;

import java.net.*;
import java.io.*;

/**
 *
 * @author Eliasgkh
 */

public class Saca {
    static final int portNumber = 350;
    Radar radar;
    private static final Object key = new Object();
    
    public Saca(Radar radar){
        this.radar = radar;
    }
    
    void traitement(Socket connectionSocket){
        
        String resultat; 
        String []s;
        Ctrl c = new Ctrl();
        try{
       
            BufferedReader inFromClient =new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            PrintWriter outToClient =new PrintWriter(connectionSocket.getOutputStream()); 

            s = inFromClient.readLine().split("/"); //id/nom/arrivee

                radar.afficher(s[0], s[1], "0", "0", "0","0","0","Depart");
                resultat = c.calcul(Double.parseDouble(s[2]),Double.parseDouble(s[3]),Double.parseDouble(s[4]),0,0,0,0);


            while (true){

                outToClient.println(resultat);
                outToClient.flush();

                String [] position = inFromClient.readLine().split("/");;
                String [] res = resultat.split("/");
                if((position.length >= 4)&&(position[3].equals("end"))){
                    radar.afficher(s[0], s[1], position[0], position[1],position[2], res[0], res[1],"Arrivee");
                    inFromClient.close();
                    outToClient.close();
                    connectionSocket.close();
                }
                else{

                        radar.afficher(s[0], s[1], position[0], position[1],position[2], res[0], res[1],"-");
                        resultat = c.calcul(Double.parseDouble(position[0]),Double.parseDouble(position[1]),Double.parseDouble(position[2]),Double.parseDouble(res[3]),Double.parseDouble(res[4]),0,0);
                }

            }
        }catch(Exception e){System.out.println("arret d'execution"); System.exit(0);}
    }
    
    public void start ()throws Exception{
        
        ServerSocket welcomeSocket = new ServerSocket(portNumber);

        while (true) {
        Socket connectionSocket = welcomeSocket.accept();
        
        new Thread(() -> {
            try{
                traitement(connectionSocket);
            }
            catch(Exception e){}
        }).start();
        
        }
    }
}
