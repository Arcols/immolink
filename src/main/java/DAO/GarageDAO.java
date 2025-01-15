package DAO;

import java.sql.SQLException;
import java.util.List;

import classes.Garage;
import enumeration.TypeLogement;

public interface GarageDAO {

	/**
	 * Crée un nouveau Garage non associé à un bien louable dans la base de données.
	 * @param garage L'objet Garage à insérer
	 * @throws DAOException en cas d'erreur lors de la création du bien immobilier
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
	Integer getIdGarage(String numero_fiscal, TypeLogement typeGarage) throws DAOException;

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
	 * @param typeLogement le type de logement du garage
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id,TypeLogement typeLogement) throws DAOException;

	/**
	 * Met à jour les informations d'un Garage existant dans la base de données.
	 * @param id L'identifiant unique du bien immobilier
	 * @param typeActuel le type de logement actuel
	 * @param typeApres le type de logement après la mise à jour
	 * @throws DAOException
	 */
	void updateTypeGarage(int id,TypeLogement typeActuel,TypeLogement typeApres) throws DAOException;

	/**
	 * Récupère tous les biens immobiliers de la base de données.
	 *
	 * @return Une liste de tous les objets Garage
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<Garage> findAll() throws DAOException;

	/**
	 * Récupère tous les garages qui ne sont pas associés à un bien louable
	 * @return List<Garage> liste des garages non associés
	 * @throws DAOException
	 */
	List<Garage> findAllGaragePasAssoc() throws DAOException;

	/**
	 * Récupère tous les garages qui sont associés à un bien louable
	 * @return List<Garage> liste des garages associés
	 * @throws DAOException
	 */
	List<Garage> findAllGarageAssoc() throws DAOException;

	/**
	 *
	 * Récupère l'id du garage associé à un bien louable
	 * @param idBien id du bien louable
	 * @return Integer id du garage associé
	 * @throws DAOException
	 */
	Integer readIdGarageFromBien(Integer idBien) throws DAOException;

}
