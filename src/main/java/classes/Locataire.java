package classes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Locataire {

    private int idBDD;
    private String nom;
    private String prénom;
    private String téléphone;
    private String genre;
    private String mail;
    private Date date_arrive;
    public List<Bail> beaux = new ArrayList<>();
    public List<Charge> charges;

    public Locataire(String nom, String prénom, String téléphone, String mail, Date date_arrive, String genre, int idBDD) {
        this.setNom(nom);
        this.setPrénom(prénom);
        this.setTéléphone(téléphone);
        this.setMail(mail);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.setId(idBDD);
        this.charges = new ArrayList<>();
        //this.beaux= new ArrayList<>();
    }

    public Locataire(String nom, String prénom, String téléphone, Date date_arrive, String genre, int idBDD) {
        this.setNom(nom);
        this.setPrénom(prénom);
        this.setTéléphone(téléphone);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.setId(idBDD);
        this.mail = null;
        this.charges = new ArrayList<>();
        //this.beaux= new ArrayList<>();
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrénom() {
        return this.prénom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public String getTéléphone() {
        return this.téléphone;
    }

    public void setTéléphone(String téléphone) {
        this.téléphone = téléphone;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateArrive() {
        return this.date_arrive;
    }

    public void setDateArrive(Date date_arrive) {
        this.date_arrive = date_arrive;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return this.idBDD;
    }

    public void setId(int idBDD) {
        this.idBDD = idBDD;
    }

    public List<Bail> getBeaux() {
        return this.beaux;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    public void addCharge(Charge charge) {
        this.charges.add(charge);
    }

    public void addBail(Bail bail) {
        this.beaux.add(bail);
    }

    @Override
    public String toString() {
        return "Locataire{" +
                "idBDD=" + idBDD +
                ", nom='" + nom + '\'' +
                ", prénom='" + prénom + '\'' +
                ", téléphone='" + téléphone + '\'' +
                ", genre='" + genre + '\'' +
                ", mail='" + mail + '\'' +
                ", date_arrive=" + date_arrive +
                ", beaux=" + beaux +
                ", charges=" + charges +
                '}';
    }
}