package DAO;

import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
 * Interface définissant les opérations CRUD pour les objets BienLouable.
 */
public interface BienLouableDAO {

	/*
	 * Crée un nouveau BienLouable dans la base de données.
	 *
	 * @param bien L'objet BienLouable à insérer
	 * @throws DAOException             en cas d'erreur lors de la création du bien
	 *                                  immobilier
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface)
			throws DAOException, IllegalArgumentException, SQLException;

	/**
	 * Ajoute un garage à un bien louable
	 * @param garage Garage object
	 * @param bien BienLouable object
	 */
	 void ajouterUnGarageAuBienLouable(BienLouable bien,Garage garage) throws  DAOException;

	/**
	 * Récupère un BienLouable de la base de données en utilisant son numéro fiscal.
	 * @param num_fiscal
	 * @return
	 * @throws DAOException
	 */
	BienLouable readFisc(String num_fiscal) throws DAOException;

	/**
	 *  Récupère l'id d'un bien louable en utilisant son numéro fiscal
	 * @param num_fiscal
	 * @return
	 * @throws DAOException
	 */
	Integer getId(String num_fiscal) throws DAOException;

	/*
	 * Supprime un BienLouable de la base de données en utilisant son identifiant.
	 *
	 * @param id L'identifiant unique du bien immobilier à supprimer
	 * @throws DAOException en cas d'erreur lors de la suppression du bien
	 *                      immobilier
	 */
	void delete(int id) throws DAOException;

	/*
	 * Récupère tous les biens immobiliers de la base de données.
	 *
	 * @return Une liste de tous les objets BienLouable
	 * @throws DAOException en cas d'erreur lors de la lecture des biens immobiliers
	 */
	List<BienLouable> findAll() throws DAOException;

    Map<String, List<String>> getAllcomplements() throws SQLException;
}