package DAO;

import java.util.List;
import java.util.Map;

import classes.Bail;
import classes.Locataire;

public interface LouerDAO {

    /**
     * Crée une nouvelle location dans la base de données.
     * 
     * @param locataire L'objet locataire à insérer
     * @param bail      L'objet bail à insérer
     * @param quotite   La quotité de la location
     * @throws DAOException en cas d'erreur lors de la création de la location
     */
    void create(Locataire locataire, Bail bail, int quotite) throws DAOException;

    /**
     * Récupère la liste des id d'un locataire d'un bail dans la base de données.
     * @param idBail l'id du bail
     * @return l'id des locations
     */
    List<Integer> getIdLoc(int idBail);

    /**
     * Récupère la quotité d'un locataire dans un bail.
     * @param idBail
     * @param idLocataire
     * @return la quotité de la location
     */
    Integer getQutotié(int idBail, int idLocataire);

    /**
     * Supprime une location dans la base de données.
     * @param idBail
     * @param idLocataire
     */
    void delete(int idBail, int idLocataire);

    /**
     * Récupère la quotité d'une location dans la base de données.
     * @param idBail L'identifiant du bail à récupérer
     * @param idLocataire L'identifiant du locataire à récupérer
     * @param quotite La quotité de la location
     */
    void updateQuotite(int idBail,int idLocataire, int quotite);

    /**
     * Récupère une map IdBail : liste des locataires de tous les beaux dans la base de données.
     * @return la liste des locataires pour chaque bail (Map<IdBail, List<IdLocataire>>)
     */
    Map<Integer,List<Integer>> getAllLocatairesDesBeaux();

}
