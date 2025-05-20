package DAO;

import classes.Locataire;

import java.util.List;

public interface LocataireDAO {

    /**
     * Ajoute un locataire dans la base de donnees
     *  @param locataire L'objet Locataire à inserer
     * @throws DAOException en cas d'erreur lors de la creation du locataire
     */
    void addLocataire(Locataire locataire);


    /**
     * Recupère un locataire de la base de donnees en utilisant son nom et prenom
     * @param nom Le nom du locataire
     * @param prenom Le prenom du locataire
     * @param telephone Le telephone du locataire
     * @return L'objet Locataire trouve, ou null si aucun locataire n'est trouve
     */
     Locataire getLocataireByNomPrenom(String nom, String prenom,String telephone);

        /**
         * Recupère tous les locataires de la base de donnees
         * @return Une liste de tous les objets Locataire
         * @throws DAOException en cas d'erreur lors de la lecture des locataires
         */
    List<Locataire> getAllLocataire();

    /**
     * Recupère l'identifiant d'un locataire dans la base de donnees
     * @param locataire L'objet locataire à rechercher
     * @return L'identifiant du locataire
     */
    int getId(Locataire locataire);

    /**
     * Recupère un locataire de la base de donnees en utilisant son identifiant
     * @param id L'identifiant
     * @return  L'objet Locataire trouve, ou null si aucun locataire n'est trouve
     */
    Locataire getLocFromId(int id);
}
