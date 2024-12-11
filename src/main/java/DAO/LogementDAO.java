package DAO;

import java.util.List;

import classes.BienLouable;
import classes.Garage;
import classes.Logement;

public interface LogementDAO {

	/**
	 * Crée un nouveau BienLouable dans la base de données.
	 *
	 * @param logement L'objet Logement à insérer
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

	/**
	 *  Récupère l'id d'un logement en utilisant son numéro fiscal
	 * Lier un garage à un logement
	 * @param logement
	 * @param garage
	 * @throws DAOException
	 */
	void lierUnGarageAuBienLouable(Logement logement, Garage garage) throws DAOException;

	/**
	 * Récupère l'identifiant d'un bien louable en utilisant son numéro fiscal.
	 * @param num_fiscal le numéro fiscal du bien louable
	 * @return l'identifiant du bien louable
	 * @throws DAOException
	 */
	Integer getId(String num_fiscal) throws DAOException;

	/**
	 * Récupère le garage associé à un logement
	 * @param logement le logement
	 * @return l'identifiant du garage associé
	 */
	Integer getGarageAssocie(Logement logement);

}
