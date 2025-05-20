package classes;

import java.sql.Date;

public class Facture {
    private final String numero;
    private final String type;
    private final Date date;
    private final double montant;

    public Facture(String numero,String type, Date date, double montant) {
        this.numero = numero;
        this.type = type;
        this.date = date;
        this.montant = montant;
    }

    public String getNumero() {
        return numero;
    }

    public String getType() {
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }
}
