package DAO;

import java.sql.Date;

public interface ChargeDAO {

    /**
     * Crée une nouvelle charge dans la base de données.
     * @param type le type de la charge
     * @param id_bail l'identifiant du bail associé à la charge
     * @throws DAOException en cas d'erreur lors de la création de la charge
     */
    void create(String type, int id_bail) throws DAOException;

    /**
     * Récupère le montant d'une charge dans la base de données.
     * @param annee l'année de la charge
     * @param id l'identifiant de la charge
     * @return le montant de la charge
     * @throws DAOException
     */
    double getMontant(Date annee, int id) throws  DAOException;

    /**
     * Récupère l'identifiant d'une charge dans la base de données.
     * @param type le type de la charge
     * @param id_bail l'identifiant du bail associé à la charge
     * @return l'identifiant de la charge
     * @throws DAOException
     */
    int getId(String type, int id_bail) throws DAOException;
}
