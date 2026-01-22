package stationlavage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Etablissement {

    private String nom;

    // ########### CLIENTS ###########
    private Client[] clients;
    private int nbClients;
    private int prochainNumeroClient;

    // ########### PLANNING ###########

    private RendezVous[][] planning;

    private static final int NB_JOURS = 7;
    private static final LocalTime OUVERTURE = LocalTime.of(10, 0);
    private static final LocalTime FERMETURE = LocalTime.of(18, 0);
    private static final int PAS_MIN = 30;
    private static final int NB_CRENEAUX = 16; 

    private Scanner sc = new Scanner(System.in);

    // ########### CONSTRUCTEUR ###########
    public Etablissement(String nom, int maxClients) {
        this.nom = nom;
        this.clients = new Client[maxClients];
        this.nbClients = 0;
        this.prochainNumeroClient = 1;
        this.planning = new RendezVous[NB_CRENEAUX][NB_JOURS];
    }

    // #################################
    // ########### CLIENTS ###########
    // #################################

    public Client rechercher(String nom, String telephone) {
        for (int i = 0; i < nbClients; i++) {
            if (clients[i].getNom().equalsIgnoreCase(nom)
                    && clients[i].getTelephone().equals(telephone)) {
                return clients[i];
            }
        }
        return null;
    }

    public Client ajouter(String nom, String telephone) {
        Client c = rechercher(nom, telephone);
        if (c != null) return c;
        if (nbClients >= clients.length) return null;

        c = new Client(prochainNumeroClient++, nom, telephone);
        insererClientTrie(c);
        return c;
    }

    public Client ajouter(String nom, String telephone, String email) {
        Client c = rechercher(nom, telephone);
        if (c != null) return c;
        if (nbClients >= clients.length) return null;

        c = new Client(prochainNumeroClient++, nom, telephone, email);
        insererClientTrie(c);
        return c;
    }

    private void insererClientTrie(Client c) {
        int pos = 0;
        while (pos < nbClients && c.placerApres(clients[pos])) pos++;

        for (int i = nbClients; i > pos; i--) {
            clients[i] = clients[i - 1];
        }
        clients[pos] = c;
        nbClients++;
    }

    public void afficherClients() {
        for (int i = 0; i < nbClients; i++) {
            System.out.println(clients[i]);
        }
    }

    public void afficherClients(String nomOuTel) {
        boolean trouve = false;
        for (int i = 0; i < nbClients; i++) {
            Client c = clients[i];
            if (c.getNom().equalsIgnoreCase(nomOuTel)
                    || c.getTelephone().equals(nomOuTel)) {
                System.out.println(c);
                trouve = true;
            }
        }
        if (!trouve) System.out.println("Aucun client trouvé.");
    }

    // #################################
    // ########### PLANNING ###########
    // #################################

    private boolean ouvert(LocalDate d) {
        return d.getDayOfWeek() != DayOfWeek.MONDAY;
    }

    private int indexJour(LocalDate d) {
        long diff = ChronoUnit.DAYS.between(LocalDate.now(), d);
        if (diff < 0 || diff >= NB_JOURS) return -1;
        return (int) diff;
    }

    private int indexCreneau(LocalTime t) {
        long minutes = ChronoUnit.MINUTES.between(OUVERTURE, t);
        if (minutes < 0) return -1;
        if (t.equals(FERMETURE) || t.isAfter(FERMETURE)) return -1;
        if (minutes % PAS_MIN != 0) return -1;

        int idx = (int) (minutes / PAS_MIN);
        if (idx < 0 || idx >= NB_CRENEAUX) return -1;
        return idx;
    }

    private LocalTime timeFromIndex(int idx) {
        return OUVERTURE.plusMinutes(idx * PAS_MIN);
    }

    private boolean estLibre(int c, int j) {
        return planning[c][j] == null;
    }

    private boolean creneauValide(LocalDateTime dt) {
        int j = indexJour(dt.toLocalDate());
        int c = indexCreneau(dt.toLocalTime());
        if (j == -1 || c == -1) return false;
        if (!ouvert(dt.toLocalDate())) return false;
        return estLibre(c, j);
    }

    private void placerRdv(RendezVous rdv) {
        int j = indexJour(rdv.getCreneau().toLocalDate());
        int c = indexCreneau(rdv.getCreneau().toLocalTime());
        planning[c][j] = rdv;
    }

    // #################################
    // ########### Rechecher ###########
    // #################################

    public LocalDateTime rechercher(LocalDate jour) {
        if (!ouvert(jour)) return null;

        int j = indexJour(jour);
        if (j == -1) return null;

        int[] choix = new int[NB_CRENEAUX];
        int n = 0;

        for (int c = 0; c < NB_CRENEAUX; c++) {
            if (estLibre(c, j)) {
                System.out.println((n + 1) + " - " + timeFromIndex(c));
                choix[n++] = c;
            }
        }

        if (n == 0) return null;

        int rep = Integer.parseInt(sc.nextLine());
        if (rep < 1 || rep > n) return null;

        return LocalDateTime.of(jour, timeFromIndex(choix[rep - 1]));
    }

    public LocalDateTime rechercher(LocalTime heure) {
        int c = indexCreneau(heure);
        if (c == -1) return null;

        int[] choix = new int[NB_JOURS];
        int n = 0;

        for (int j = 0; j < NB_JOURS; j++) {
            LocalDate d = LocalDate.now().plusDays(j);
            if (ouvert(d) && estLibre(c, j)) {
                System.out.println((n + 1) + " - " + d);
                choix[n++] = j;
            }
        }

        if (n == 0) return null;

        int rep = Integer.parseInt(sc.nextLine());
        if (rep < 1 || rep > n) return null;

        return LocalDateTime.of(LocalDate.now().plusDays(choix[rep - 1]), heure);
    }

    public RendezVous ajouter(Client cl, LocalDateTime dt, char cat, boolean interieur) {
        if (!creneauValide(dt)) return null;
        RendezVous rdv = new RendezVous(cl, new PrestationExpress(cat, interieur), dt);
        placerRdv(rdv);
        return rdv;
    }

    public RendezVous ajouter(Client cl, LocalDateTime dt, char cat) {
        if (!creneauValide(dt)) return null;
        RendezVous rdv = new RendezVous(cl, new PrestationSale(cat), dt);
        placerRdv(rdv);
        return rdv;
    }

    public RendezVous ajouter(Client cl, LocalDateTime dt, char cat, int type) {
        if (!creneauValide(dt)) return null;
        RendezVous rdv = new RendezVous(cl, new PrestationTresSale(cat, type), dt);
        placerRdv(rdv);
        return rdv;
    }

    // #################################
    // ########### PARTIE 2 ###########
    // #################################

    public void planifier() {
        System.out.print("Nom : ");
        String nomC = sc.nextLine();
        System.out.print("Telephone : ");
        String tel = sc.nextLine();

        Client cl = rechercher(nomC, tel);
        if (cl == null) {
            System.out.print("Email : ");
            String email = sc.nextLine();
            cl = email.isEmpty() ? ajouter(nomC, tel) : ajouter(nomC, tel, email);
        }

        System.out.println("ecrire 1 pour choisir le jour / 2 pour heure");
        String ch = sc.nextLine();

        LocalDateTime dt;
        if (ch.equals("1")) {
            System.out.print("Date yyyy-MM-dd : ");
            dt = rechercher(LocalDate.parse(sc.nextLine()));
        } else {
            System.out.print("Heure HH:mm : ");
            dt = rechercher(LocalTime.parse(sc.nextLine()));
        }

        if (dt == null) {
            System.out.println("Pas de creneau.");
            return;
        }

        System.out.println("Taper 1 pour Express / 2 pour Sale / 3 pour Tres sale");
        String type = sc.nextLine();

        System.out.print("Choissiez la catégorie de votre véhicule A,B, ou C : ");
        char cat = sc.nextLine().toUpperCase().charAt(0);

        RendezVous rdv;
        if (type.equals("1")) {
            System.out.print("Nettoyage de l'Interieur (taper true ou false)  : ");
            rdv = ajouter(cl, dt, cat, Boolean.parseBoolean(sc.nextLine()));
        } else if (type.equals("2")) {
            rdv = ajouter(cl, dt, cat);
        } else {
            System.out.print("Type salissure (1-4) : ");
            rdv = ajouter(cl, dt, cat, Integer.parseInt(sc.nextLine()));
        }

        System.out.println(rdv != null ? "RDV ajouté : " + rdv : "Erreur RDV");
    }

    // #################################
    // ########### AFFICHAGES ###########
    // #################################

    public void afficherPlanningJour(LocalDate jour) {
        int j = indexJour(jour);
        if (j == -1 || !ouvert(jour)) return;

        for (int c = 0; c < NB_CRENEAUX; c++) {
            System.out.print(timeFromIndex(c) + " : ");
            System.out.println(planning[c][j] == null ? "LIBRE" : planning[c][j]);
        }
    }

    public void afficherRdvClient(int num) {
        for (int j = 0; j < NB_JOURS; j++) {
            for (int c = 0; c < NB_CRENEAUX; c++) {
                RendezVous r = planning[c][j];
                if (r != null && r.getClient().getNumero() == num) {
                    System.out.println(r);
                }
            }
        }
    }

    // #################################
    // ########### Export ###########
    // #################################

    public void versFichierClients(String nomFichier) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nomFichier));
            for (int i = 0; i < nbClients; i++) {
                Client c = clients[i];
                if (c.aEmail())
                    pw.println(c.getNumero() + " : " + c.getNom() + " : " + c.getTelephone() + " : " + c.getEmail());
                else
                    pw.println(c.getNumero() + " : " + c.getNom() + " : " + c.getTelephone());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erreur fichier clients");
        }
    }

    public void versFichierRendezVous(String nomFichier) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nomFichier));

            for (int j = 0; j < NB_JOURS; j++) {
                for (int c = 0; c < NB_CRENEAUX; c++) {
                    RendezVous r = planning[c][j];
                    if (r == null) continue;

                    pw.println(r.getCreneau());
                    pw.println(r.getClient().getNumero());

                    Prestation p = r.getPrestation();
                    char cat = p.getCategorie();
                    int prix = (int) r.getPrix();

                    if (p instanceof PrestationExpress) {
                        pw.println(cat + " : " + ((PrestationExpress) p).isNettoyerInterieur() + " : " + prix + "Euros");
                    } else if (p instanceof PrestationTresSale) {
                        pw.println(cat + " : " + ((PrestationTresSale) p).getTypeSalissure() + " : " + prix + "Euros");
                    } else {
                        pw.println(cat + " : " + prix + "Euros");
                    }
                }
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erreur fichier rdv");
        }
    }
}
