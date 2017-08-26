/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import java.io.IOException;
//import saca_app.AvionEntity;
//import saca_app.Avion;
import saca_app.Radar;
import saca_app.Saca;

/**
 *
 * @author Eliasgkh
 */
public class Saca_App {

    public static void main(String[] args) throws IOException, Exception {
        Radar radar = new Radar();
        Saca  c = new Saca(radar);
        c.start();
     
    }
    
    }  

