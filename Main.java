package stationlavage;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ###########Création de l'établissement ###########
        Etablissement e = new Etablissement("LavageDesMs", 50);
                Scanner sc = new Scanner(System.in);

        int choix = -1;

        while (choix != 0) {
            // ###########Menu  ###########
            System.out.println("\n########### MENU ###########");
            System.out.println("1- Planifier un rendez-vous");
            System.out.println("2- Afficher tous les clients");
            System.out.println("3- Afficher le planning d'un jour");
            System.out.println("4- Afficher les rendez-vous d'un client");
            System.out.println("5- Sauvegarder les donnees");
            System.out.println("0- Quitter");
            System.out.print("choix : ");

            try {
                choix = Integer.parseInt(sc.nextLine());
            } catch (Exception e1) {
                choix = -1;
            }

            switch (choix) {

                case 1:
                    e.planifier();
                    break;

                case 2:
                    e.afficherClients();
                    break;

                case 3:
                    System.out.print("Date (yyyy-mm-dd) : ");
                    try {
                        LocalDate d = LocalDate.parse(sc.nextLine());
                        e.afficherPlanningJour(d);
                    } catch (Exception ex) {
                        System.out.println("Date invalide.");
                    }
                    break;

                case 4:
                    System.out.print("Numero du client : ");
                    try {
                        int num = Integer.parseInt(sc.nextLine());
                        e.afficherRdvClient(num);
                    } catch (Exception ex) {
                        System.out.println("Numero invalide.");
                    }
                    break;

                case 5:
                    e.versFichierClients("clients.txt");
                    e.versFichierRendezVous("rdv.txt");
                    System.out.println("Sauvegarde effectuee.");
                    break;

                case 0:
                    e.versFichierClients("clients.txt");
                    e.versFichierRendezVous("rdv.txt");
                    System.out.println("Fin du programme.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}








