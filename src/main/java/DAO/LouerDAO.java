package DAO;

import classes.Bail;
import classes.Locataire;

import java.util.List;

public interface LouerDAO {
    void create(Locataire locataire, Bail bail) throws DAOException;

    List<Integer> getIdLoc(int idBail);
}
