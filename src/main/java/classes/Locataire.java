package classes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Locataire {

    private String nom;
    private String prenom;
    private String lieu_naissance;
    private String date_naissance;
    private String telephone;
    private String genre;
    private String mail;
    private Date date_arrive;
    public List<Bail> baux = new ArrayList<>();

    /**
     * Constructeur de la classe Locataire
     * @param nom le nom
     * @param prenom le prenom
     * @param lieu_naissance le lieu de naissance
     * @param date_naissance la date de naissance
     * @param telephone le telephone
     * @param mail le mail
     * @param date_arrive la date d'arrivee
     * @param genre le genre
     */
    public Locataire(String nom, String prenom, String lieu_naissance, String date_naissance, String telephone, String mail, Date date_arrive, String genre) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setLieuNaissance(lieu_naissance);
        this.setDateNaissance(date_naissance);
        this.setTelephone(telephone);
        this.setMail(mail);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.baux= new ArrayList<>();
    }

    /**
     * Constructeur de la classe Locataire
     * @param nom le nom
     * @param prenom le prenom
     * @param lieu_naissance le lieu de naissance
     * @param date_naissance la date de naissance
     * @param telephone le telephone
     * @param date_arrive la date d'arrivee
     * @param genre le genre
     */
    public Locataire(String nom, String prenom, String lieu_naissance, String date_naissance, String telephone, Date date_arrive, String genre) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setLieuNaissance(lieu_naissance);
        this.setDateNaissance(date_naissance);
        this.setTelephone(telephone);
        this.setDateArrive(date_arrive);
        this.setGenre(genre);
        this.mail = null;
        this.baux= new ArrayList<>();
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLieuNaissance() {
        return this.lieu_naissance;
    }

    public void setLieuNaissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public String getDateNaissance() {
        return this.date_naissance;
    }

    public void setDateNaissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public List<Bail> getbaux() {
        return this.baux;
    }
    
    /**
     * Ajoute un bail à la liste des baux
     * @param bail le bail à ajouter
     */
    public void addBail(Bail bail) {
        this.baux.add(bail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Locataire locataire = (Locataire) obj;
        return nom.equals(locataire.nom) &&
                prenom.equals(locataire.prenom) &&
                lieu_naissance.equals(locataire.lieu_naissance) &&
                date_naissance.equals(locataire.date_naissance) &&
                telephone.equals(locataire.telephone) &&
                genre.equals(locataire.genre) &&
                (Objects.equals(mail, locataire.mail)) &&
                date_arrive.equals(locataire.date_arrive);
    }
}