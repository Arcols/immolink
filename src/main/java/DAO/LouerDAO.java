package DAO;

import classes.Bail;
import classes.Locataire;

public interface LouerDAO {
    void create(Locataire locataire, Bail bail) throws DAOException;
}
