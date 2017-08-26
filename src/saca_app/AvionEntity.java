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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import static java.sql.DriverManager.println;
import java.util.Arrays;
import java.util.Random;

public class AvionEntity extends Thread {

    public int ALTMAX = 20000;
    public int ALTMIN = 0;

    public int VITMAX = 1000;
    public int VITMIN = 200;

    public int PAUSE = 2000;
    int id ;
    Random rand = new Random();
// caractéristiques du déplacement de l'avion
    deplacement dep;
// coordonnées spatiales de l'avion
    coordonnees coord;
// numéro de vol de l'avion : code sur 5 caractéres
    char[] numero_vol = new char[6];
    ServerSocket socketDeCommunication;
    Socket socketDeConnection;
    boolean ouvrir_communication() {

        try {
            socketDeCommunication = new ServerSocket(6789);
            socketDeConnection = socketDeCommunication.accept();
        } catch (IOException ex) {
            //System.out.println(ex.getStackTrace());
            //return false;
        }
        // fonction à implémenter qui permet d'entrer en communication via TCP
        // avec le gestionnaire de vols
        return true;
    }

    void fermer_communication() {

        try {
            if (socketDeConnection.isConnected()) {
                socketDeConnection.close();
            }
        } catch (IOException ex) {
        }

        // fonction à implémenter qui permet de fermer la communication
        // avec le gestionnaire de vols
    }

    void envoyer_caracteristiques() {
        try {
            DataOutputStream outToClient = new DataOutputStream(socketDeConnection.getOutputStream());
            outToClient.writeUTF(get_donnees());
        } catch (IOException ex) {
        }
        // fonction à implémenter qui envoie l'ensemble des caractéristiques
        // courantes de l'avion au gestionnaire de vols
    }
// initialise aléatoirement les paramétres initiaux de l'avion

  public  int getRandomNumber() {
        return rand.nextInt(50) + 1;
    }

    private static char rndChar() {
        int rnd = (int) (Math.random() * 52); // or use Random or whatever
        char base = (rnd < 26) ? 'A' : 'a';
        return (char) (base + rnd % 26);

    }

  public void initialiser_avion() {
      
        coord = new coordonnees();
        dep = new deplacement();
        // intialisation des paramétres de l'avion
        coord.x = 100 + getRandomNumber() % 1000;
        coord.y = 1000 + getRandomNumber() % 1000;
        coord.altitude = 900 + getRandomNumber() % 100;
        id = getRandomNumber()*10 + 3;
        dep.cap = getRandomNumber() % 360;
        dep.vitesse = 600 + getRandomNumber() % 200;

        // initialisation du numero de l'avion : chaine de 5 caracteres 
        // formée de 2 lettres puis 3 chiffres
        numero_vol[0] = rndChar();
        numero_vol[1] = rndChar();
        System.out.println(numero_vol[2] + "%03d" + (getRandomNumber() % 999) + 1);
        numero_vol[5] = (char) id;
        System.out.println("Numero de vol " + numero_vol);
    }
  
  
  
  // obtenir coordonne de l'avion
  
    public  int getX() {
        return coord.x;
    };
      
    public  int getY() {
        return coord.y;
    };
    
    public  int getAlt() {
        return coord.altitude;
    };
    
     public  int getcap() {
        return dep.cap;
    };
     
      public  int getvit() {
        return dep.vitesse;
    };
      
     public int getid(){
          return  id;

      };

      public String getnom(){
          return  "a03d" + id;

      };

// modifie la valeur de l'avion avec la valeur pass�e en param�tre

    public void changer_vitesse(int vitesse) {
        if (vitesse < 0) {
            dep.vitesse = 0;
        } else if (vitesse > VITMAX) {
            dep.vitesse = VITMAX;
        } else {
            dep.vitesse = vitesse;
        }
    }

// modifie le cap de l'avion avec la valeur passée en paramètre
   public void changer_cap(int cap) {
        if ((cap >= 0) && (cap < 360)) {
            dep.cap = cap;
        }
    }

// modifie l'altitude de l'avion avec la valeur passée en paramètre
    void changer_altitude(int alt) {
        if (alt < 0) {
            coord.altitude = 0;
        } else if (alt > ALTMAX) {
            coord.altitude = ALTMAX;
        } else {
            coord.altitude = alt;
        }
    }

// affiche les caractéristiques courantes de l'avion
  public  void afficher_donnees() {
        System.out.println(get_donnees());
    }

    String get_donnees() {
        return "Avion " + numero_vol+ " -> localisation : (" + coord.x + "," + coord.y + "), altitude : " + coord.altitude + ", vitesse : "
                + dep.vitesse + ", cap :" + dep.cap;
    }

// recalcule la localisation de l'avion en fonction de sa vitesse et de son cap
 public   void calcul_deplacement() {
        double cosinus, sinus;
        double dep_x, dep_y;
        int nb;

        if (dep.vitesse < VITMIN) {
            System.out.println("Vitesse trop faible : crash de l'avion\n");
            fermer_communication();
            return;
        }
        if (coord.altitude == 0) {
            System.out.println("L'avion s'est ecrase au sol\n");
            fermer_communication();
            return;
        }

        cosinus = Math.cos(dep.cap * 2 * Math.PI / 360);
        sinus = Math.sin(dep.cap * 2 * Math.PI / 360);

        dep_x = cosinus * dep.vitesse * 10 / VITMIN;
        dep_y = sinus * dep.vitesse * 10 / VITMIN;

        if ((dep_x > 0) && (dep_x < 1)) {
            dep_x = 1;
        }
        if ((dep_x < 0) && (dep_x > -1)) {
            dep_x = -1;
        }

        if ((dep_y > 0) && (dep_y < 1)) {
            dep_y = 1;
        }
        if ((dep_y < 0) && (dep_y > -1)) {
            dep_y = -1;
        }

        coord.x = coord.x + (int) dep_x;
        coord.y = coord.y + (int) dep_y;

        // afficher_donnees();
    }

  public  void se_deplacer() {
        while (true) {

            try {
                ouvrir_communication();
                Thread.sleep(PAUSE);
                calcul_deplacement();
                envoyer_caracteristiques();
                fermer_communication();
            } catch (InterruptedException ex) {
            }
        }
    }

   @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {

        //afficher_donnees();
        // on quitte si on arrive à pas contacter le gestionnaire de vols
        if (!ouvrir_communication()) {
            System.out.println("Impossible de contacter le gestionnaire de vols\n");
            return;
        }

        // on se déplace une fois toutes les initialisations faites
        se_deplacer();

    }
   
}
