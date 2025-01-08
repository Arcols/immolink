package classes;

import java.sql.Date;
import java.util.List;

public class Charge {
    private String type;

    private List<Facture> facture;

    /**
     * Constructeur de la classe Charge
     * @param facture la liste des factures
     */
    public Charge(List<Facture> facture) {
        this.facture = facture;
    }

    public String getType() {
        return type;
    }

    public List<Facture> getFacture() {
        return facture;
    }
}
