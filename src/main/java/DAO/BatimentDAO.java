package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import classes.Batiment;
import enumeration.TypeLogement;

public interface BatimentDAO {

	/**
	 * Cree un nouveau Batiment dans la base de donnees.
	 *
	 * @param batiment L'objet Batiment à inserer
	 * @throws DAOException             en cas d'erreur lors de la creation du bien
	 *                                  immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	void create(Batiment batiment) throws DAOException;

	/**
	 *  Recupère l'id d'un batiment en utilisant son numero fiscal
	 * @param num_fisc le numero fiscal du batiment
	 * @return Integer id du batiment demande
	 * @throws DAOException
	 */
	Batiment readFisc(String num_fisc) throws DAOException;

	/**
	 * Recupère un Batiment de la base de donnees en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier
	 * @return L'objet Batiment trouve, ou null si aucun bien n'est trouve
	 * @throws DAOException en cas d'erreur lors de la lecture du bien immobilier
	 */
	Batiment readId(int id) throws DAOException;

	/**
	 * Met à jour les informations d'un Batiment existant dans la base de donnees.
	 *
	 * @param ville
	 * @param adresse
	 * @throws DAOException en cas d'erreur lors de la mise à jour du bien
	 *                      immobilier
	 */
	int getIdBat(String ville, String adresse) throws DAOException;

	/**
	 *  Recupère l'id d'un batiment en utilisant son numero fiscal
	 * @param batiment le batiment
	 * @throws DAOException
	 */
	void update(Batiment batiment) throws DAOException;

	/**
	 * Supprime un Batiment de la base de donnees en utilisant son identifiant.
	 *
	 * @param num_fisc L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(String num_fisc) throws DAOException;

	/**
	 * Recupère tous les biens immobiliers de la base de donnees.
	 *
	 * @return Une liste de tous les objets Batiment
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	Map<String, List<String>> searchAllBatiments() throws SQLException;

	/**
	 * Cherche tous les batiments qui ont des biens louables
	 * @return une Map des villes avec toutes les adresses pour cette ville
	 * @throws SQLException
	 */
	Map<String, List<String>> searchAllBatimentsWithCompl() throws SQLException;

	/**
	 * Recupère tous les ids des biens louables d'un batiment
	 * @param idBat l'id du batiment
	 * @return la liste des ids des biens louables
	 * @throws DAOException
	 */
	List<Integer> getIdBienLouables(Integer idBat) throws DAOException;

	/**
	 * Recupère tous les batiments de la base de donnees
	 * @return la liste des batiments
	 */
    List<Batiment> findAll();

	/**Recupère tous les ids des biens d'un batiment en fonction de leur type
			* @param idBat l'id du batiment
	 		* @param type le type de bien rechercher
			* @return la liste des ids des biens
	 * @throws DAOException
	*/
	List<Integer> getBienTypeBat(Integer idBat, TypeLogement type) throws DAOException;
}
