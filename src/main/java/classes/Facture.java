package classes;

import java.sql.Date;

public class Facture {
    private String numero;
    private String type;
    private Date date;
    private double montant;

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
