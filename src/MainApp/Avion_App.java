/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import saca_app.Avion_Com;
import saca_app.AvionEntity;
import saca_app.Avion;

/**
 *
 * @author Eliasgkh
 */
public class Avion_App {
    

    public static void main(String[] args) throws Exception {
        
         AvionEntity av = new AvionEntity();
         av.initialiser_avion();
         av.afficher_donnees();
         System.out.println(av.getRandomNumber());
         System.out.println(av.getX());
         System.out.println(av.getY());
         System.out.println(av.getcap());
         System.out.println(av.getvit());
         av.calcul_deplacement();
         System.out.println("ddd");
         System.out.println(av.getX());
         System.out.println(av.getY());
         System.out.println(av.getcap());
         System.out.println(av.getvit());
        
            Avion_Com a = new Avion_Com();
                a.start();
    System.out.println(av.getRandomNumber());
        }
        
        
      //  Avion_Com a = new Avion_Com(13,"avion2","Liban","New York",20,45,96);
      //  a.start();
//}
    
}
