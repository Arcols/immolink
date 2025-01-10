package classes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Locataire {

    private String nom;
    private String prénom;
    private String lieuNaissance;
    private String dateNaissance;
    private String téléphone;
    private String genre;
    private String mail;
    private Date date_arrive;
    public List<Bail> beaux = new ArrayList<>();
    public List<Charge> charges;

    /**
     * Constructeur de la classe Locataire
     * @param nom le nom
     * @param prénom le prénom
     * @param lieuNaissance le lieu de naissance
     * @param dateNaissance la date de naissance
     * @param téléphone le téléphone
     * @param mail le mail
     * @param date_arrive la date d'arrivée
     * @param genre le genre
     */
    public Locataire(String nom, String prénom, String lieuNaissance, String dateNaissance, String téléphone, String mail, Date date_arrive, String genre) {
        this.setNom(nom);
        this.setPrénom(prénom);
        this.setLieuNaissance(lieuNaissance);
        this.setDateNaissance(dateNaissance);
        this.setTéléphone(téléphone);
        this.setMail(mail);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.charges = new ArrayList<>();
        this.beaux= new ArrayList<>();
    }

    /**
     * Constructeur de la classe Locataire
     * @param nom le nom
     * @param prénom le prénom
     * @param lieuNaissance le lieu de naissance
     * @param dateNaissance la date de naissance
     * @param téléphone le téléphone
     * @param date_arrive la date d'arrivée
     * @param genre le genre
     */
    public Locataire(String nom, String prénom, String lieuNaissance, String dateNaissance, String téléphone, Date date_arrive, String genre) {
        this.setNom(nom);
        this.setPrénom(prénom);
        this.setLieuNaissance(lieuNaissance);
        this.setDateNaissance(dateNaissance);
        this.setTéléphone(téléphone);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.mail = null;
        this.charges = new ArrayList<>();
        this.beaux= new ArrayList<>();
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

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public List<Bail> getBeaux() {
        return this.beaux;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    /**
     * Ajoute une charge à la liste des charges
     * @param charge la charge à ajouter
     */
    public void addCharge(Charge charge) {
        this.charges.add(charge);
    }

    /**
     * Ajoute un bail à la liste des baux
     * @param bail le bail à ajouter
     */
    public void addBail(Bail bail) {
        this.beaux.add(bail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Locataire locataire = (Locataire) obj;
        return nom.equals(locataire.nom) &&
                prénom.equals(locataire.prénom) &&
                lieuNaissance.equals(locataire.lieuNaissance) &&
                dateNaissance.equals(locataire.dateNaissance) &&
                téléphone.equals(locataire.téléphone) &&
                genre.equals(locataire.genre) &&
                (mail != null ? mail.equals(locataire.mail) : locataire.mail == null) &&
                date_arrive.equals(locataire.date_arrive);
    }
}