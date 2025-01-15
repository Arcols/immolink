package classes;

import java.sql.Date;
import java.util.Objects;

public class Bail {
    private boolean solde_de_compte;

    private String fisc_bien;

    private double loyer;

    private double charge;

    private  double depot_garantie;

    private Date date_debut;

    private Date date_fin;

    private double icc;

    private Integer index_eau;

    private Date dernier_anniversaire;

    /**
     * Constructeur de la classe Bail
     * @param solde_de_compte le solde de compte
     * @param fisc_bien le numéro fiscal du bien
     * @param loyer le loyer
     * @param charge les charges
     * @param depot_garantie le dépôt de garantie
     * @param date_debut la date de début
     * @param date_fin la date de fin
     * @param icc l'indice de coût de la construction
     * @param index_eau l'index de l'eau
     * @param dernier_anniversaire la date du dernier anniversaire
     */
    public Bail(boolean solde_de_compte, String fisc_bien, double loyer, double charge, double depot_garantie, Date date_debut, Date date_fin, double icc, Integer index_eau, Date dernier_anniversaire) {
        this.solde_de_compte = solde_de_compte;
        this.fisc_bien = fisc_bien;
        this.loyer = loyer;
        this.charge = charge;
        this.depot_garantie = depot_garantie;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.icc = icc;
        this.index_eau = index_eau;
        this.dernier_anniversaire = dernier_anniversaire;
    }

    public boolean isSoldeDeCompte() {
        return solde_de_compte;
    }

    public Date getDateDebut() {
        return date_debut;
    }

    public Date getDateFin() {
        return date_fin;
    }

    public double getCharge() {
        return charge;
    }

    public double getDepotGarantie() {
        return depot_garantie;
    }

    public double getLoyer() {
        return loyer;
    }

    public String getFiscBien() {
        return fisc_bien;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setDateDebut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDateFin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setDepotGarantie(double depot_garantie) {
        this.depot_garantie = depot_garantie;
    }

    public void setFiscBien(String fisc_bien) {
        this.fisc_bien = fisc_bien;
    }

    public void setLoyer(double loyer) {
        this.loyer = loyer;
    }

    public void setSoldeDeCompte(boolean solde_de_compte) {
        this.solde_de_compte = solde_de_compte;
    }

    public double getIcc() {
        return icc;
    }

    public Integer getIndexEau() {
        return index_eau;
    }

    public Date getDernierAnniversaire() {
        return dernier_anniversaire;
    }

    /**
     * Méthode permettant de comparer deux objets de type Bail
     * @param o l'objet à comparer
     * @return true si les deux objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bail bail = (Bail) o;
        return solde_de_compte == bail.solde_de_compte &&
                Double.compare(bail.loyer, loyer) == 0 &&
                Double.compare(bail.charge, charge) == 0 &&
                Double.compare(bail.depot_garantie, depot_garantie) == 0 &&
                Objects.equals(fisc_bien, bail.fisc_bien) &&
                Objects.equals(date_debut, bail.date_debut) &&
                Objects.equals(date_fin, bail.date_fin);
    }

    /**
     * Méthode permettant de générer un code de hachage pour un objet de type Bail
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(solde_de_compte, fisc_bien, loyer, charge, depot_garantie, date_debut, date_fin);
    }
}
