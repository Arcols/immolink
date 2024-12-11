package DAO;

import  classes.Devis;

public interface DevisDAO {

    /**
     * Crée un nouveau devis dans la base de données.
     *
     * @param devis L'objet devis à insérer
     * @throws DAOException en cas d'erreur lors de la création du devis
     */
    void create(Devis devis,String num_fiscal) throws DAOException;

    /**
     * Trouve un devis dans la base de données.
     *
     * @param reference La référence du devis à trouver
     * @return L'objet devis trouvé
     * @throws DAOException en cas d'erreur lors de la recherche du devis
     */
    Devis read(String reference) throws DAOException;

    /**
     * Récupère l'identifiant d'un devis dans la base de données.
     * @param devis L'objet devis à rechercher
     * @return L'identifiant du devis
     */
    Integer getId(Devis devis);

}
