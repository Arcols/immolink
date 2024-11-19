package projet.classes;

import java.util.List;

public class Colocation {
    private int quotite;

    public List<Locataire> locataire ;
    public Colocation(int quotite, List<Locataire> locataire) {
        this.quotite = quotite;
        this.locataire = locataire;
    }
    

}
