package DAO;

import classes.Facture;

import java.sql.Date;
import java.util.List;

public interface FactureDAO {

    /**
     * Crée une nouvelle facture dans la base de données.
     * @param facture L'objet facture à insérer
     * @param id_charge L'identifiant de la charge associée à la facture
     * @throws DAOException
     */
    void create(Facture facture,int id_charge) throws DAOException;

    /**
     * Récupère une facture de la base de données en utilisant son identifiant.
     * @param annee l'année de la facture
     * @param id_charge l'identifiant de la charge associée à la facture
     * @return L'objet facture trouvé, ou null si aucune facture n'est trouvée
     * @throws DAOException
     */
    List<Facture> getAllByAnnee(Date annee, int id_charge) throws  DAOException;

    /**
     * Récupère toutes les factures de la base de données.
     * @param id_charge l'identifiant de la charge associée à la facture
     * @return Une liste de tous les objets facture
     * @throws DAOException
     */
    List<Facture> getAll(int id_charge) throws  DAOException;

}
