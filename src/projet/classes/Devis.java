package projet.classes;

public class Devis {

    public Devis(String num_devis, String montant, String nature, float montant_nondeductible) {
        this.num_devis = num_devis;
        this.montant = montant;
        this.nature = nature;
        this.montant_nondeductible = montant_nondeductible;
    }
    private String num_devis;

    private String montant;

    private String nature;

    private float montant_nondeductible;

}
