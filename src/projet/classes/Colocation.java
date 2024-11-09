package projet.classes;

import java.util.ArrayList;
import java.util.List;

public class Colocation {

    public Colocation(int quotite, List<Locataire> locataire) {
        this.quotite = quotite;
        this.locataire = locataire;
    }
    private int quotite;

    public List<Locataire> locataire = new ArrayList<Locataire> ();

}
