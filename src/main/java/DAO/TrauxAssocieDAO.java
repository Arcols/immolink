package DAO;

import classes.Devis;
import enumeration.TypeLogement;
import java.util.List;

public interface TrauxAssocieDAO {

    /**
     * Associe un devis à un Bien
     * @param num_fiscal
     * @param devis
     * @throws DAOException
     */
    public void create(String num_fiscal, Devis devis, TypeLogement typeLogement) throws DAOException;

    /**
     * Récupère tous les id des devis associés à un bien
     * @param num_fiscal
     * @param typeLogement
     * @return
     * @throws DAOException
     */
    List<Integer> findAll(String num_fiscal, TypeLogement typeLogement) throws DAOException;

}
