package classes;

import java.util.List;

public class Propriétaire {
    private String nom;

    private String prenom;


    public List<BienImmobilier> bien_immobilier;

    /**
     * Constructeur de la classe Propriétaire
     * @param nom le nom
     * @param prenom le prénom
     * @param bien_immobilier la liste des biens immobiliers
     */
    public Propriétaire(String nom, String prenom, List<BienImmobilier> bien_immobilier) {
        this.nom = nom;
        this.prenom = prenom;
        this.bien_immobilier = bien_immobilier;
    }

}
