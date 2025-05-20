package DAO;

import java.sql.SQLException;
import java.util.List;

import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;

public interface GarageDAO {

	/**
	 * Cree un nouveau Garage non associe à un bien louable dans la base de donnees.
	 * @param garage L'objet Garage à inserer
	 * @throws DAOException en cas d'erreur lors de la creation du bien immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	void create(Garage garage) throws DAOException;

	/**
	 * Recupère l'id d'un garage en utilisant son numero fiscal
	 * @param numero_fiscal le numero fiscal du garage
	 * @return Integer id du garage demande
	 * @throws DAOException
	 */
	Integer getIdGarage(String numero_fiscal, TypeLogement typeGarage) throws DAOException;

	/**
	 * Recupère un Garage de la base de donnees en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet Garage trouve, ou null si aucun bien n'est trouve
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Garage read(int id) throws DAOException;

	/**
	 * Supprime un Garage de la base de donnees en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @param typeLogement le type de logement du garage
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id,TypeLogement typeLogement) throws DAOException;

	/**
	 * Met à jour les informations d'un Garage existant dans la base de donnees.
	 * @param id L'identifiant unique du bien immobilier
	 * @param typeActuel le type de logement actuel
	 * @param typeApres le type de logement après la mise à jour
	 * @throws DAOException
	 */
	void updateTypeGarage(int id,TypeLogement typeActuel,TypeLogement typeApres) throws DAOException;

	/**
	 * Recupère tous les biens immobiliers de la base de donnees.
	 *
	 * @return Une liste de tous les objets Garage
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<Garage> findAll() throws DAOException;

	/**
	 * Recupère tous les garages qui ne sont pas associes à un bien louable
	 * @return List<Garage> liste des garages non associes
	 * @throws DAOException
	 */
	List<Garage> findAllGaragePasAssoc() throws DAOException;

	/**
	 * Recupère tous les garages qui sont associes à un bien louable
	 * @return List<Garage> liste des garages associes
	 * @throws DAOException
	 */
	List<Garage> findAllGarageAssoc() throws DAOException;

	/**
	 * Recupère l'id du garage associe à un bien louable
	 * @param idBien id du bien louable
	 * @return Integer id du garage associe
	 * @throws DAOException
	 */
	Integer readIdGarageFromBien(Integer idBien) throws DAOException;

	/**
	 * Recupère l'id du bien associe à un garage
	 * @param idGarage id du garage
	 * @param typeLogement type de logement, doit etre GARAGE_ASSOCIE, si non alors renvoie -1
	 * @return Integer id du bien associe
	 * @throws DAOException
	 */
	Integer idBienGarage(Integer idGarage,TypeLogement typeLogement) throws DAOException;

	/**
	 * Recupère la liste des biens louables associes à un garage
	 * @param id l'id du garage
	 * @return List<Integer> liste des id des biens associes
	 * @throws DAOException
	 */
    List<Integer> getListeBeauxFromGarage(Integer id) throws DAOException;
	}
