package DAO;

import classes.Charge;
import classes.Facture;

import java.sql.Date;
import java.util.List;

public interface ChargeDAO {
    void create(String type, int id_bail) throws DAOException;

    double getMontant(Date annee, int id) throws  DAOException;
}
