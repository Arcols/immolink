package classes;

import java.util.ArrayList;
import java.util.List;

public class BienLouable extends BienImmobilier {

    public BienLouable(String numero_fiscal, String complement_adresse, Batiment batiment, List<Devis> travaux, List<Diagnostic> diagnostic) {
        this.numero_fiscal = numero_fiscal;
        this.complement_adresse = complement_adresse;
        this.batiment = batiment;
        this.travaux = travaux;
        this.diagnostic = diagnostic;
    }
    private String numero_fiscal;

    private String complement_adresse;

    Batiment batiment;

    public List<Devis> travaux = new ArrayList<Devis> ();

    public List<Diagnostic> diagnostic = new ArrayList<Diagnostic> ();

}
