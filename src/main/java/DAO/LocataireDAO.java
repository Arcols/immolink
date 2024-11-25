package DAO;

import classes.Locataire;

import java.util.List;

public interface LocataireDAO {
    /*
     * Ajoute un locataire dans la base de données
     *  @param locataire L'objet Locataire à insérer
     * @throws DAOException en cas d'erreur lors de la création du locataire
     */
    public void addLocataire(Locataire locataire);

    /*
     * Met à jour les informations d'un locataire existant dans la base de données
     * @param locataire L'objet Locataire avec les informations mises à jour
     * @throws DAOException en cas d'erreur lors de la mise à jour du locataire
     */
    public void updateLocataire(Locataire locataire);

    /*
     * Récupère un locataire de la base de données en utilisant son identifiant
     * @param id L'identifiant unique du locataire
     * @return L'objet Locataire trouvé, ou null si aucun locataire n'est trouvé
     * @throws DAOException en cas d'erreur lors de la lecture du locataire
     */
    public Locataire getLocataireById(int id);

    /*
     * Récupère tous les locataires de la base de données
     * @return Une liste de tous les objets Locataire
     * @throws DAOException en cas d'erreur lors de la lecture des locataires
     */
    public List<Locataire> getAllLocataire();

    /*
     * Supprime un locataire de la base de données en utilisant son identifiant
     * @param id L'identifiant unique du locataire à supprimer
     * @throws DAOException en cas d'erreur lors de la suppression du locataire
     */
    public void deleteLocataire(int id);
}
