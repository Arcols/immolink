package DAO;

import classes.Diagnostic;

import java.sql.Date;
import java.util.List;

public interface DiagnosticDAO {

    /**
     * Créer un diagnostic dans la bdd
     * @param diagnostic , numero_fiscal du batiment associé
     * @throws DAOException
     */
    void create(Diagnostic diagnostic,String numero_fiscal) throws DAOException;

    /**
     * Trouver un diagnostic dans la bdd
     * @param numero_fiscal du bien louable associé
     * @param reference du diagnostic
     * @return Diagnostic object
     * @throws DAOException
     */
    Diagnostic read(String numero_fiscal,String reference) throws DAOException;

    /**
     * Mettre à jour le path d'un diagnostic dans la bdd
     * @param diagnostic , numero_fiscal du bien louable associé
     * @param numero_fiscal du bien louable associé
     * @param path path du diagnostic
     * @throws DAOException
     */
    void updatePath(Diagnostic diagnostic,String numero_fiscal,String path) throws DAOException;

    /**
     * Mettre à jour la date d'expiration d'un diagnostic dans la bdd
     * @param diagnostic , numero_fiscal du bien louable associé
     * @param numero_fiscal du bien louable associé
     * @param date date d'expiration
     * @throws DAOException
     */
    void updateDate(Diagnostic diagnostic, String numero_fiscal, Date date) throws DAOException;

    /**
     * Supprimer un diagnostic dans la bdd
     * @param numero_fiscal du bien louable associé
     * @param reference du diagnostic
     * @throws DAOException
     */
    void delete(String numero_fiscal,String reference) throws DAOException;

    /**
     * Trouver tous les diagnostics d'un bien louable dans la bdd
     * @param id du bien louable associé
     * @return List of Diagnostic objects
     * @throws DAOException
     */
    List<Diagnostic> readAllDiag(int id) throws DAOException;

    /**
     * Renvoie la liste des diagnostics périmés
     * @return List of Diagnostic objects
     * @throws DAOException
     */
    List<String> readDiagPerimes() throws DAOException;
}
