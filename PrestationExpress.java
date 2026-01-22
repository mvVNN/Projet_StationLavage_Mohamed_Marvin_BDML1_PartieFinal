/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
public class PrestationExpress extends Prestation {
    private boolean nettoyerInterieur;

    public PrestationExpress(char categorie, boolean nettoyerInterieur) {
        super(categorie);
        this.nettoyerInterieur = nettoyerInterieur;
    }

    public boolean isNettoyerInterieur() { return nettoyerInterieur; }

    @Override
    public double nettoyage() {
        double total = lavage() + sechage();
        if (nettoyerInterieur) total += interieur();
        return total;
    }

    @Override
    public String toString() {
        return "Express(cat=" + categorie + ", interieur=" + nettoyerInterieur + ")";
    }
}
