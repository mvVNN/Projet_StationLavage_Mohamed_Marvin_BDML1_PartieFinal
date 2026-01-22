/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
// import pour la date d'aujourd 'hui
import java.time.LocalDateTime;

public class RendezVous {
    private Client client;
    private Prestation prestation;
    private double prix;
    private LocalDateTime creneau;

    // Constructeur du rendez-vous
    public RendezVous(Client client, Prestation prestation, LocalDateTime creneau) {
        this.client = client;
        this.prestation = prestation;
        this.creneau = creneau;
        this.prix = prestation.nettoyage();
    }

    public Client getClient() { return client; }
    public Prestation getPrestation() { return prestation; }
    public double getPrix() { return prix; }
    public LocalDateTime getCreneau() { return creneau; }

    @Override
    public String toString() {
        return "RDV(" + creneau + ") - " + client + " - " + prestation + " - prix=" + prix + "€";
    }
}
