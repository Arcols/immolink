package classes;

import java.util.ArrayList;
import java.util.List;

public class Charge {

    public Charge(List<Facture> facture) {
        this.facture = facture;
    }
    public List<Facture> facture = new ArrayList<Facture> ();

}
