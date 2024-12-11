package DAO;

import classes.Devis;

public interface TrauxAssocieDAO {

    /**
     * Associe un devis à un Bien
     * @param num_fiscal
     * @param devis
     * @throws DAOException
     */
    public void create(String num_fiscal, Devis devis) throws DAOException;

}
