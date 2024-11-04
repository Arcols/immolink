package classes;
import java.util.ArrayList;
import java.util.List;

public class Batiment extends BienImmobilier {

    public Batiment(String adresse, String code_postal, String ville, List<BienLouable> bien_louable) {
        this.adresse = adresse;
        this.code_postal = code_postal;
        this.ville = ville;
        this.bien_louable = bien_louable;
    }
    private String adresse;

    private String code_postal;

    private String ville;

    public List<BienLouable> bien_louable = new ArrayList<BienLouable> ();

}
