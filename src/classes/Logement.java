package classes;

import java.util.List;

public class Logement extends BienLouable {
    private int nb_piece;
    private float surface;
    public Garage garage;
    
    // Constructeur si il n'y a pas de garage présent
    public Logement(int nb_piece, float surface, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) throws IllegalArgumentException{
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        if (surface<9){
            throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
        }
        this.nb_piece = nb_piece;
        this.surface = surface;
        this.garage = null; 
    }

    // Constructeur si il y a un garage
    public Logement(int nb_piece, float surface, Garage garage, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) {
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        if (surface<9){
            throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
        }
        this.nb_piece = nb_piece;
        this.surface = surface;
        this.garage = garage;
    }

    /*
     * In : /
     * Out : boolean
     * Return true if Logement get a Garage
     */
    public boolean hasGarage(){
        return garage != null;
    }

    /*
     * In : /
     * Out : Garage or null
     * Return a garage or null if there is no garage
     */
    public Garage getGarage(){
        return this.garage;
    }

    public int getNbPiece(){
        return this.nb_piece;
    }

    public float getSurface(){
        return this.surface;
    }
}
