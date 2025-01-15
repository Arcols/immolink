package DAO;

import classes.Locataire;

import java.util.List;

public interface LocataireDAO {

    /**
     * Ajoute un locataire dans la base de données
     *  @param locataire L'objet Locataire à insérer
     * @throws DAOException en cas d'erreur lors de la création du locataire
     */
    void addLocataire(Locataire locataire);

    /**
     * Met à jour les informations du téléphone d'un locataire existant dans la base de données
     * @param locataire L'objet Locataire avec les informations mises à jour
     * @param tel Le nouveau numéro de téléphone du locataire
     * @throws DAOException en cas d'erreur lors de la mise à jour du locataire
     */
    void updateLocataireTel(Locataire locataire, String tel);

    /**
     * Met à jour les informations du mail d'un locataire existant dans la base de données
     * @param locataire L'objet Locataire avec les informations mises à jour
     * @param mail Le nouveau mail du locataire
     * @throws DAOException en cas d'erreur lors de la mise à jour du locataire
     *
     */
    void updateLocataireMail(Locataire locataire, String mail);

    /**
     * Met à jour les informations du genre d'un locataire existant dans la base de données
     * @param locataire L'objet Locataire avec les informations mises à jour
     * @param genre Le nouveau genre du locataire
     * @throws DAOException en cas d'erreur lors de la mise à jour du locataire
     */
    void updateLocataireGenre(Locataire locataire, String genre);

    /**
     * Récupère un locataire de la base de données en utilisant son nom et prénom et téléphone
     * @param nom Le nom du locataire à rechercher
     * @param prénom Le prénom du locataire à rechercher
     * @param tel  Le téléphone du locataire à rechercher
     * @return L'objet Locataire trouvé, ou null si aucun locataire n'est trouvé
     * @throws DAOException en cas d'erreur lors de la lecture du locataire
     */
    Locataire getLocataireByNomPrénomTel(String nom, String prénom, String tel);

    /**
     * Récupère tous les locataires de la base de données
     * @return Une liste de tous les objets Locataire
     * @throws DAOException en cas d'erreur lors de la lecture des locataires
     */
    List<Locataire> getAllLocataire();

    /**
     * Supprime un locataire de la base de données
     * @param locataire Locataire à supprimer
     * @throws DAOException en cas d'erreur lors de la suppression du locataire
     */
    void deleteLocataire(Locataire locataire);

    /**
     * Récupère l'identifiant d'un locataire dans la base de données
     * @param locataire L'objet locataire à rechercher
     * @return L'identifiant du locataire
     */
    int getId(Locataire locataire);

    /**
     * Récupère un locataire de la base de données en utilisant son identifiant
     * @param id L'identifiant
     * @return  L'objet Locataire trouvé, ou null si aucun locataire n'est trouvé
     */
    Locataire getLocFromId(int id);
}
