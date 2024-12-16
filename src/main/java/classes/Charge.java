package classes;

import java.util.List;

public class Charge {
    public List<Facture> facture;

    /**
     * Constructeur de la classe Charge
     * @param facture la liste des factures
     */
    public Charge(List<Facture> facture) {
        this.facture = facture;
    }

}
