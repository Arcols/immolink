package DAO;

import classes.Diagnostic;

import java.sql.Date;
import java.util.List;

public interface DiagnosticDAO {

    /**
     * Creer un diagnostic dans la bdd
     * @param diagnostic , numero_fiscal du batiment associe
     * @throws DAOException
     */
    void create(Diagnostic diagnostic,String numero_fiscal) throws DAOException;

    /**
     * Trouver un diagnostic dans la bdd
     * @param numero_fiscal du bien louable associe
     * @param reference du diagnostic
     * @return Diagnostic object
     * @throws DAOException
     */
    Diagnostic read(String numero_fiscal,String reference) throws DAOException;

   /**
     * Mettre à jour la date d'expiration d'un diagnostic dans la bdd
     * @param diagnostic , numero_fiscal du bien louable associe
     * @param numero_fiscal du bien louable associe
     * @param date date d'expiration
     * @throws DAOException
     */
    void updateDate(Diagnostic diagnostic, String numero_fiscal, Date date) throws DAOException;

    /**
     * Supprimer un diagnostic dans la bdd
     * @param numero_fiscal du bien louable associe
     * @param reference du diagnostic
     * @throws DAOException
     */
    void delete(String numero_fiscal,String reference) throws DAOException;

    /**
     * Trouver tous les diagnostics d'un bien louable dans la bdd
     * @param id du bien louable associe
     * @return List of Diagnostic objects
     * @throws DAOException
     */
    List<Diagnostic> readAllDiag(int id) throws DAOException;

    /**
     * Renvoie la liste des diagnostics perimes
     * @return List of Diagnostic objects
     * @throws DAOException
     */
    List<String> readDiagPerimes() throws DAOException;
}
