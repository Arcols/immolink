package classes;

import java.sql.Date;

public class Bail {
    private boolean solde_de_compte;

    private String fisc_bien;

    private double loyer;

    private double charge;

    private  double depot_garantie;

    private Date date_debut;

    private Date date_fin;

    public Bail(boolean solde_de_compte, String fisc_bien, double loyer, double charge, double depot_garantie, Date date_debut, Date date_fin) {
        this.solde_de_compte = solde_de_compte;
        this.fisc_bien = fisc_bien;
        this.loyer = loyer;
        this.charge = charge;
        this.depot_garantie = depot_garantie;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public boolean isSolde_de_compte() {
        return solde_de_compte;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public double getCharge() {
        return charge;
    }

    public double getDepot_garantie() {
        return depot_garantie;
    }

    public double getLoyer() {
        return loyer;
    }

    public String getFisc_bien() {
        return fisc_bien;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setDepot_garantie(double depot_garantie) {
        this.depot_garantie = depot_garantie;
    }

    public void setFisc_bien(String fisc_bien) {
        this.fisc_bien = fisc_bien;
    }

    public void setLoyer(double loyer) {
        this.loyer = loyer;
    }

    public void setSolde_de_compte(boolean solde_de_compte) {
        this.solde_de_compte = solde_de_compte;
    }
}
