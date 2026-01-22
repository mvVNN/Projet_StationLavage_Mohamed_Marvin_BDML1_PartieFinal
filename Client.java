/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationlavage;

/**
 *
 * @author marvi
 */
public class Client {
    private int numero;
    private String nom;
    private String telephone;
    private String email;
    public Client(int numero, String nom, String telephone) {
        this(numero, nom, telephone, null);
    }
    // Constructeur client 
    public Client(int numero, String nom, String telephone, String email) {
        this.numero = numero;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
    }

    public int getNumero() { return numero; }
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }

    public boolean aEmail() { return email != null && !email.isBlank(); }

    
    // Information 
    @Override
    public String toString() {
        // information si il y a un mail
        if (aEmail()) {
            return numero + " : " + nom + " : " + telephone + " : " + email;
        }
        // Information si il n'y a pas de mail
        return numero + " : " + nom + " : " + telephone;
    }
    
    // placer apres les clients
    public boolean placerApres(Client autre) {
        int cmpNom = this.nom.compareToIgnoreCase(autre.nom);
        if (cmpNom > 0) return true;
        if (cmpNom < 0) return false;
        return this.telephone.compareTo(autre.telephone) > 0;
    }
}
