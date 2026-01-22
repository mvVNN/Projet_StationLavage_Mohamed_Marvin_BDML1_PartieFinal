/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
public class PrestationTresSale extends Prestation {
    private int typeSalissure;

    public PrestationTresSale(char categorie, int typeSalissure) {
        super(categorie);
        this.typeSalissure = typeSalissure;
    }

    public int getTypeSalissure() { return typeSalissure; }

    private double surcoutProduit() {
       
        switch (typeSalissure) {
            case 1: return 5.0;
            case 2: return 4.0; 
            case 3: return 3.0; 
            case 4: return 6.0;  
            default: return 4.0;
        }
    }
    @Override
    public double lavage() {
        return super.lavage() + surcoutProduit();
    }
    @Override
    public double prelavage() {
        return super.prelavage() + surcoutProduit();
    }
    @Override
    public double nettoyage() {
        return prelavage() + lavage() + sechage() + interieur();
    }
    @Override
    public String toString() {
        return "TresSale(cat=" + categorie + ", type=" + typeSalissure + ")";
    }
}
