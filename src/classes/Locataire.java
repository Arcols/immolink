package classes;

import java.util.ArrayList;
import java.util.List;

public class Locataire {

    private String nom;

    private String prénom;

    private String téléphone;

    private String genre;

    public List<Bail> bails = new ArrayList<Bail> ();

    public List<Charge> charges = new ArrayList<Charge> ();

    public Locataire(String nom, String prénom, String téléphone, String genre, List<Bail> bails, List<Charge> charges) {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.genre = genre;
        this.bails = bails;
        this.charges = charges;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrénom() {
        return this.prénom;
    }

    public String getTéléphone() {
        return this.téléphone;
    }

    public String getGenre() {
        return this.genre;
    }

    public List<Bail> getBails() {
        return this.bails;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    
    
}
