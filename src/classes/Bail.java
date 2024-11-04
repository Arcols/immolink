package classes;
public class Bail {

    public Bail(boolean solde_de_compte, BienLouable bien_louable, Colocation colocation) {
        this.solde_de_compte = solde_de_compte;
        this.bien_louable = bien_louable;
        this.colocation = colocation;
    }
    
    private boolean solde_de_compte;

    public BienLouable bien_louable;

    public Colocation colocation;

}
