package DAO;

import java.sql.Date;
import java.util.List;

public interface ChargeDAO {

    /**
     * Cree une nouvelle charge dans la base de donnees.
     * @param type le type de la charge
     * @param id_bail l'identifiant du bail associe à la charge
     * @throws DAOException en cas d'erreur lors de la creation de la charge
     */
    void create(String type, int id_bail) throws DAOException;

    /**
     * Recupère le montant d'une charge dans la base de donnees.
     * @param annee l'annee de la charge
     * @param id l'identifiant de la charge
     * @return le montant de la charge
     * @throws DAOException
     */
    double getMontant(Date annee, int id) throws  DAOException;

    /**
     * Recupère l'identifiant d'une charge dans la base de donnees.
     * @param type le type de la charge
     * @param id_bail l'identifiant du bail associe à la charge
     * @return l'identifiant de la charge
     * @throws DAOException
     */
    int getId(String type, int id_bail) throws DAOException;

    /**
     * Supprime une charge de la base de donnees en utilisant son identifiant.
     * @param id_charge l'identifiant de la charge à supprimer
     * @throws DAOException
     */
    void delete(Integer id_charge) throws DAOException;

    /**
     * Recupère la liste des identifiants des charges associees à un bail
     * @param id_bail l'identifiant du bail
     * @return la liste des identifiants des charges associees à un bail
     * @throws DAOException
     */
    List<Integer> getAllId(Integer id_bail) throws DAOException;
}
