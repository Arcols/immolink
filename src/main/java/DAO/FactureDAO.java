package DAO;

import classes.Facture;

import java.sql.Date;
import java.util.List;

public interface FactureDAO {

    void create(Facture facture) throws DAOException;

    List<Facture> getAllByAnnee(Date annee, int id_charge) throws  DAOException;

}
