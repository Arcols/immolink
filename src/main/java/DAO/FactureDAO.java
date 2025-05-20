package DAO;

import classes.Facture;

import java.sql.Date;
import java.util.List;

public interface FactureDAO {

    /**
     * Cree une nouvelle facture dans la base de donnees.
     * @param facture L'objet facture à inserer
     * @param id_charge L'identifiant de la charge associee à la facture
     * @throws DAOException
     */
    void create(Facture facture,int id_charge) throws DAOException;

    /**
     * Recupère une facture de la base de donnees en utilisant son identifiant.
     * @param annee l'annee de la facture
     * @param id_charge l'identifiant de la charge associee à la facture
     * @return L'objet facture trouve, ou null si aucune facture n'est trouvee
     * @throws DAOException
     */
    List<Facture> getAllByAnnee(Date annee, int id_charge) throws  DAOException;

    /**
     * Recupère toutes les factures de la base de donnees.
     * @param id_charge l'identifiant de la charge associee à la facture
     * @return Une liste de tous les objets facture
     * @throws DAOException
     */
    List<Facture> getAll(int id_charge) throws  DAOException;

    /**
     * Supprime une facture de la base de donnees en utilisant son identifiant.
     * @param id_facture L'identifiant unique de la facture à supprimer
     * @throws DAOException en cas d'erreur lors de la suppression de la facture
     */
    void delete(Integer id_facture) throws DAOException;

    /**
     * Recupère tous les identifiants des factures de la base de donnees d'une charge donnee en paramètre
     * @param id_charge l'identifiant de la charge associee à la facture
     * @return Une liste de tous les identifiants des factures
     * @throws DAOException
     */
    List<Integer> getAllId(Integer id_charge) throws DAOException;
}
