package classes;

import java.sql.Date;
import java.util.List;

public class Charge {
    private String type;
    private Date annee;
    private List<Facture> facture;

    public Charge(List<Facture> facture) {
        this.facture = facture;
    }

    public String getType() {
        return type;
    }

    public Date getAnnee() {
        return annee;
    }

    public List<Facture> getFacture() {
        return facture;
    }
}
