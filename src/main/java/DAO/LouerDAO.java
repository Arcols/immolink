package DAO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import classes.Bail;
import classes.Locataire;

public interface LouerDAO {

    /**
     * Cree une nouvelle location dans la base de donnees.
     * 
     * @param locataire L'objet locataire à inserer
     * @param bail      L'objet bail à inserer
     * @param quotite   La quotite de la location
     * @throws DAOException en cas d'erreur lors de la creation de la location
     */
    void create(Locataire locataire, Bail bail, int quotite) throws DAOException;

    /**
     * Vrai si le locataire est dejà dans le bail.
     * @param idloc
     * @param idBail
     * @return Boolean
     */
    boolean locInBail(int idloc, int idBail);

    /**
     * Recupère la liste des id d'un locataire d'un bail dans la base de donnees.
     * @param idBail l'id du bail
     * @return l'id des locations
     */
    List<Integer> getIdLoc(int idBail);

    /**
     * Recupère la quotite d'un locataire dans un bail.
     * @param idBail
     * @param idLocataire
     * @return la quotite de la location
     */
    Integer getQuotite(int idBail, int idLocataire);

    /**
     * Supprime une location dans la base de donnees.
     * @param idBail
     * @param idLocataire
     */
    void delete(int idBail, int idLocataire);

    /**
     * Recupère la quotite d'une location dans la base de donnees.
     * @param idBail L'identifiant du bail à recuperer
     * @param idLocataire L'identifiant du locataire à recuperer
     * @param quotite La quotite de la location
     */
    void updateQuotite(int idBail,int idLocataire, int quotite);

    /**
     * Recupère une map IdBail : liste des locataires de tous les beaux dans la base de donnees.
     * @return la liste des locataires pour chaque bail (Map<IdBail, List<IdLocataire>>)
     */
    Map<Integer,List<Integer>> getAllLocatairesDesBeaux();

    /**
     * Renvoie True si le locataire a paye tous ses loyers
     * @param idLocataire
     * @return true ou false
     */
    Boolean getStatut(int idLocataire);

    /**
     * Renvoie True si tous les locataires ont paye le loyer
     * @param idBail
     * @return true ou false
     */
    Boolean getStatutBail(int idBail);

    /**
     * Renvoie la date du dernier paiement
     * @param idLocataire
     * @param idBail
     * @return Date
     */
    Boolean getLoyerPaye(int idLocataire, int idBail);

    /**
     * Mets a jour la date du dernier paiement
     * @param idBail
     * @param idLocataire
     * @param date
     */
    void updatePaiement(int idBail, int idLocataire, Date date);
}
