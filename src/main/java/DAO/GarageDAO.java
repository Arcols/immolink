package DAO;

import java.sql.SQLException;
import java.util.List;

import classes.Garage;

public interface GarageDAO {

	/**
	 * Crée un nouveau Garage dans la base de données.
	 *
	 * @param garage L'objet Garage à insérer
	 * @throws DAOException             en cas d'erreur lors de la création du bien
	 *                                  immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	void create(Garage garage) throws DAOException;

	/**
	 * Récupère l'id d'un garage en utilisant son numéro fiscal
	 * @param numero_fiscal le numéro fiscal du garage
	 * @return Integer id du garage demandé
	 * @throws DAOException
	 */
	Integer getIdGarage(String numero_fiscal) throws DAOException;

	/**
	 * Récupère un Garage de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet Garage trouvé, ou null si aucun bien n'est trouvé
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Garage read(int id) throws DAOException;

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
