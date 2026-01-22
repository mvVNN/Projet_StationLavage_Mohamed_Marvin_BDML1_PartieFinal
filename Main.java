package stationlavage;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ###########Création de l'établissement ###########
        Etablissement e = new Etablissement("LavageDesMs", 50);
        
        // ##################################
        // Lancement du 1er scénario souris
        //###################################

        e.planifier();

        // ###########################################        
        // Lancement du 2eme: client existant et RDV Sale
        e.planifier();
        // ###########################################


        // ############################################
        // Sauvegarde pour vérifier les fichiers
        //############################################
        
        e.versFichierClients("clients.txt");
        e.versFichierRendezVous("rdv.txt");

        System.out.println("\n FIN du TEST");
    }
}







