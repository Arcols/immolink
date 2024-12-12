package DAO;

import classes.Bail;
import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BienLouableDAO {

	/**
	 * Crée un nouveau BienLouable dans la base de données.
	 *
	 * @param bien L'objet BienLouable à créer
	 * @throws DAOException en cas d'erreur lors de la création du bien immobilier
	 */
	public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface)
			throws DAOException, IllegalArgumentException, SQLException;

	/**
	 * Lie un garage à un bien louable en insérant son id dans la base de donnée dans la colonne garage_assoc
	 * @param garage Garage object
	 * @param bien BienLouable object
	 */
	 void lierUnGarageAuBienLouable(BienLouable bien,Garage garage) throws  DAOException;

	/**
	 * Récupère un BienLouable de la base de données en utilisant son numéro fiscal.
	 * @param num_fiscal
	 * @return
	 * @throws DAOException
	 */
	BienLouable readFisc(String num_fiscal) throws DAOException;

	/**
	 * Récupère un BienLouable de la base de données en utilisant son identifiant.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	BienLouable readId(int id) throws DAOException;

	/**
	 *  Récupère l'id d'un bien louable en utilisant son numéro fiscal
	 * @param num_fiscal
	 * @return
	 * @throws DAOException
	 */
	Integer getId(String num_fiscal) throws DAOException;

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
	List<BienLouable> findAll() throws DAOException;

	/**
	 *  Récupère le garage associé à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return l'identifiant du garage associé
	 */
	Integer getTypeFromCompl(String ville,String adresse, String complement);

	/**
	 *  Récupère le garage associé à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return
	 */
	Integer getNbPieceFromCompl(String ville,String adresse, String complement);

	/**
	 * Récupère le garage associé à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return l'identifiant du garage associé
	 */
	Double getSurfaceFromCompl(String ville,String adresse, String complement);

	/**
	 * Récupère le garage associé à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return l'identifiant du garage associé
	 */
	String getFiscFromCompl(String ville,String adresse, String complement);

    Bail getBailFromBien(BienLouable bien);

    /**
	 * Récupère le garage associé à un bien louable
	 * @return l'identifiant du garage associé
	 * @throws SQLException
	 */
    Map<String, List<String>> getAllcomplements() throws SQLException;

	/**
	 * Récupère un BienLouable de la base de données en utilisant son identifiant.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	BienLouable readId(int id) throws DAOException;
}