package projet.DAO;

import java.sql.SQLException;
import java.util.List;

import projet.classes.Garage;

public interface GarageDAO {
	/**
	 * Crée un nouveau Garage dans la base de données.
	 *
	 * @param bien L'objet Garage à insérer
	 * @throws DAOException             en cas d'erreur lors de la création du bien
	 *                                  immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	void create(Garage garage) throws DAOException;

	/**
	 * Récupère un Garage de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet Garage trouvé, ou null si aucun bien n'est trouvé
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Garage read(int id) throws DAOException;

	/**
	 * Met à jour les informations d'un Garage existant dans la base de données.
	 *
	 * @param bien L'objet Garage avec les informations mises à jour
	 * @throws DAOException en cas d'erreur lors de la mise à jour du bien
	 *                      immobilier
	 */
	void update(Garage bien) throws DAOException;

	/**
	 * Supprime un Garage de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id) throws DAOException;

	/**
	 * Récupère tous les biens immobiliers de la base de données.
	 *
	 * @return Une liste de tous les objets Garage
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<Garage> findAll() throws DAOException;
}
