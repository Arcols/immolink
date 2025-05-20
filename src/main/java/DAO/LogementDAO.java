package DAO;

import java.util.List;

import classes.Garage;
import classes.Logement;
import enumeration.TypeLogement;

public interface LogementDAO {

	/**
	 * Cree un nouveau BienLouable dans la base de donnees.
	 *
	 * @param logement L'objet Logement à inserer
	 * @param typeLogement le type de logement
	 * @throws DAOException en cas d'erreur lors de la creation du bien immobilier
	 */
	void create(Logement logement, TypeLogement typeLogement) throws DAOException;

	/**
	 * Recupère un BienLouable de la base de donnees en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet BienLouable trouve, ou null si aucun bien n'est trouve
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Logement read(int id) throws DAOException;

	/**
	 * Supprime un BienLouable de la base de donnees en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id) throws DAOException;

	/**
	 * Recupère tous les biens immobiliers de la base de donnees.
	 *
	 * @return Une liste de tous les objets BienLouable
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<Logement> findAll() throws DAOException;

	/**
	 *  Recupère l'id d'un logement en utilisant son numero fiscal
	 * Lier un garage à un logement
	 * @param logement le logement
	 * @param garage le garage
	 * @param typeLogement le type de logement
	 * @throws DAOException
	 */
	void lierUnGarageAuBienLouable(Logement logement, Garage garage,TypeLogement typeLogement) throws DAOException;

	/**
	 * Recupère l'identifiant d'un bien louable en utilisant son numero fiscal.
	 * @param num_fiscal le numero fiscal du bien louable
	 * @param typeLogement le type de logement
	 * @return l'identifiant du bien louable
	 * @throws DAOException
	 */
	Integer getId(String num_fiscal,TypeLogement typeLogement) throws DAOException;

	/**
	 * Recupère le garage associe à un logement
	 * @param logement le logement
	 * @param typeLogement le type de logement
	 * @return l'identifiant du garage associe
	 */
	Integer getGarageAssocie(Logement logement,TypeLogement typeLogement);


}
