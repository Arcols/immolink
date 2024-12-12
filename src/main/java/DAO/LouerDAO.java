package DAO;

import classes.Bail;
import classes.Locataire;

import java.util.List;

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

    List<Integer> getIdLoc(int idBail);
}
