package classes;

import java.util.ArrayList;
import java.util.List;

public class Locataire {

    public Locataire(String nom, String prénom, String téléphone, String genre, List<Bail> bail, List<Charge> charges) {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.genre = genre;
        this.bail = bail;
        this.charges = charges;
    }
    private String nom;

    private String prénom;

    private String téléphone;

    private String genre;

    public List<Bail> bail = new ArrayList<Bail> ();

    public List<Charge> charges = new ArrayList<Charge> ();

}
