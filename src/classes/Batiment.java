package classes;
import java.util.ArrayList;
import java.util.List;

public class Batiment extends BienImmobilier {
    private String adresse;

    private String code_postal;

    private String ville;

    public List<BienLouable> bien_louable;

    // Un batiment est initialisé sans bien louable
    public Batiment(String adresse, String code_postal, String ville) {
        this.adresse = adresse;
        this.code_postal = code_postal;
        this.ville = ville;
        this.bien_louable = new ArrayList<BienLouable> ();
    }

    public String getAdresse() {
        return this.adresse;
    }
    
    public String getCode_postal() {
        return this.code_postal;
    }

    public String getVille() {
        return this.ville;
    }

    public List<BienLouable> getBien_louable() {
        return this.bien_louable;
    }

    public void ajouterBienLouable(BienLouable bien_louable){
        this.bien_louable.add(bien_louable);
    }
    

}
