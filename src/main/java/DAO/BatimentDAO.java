package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import classes.Batiment;

public interface BatimentDAO {
	/**
	 * Crée un nouveau Batiment dans la base de données.
	 *
	 * @param bien L'objet Batiment à insérer
	 * @throws DAOException             en cas d'erreur lors de la création du bien
	 *                                  immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	void create(Batiment batiment) throws DAOException;

	/**
	 * Récupère un Batiment de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet Batiment trouvé, ou null si aucun bien n'est trouvé
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Batiment read(int id) throws DAOException;

	/**
	 * Met à jour les informations d'un Batiment existant dans la base de données.
	 *
	 * @param bien L'objet Batiment avec les informations mises à jour
	 * @throws DAOException en cas d'erreur lors de la mise à jour du bien
	 *                      immobilier
	 */
	int getIdBat(String ville, String adresse) throws DAOException;

	void update(Batiment batiment) throws DAOException;

	/**
	 * Supprime un Batiment de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id) throws DAOException;

	/**
	 * Récupère tous les biens immobiliers de la base de données.
	 *
	 * @return Une liste de tous les objets Batiment
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	Map<String, List<String>> searchAllBatiments() throws SQLException;
}
