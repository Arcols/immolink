package classes;

public class Facture {

    public Facture(String numero, String date, float montant, Devis devis) {
        this.numero = numero;
        this.date = date;
        this.montant = montant;
        this.devis = devis;
    }

    private String numero;

    private String date;

    private float montant;

    public Devis devis;

}
