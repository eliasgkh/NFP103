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
public class Ctrl {
  double arrivee_x,arrivee_y,arrivee_z;

    
    String calcul( double position_x, double position_y, double altitude, double cap){
        
        return (modification(position_x, position_y, altitude, cap));
    }
    
    String calcul( double arrivee_x,double arrivee_y, double arrivee_z,double position_x, double position_y, double position_z, double cap){
       this.arrivee_x = arrivee_x;
       this.arrivee_y = arrivee_y;
       this.arrivee_z = arrivee_z;
       return (modification(position_x, position_y, position_z, cap )); //z et altitude est le meme
    }
     
    String modification(double position_x, double position_y, double altitude, double cap ){
        
       String resultat = ((Math.random()*100)+200) + "/" + (Math.random()*100) + "/" +  (Math.random()*100) + "/" + (Math.random()*100) + "/" + (Math.random()*100) ;

       return resultat; //vitesse/cap/altitude
    }   
}
