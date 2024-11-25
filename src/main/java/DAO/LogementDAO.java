package DAO;

import java.util.List;

import classes.Logement;

public interface LogementDAO {
	/**
	 * Crée un nouveau BienLouable dans la base de données.
	 *
	 * @param bien L'objet BienLouable à insérer
	 * @throws DAOException en cas d'erreur lors de la création du bien immobilier
	 */
	void create(Logement logement) throws DAOException;

	/**
	 * Récupère un BienLouable de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet BienLouable trouvé, ou null si aucun bien n'est trouvé
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Logement read(int id) throws DAOException;

	/**
	 * Met à jour les informations d'un BienLouable existant dans la base de
	 * données.
	 *
	 * @param bien L'objet BienLouable avec les informations mises à jour
	 * @throws DAOException en cas d'erreur lors de la mise à jour du bien
	 *                      immobilier
	 */
	void update(Logement logement) throws DAOException;

	/**
	 * Supprime un BienLouable de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id) throws DAOException;

	/**
	 * Récupère tous les biens immobiliers de la base de données.
	 *
	 * @return Une liste de tous les objets BienLouable
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<Logement> findAll() throws DAOException;

}
