package classes;

import java.util.List;

public class Logement extends BienLouable {
    private int nb_piece;
    private float Surface;
    public Garage garage;
    
    // Constructeur si il n'y a pas de garage présent
    public Logement(int nb_piece, float Surface, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) {
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        this.nb_piece = nb_piece;
        this.Surface = Surface;
        this.garage = null; // garage est nul par défaut
    }

    // Constructeur si il y a un garage
    public Logement(int nb_piece, float Surface, Garage garage, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) {
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        this.nb_piece = nb_piece;
        this.Surface = Surface;
        this.garage = garage;
    }

    
}
