package DAO;

import classes.Devis;
import classes.Facture;
import enumeration.TypeLogement;

import java.sql.Date;
import java.util.List;

public interface FactureDAO {

    void create(Facture facture, int id_charge) throws DAOException;

    List<Facture> getAllAnnee(Date annee) throws  DAOException;

}
