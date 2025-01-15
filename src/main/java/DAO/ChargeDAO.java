package DAO;

import java.sql.Date;

public interface ChargeDAO {
    void create(String type, int id_bail) throws DAOException;

    double getMontant(Date annee, int id) throws  DAOException;

    int getId(String type, int id_bail) throws DAOException;
}
