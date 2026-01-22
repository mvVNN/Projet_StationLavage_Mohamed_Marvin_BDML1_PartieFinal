/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
public class PrestationSale extends Prestation {
    public PrestationSale(char categorie) {
        super(categorie);
    }
    @Override
    public double nettoyage() {
        return prelavage() + lavage() + sechage() + interieur();
    }

    @Override
    public String toString() {
        return "Sale(cat=" + categorie + ")";
    }
}