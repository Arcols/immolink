package projet.classes;

import java.util.ArrayList;
import java.util.List;

public class Propriétaire {

    public Propriétaire(String nom, String prenom, List<BienImmobilier> bien_immobilier) {
        this.nom = nom;
        this.prenom = prenom;
        this.bien_immobilier = bien_immobilier;
    }
    private String nom;

    private String prenom;

    public List<BienImmobilier> bien_immobilier = new ArrayList<BienImmobilier> ();

}
