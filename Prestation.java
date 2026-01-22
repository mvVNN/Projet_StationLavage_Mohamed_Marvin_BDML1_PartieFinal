/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
public abstract class Prestation {
    //catégorie du véhicule
    protected char categorie;

    public Prestation(char categorie) {
        this.categorie = Character.toUpperCase(categorie);
    }

    // Majoration pour le lavage ou pré lavage
    public char getCategorie() { return categorie; }
    protected double majorationLavageOuPrelavage() {
        if (categorie == 'B') return 1.50; 
        if (categorie == 'C') return 1.75; 
        return 1.00;
    }
    
    // Majoration pour le Séchage
    protected double majorationSechage() {
        if (categorie == 'B') return 1.05; 
        if (categorie == 'C') return 1.10; 
        return 1.00;
    }
    
    // Prix de base lavage
    public double lavage() {
        double base = 20.0;
        return base * majorationLavageOuPrelavage();
    }
    
    // Prix de base séchage
    public double sechage() {
        double base = 10.0; 
        return base * majorationSechage();
    }
    
    // Prix de base prélavege
    public double prelavage() {
        double base = 5.0; 
        return base * majorationLavageOuPrelavage();
    }
    
    // PRix de l'intérieur 
    public double interieur() {
        return (categorie == 'C') ? 40.0 : 30.0;
    }
    public abstract double nettoyage();

    @Override
    public String toString() {
        return "Prestation(cat=" + categorie + ")";
    }
}
