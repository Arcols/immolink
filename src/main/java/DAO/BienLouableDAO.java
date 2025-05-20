package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import classes.Bail;
import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;

public interface BienLouableDAO {

	/**
	 * Cree un nouveau BienLouable dans la base de donnees.
	 * @param bien L'objet BienLouable à creer
	 * @throws DAOException en cas d'erreur lors de la creation du bien louable
	 */
	void create(BienLouable bien, TypeLogement type, int nb_piece, double surface)
			throws DAOException, IllegalArgumentException, SQLException;

	/**
	 * Lie un garage à un bien louable en inserant son id dans la base de donnee dans la colonne garage_assoc
	 * @param garage Garage object
	 * @param bien BienLouable object
	 */
	void lierUnGarageAuBienLouable(BienLouable bien,Garage garage) throws  DAOException;

	/**
	 * Recupère un BienLouable de la base de donnees en utilisant son numero fiscal.
	 * @param num_fiscal
	 * @return
	 * @throws DAOException
	 */
	BienLouable readFisc(String num_fiscal) throws DAOException;

	/**
	 * Recupère un BienLouable de la base de donnees en utilisant son identifiant.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	BienLouable readId(int id) throws DAOException;

	/**
	 *  Recupère l'id d'un bien louable en utilisant son numero fiscal
	 * @param num_fiscal le numero fiscal du bien louable
	 * @return l'identifiant du bien louable
	 * @throws DAOException
	 */
	Integer getId(String num_fiscal) throws DAOException;

	/**
	 * Supprime un BienLouable de la base de donnees en utilisant son identifiant.
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
	List<BienLouable> findAll() throws DAOException;


	/**
	 *  Recupère le garage associe à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return
	 */
	Integer getNbPieceFromCompl(String ville,String adresse, String complement);

	/**
	 * Recupère le garage associe à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return l'identifiant du garage associe
	 */
	Double getSurfaceFromCompl(String ville,String adresse, String complement);

	/**
	 * Recupère le garage associe à un bien louable
	 * @param ville la ville du bien louable
	 * @param adresse l'adresse du bien louable
	 * @param complement le complement d'adresse du bien louable
	 * @return l'identifiant du garage associe
	 */
	String getFiscFromCompl(String ville,String adresse, String complement);



	/**
	 * Recupère le Bail associe à un bien louable en fonction de l'annee de fin
	 * @param bien le bien louable
	 * @param annne la date pour avoir l'annee de la fin du bail
	 * @return le bail associe
	 */
	Bail getBailFromBienAndDate(BienLouable bien, Date annne);

    /**
	 * Recupère tous les complements d'adresse de la base de donnees.
	 * @return Une map de tous les complements d'adresse Map : Adresse -> List<Complement>
	 * @throws SQLException
	 */
    Map<String, List<String>> getAllcomplements() throws SQLException;

	/**
	 * Recupère le type de logement associe à un bien louable
	 * @return le type de logement
	 */
	Map<String, List<String>> getAllComplBail();

	/**
	 * Boolean si le bien louable a un garage associe
	 * @param id l'identifiant du bien louable
	 * @return true si le bien louable a un garage associe
	 */
	boolean haveGarage(Integer id);

	/**
	 * Recupère la liste des id des Beaux associes à un bien louable
	 * @param bien le bien louable
	 * @return la liste des beaux associes
	 */
	List<Integer> getListeBeauxFromBien(BienLouable bien);

	/**
	 * Recupère le type de logement associe à un bien louable
	 * @param id l'identifiant du bien louable
	 * @return le type de logement
	 */
	TypeLogement getTypeFromId(int id);

	/**
	 * Delie un garage à son bien louable
	 * @param idBien l'identifiant du bien louable
	 * @throws DAOException
	 */
	void delierGarage(Integer idBien) throws DAOException;

	/**
	 * Recupère la liste des biens louables sans garage associe
	 * @return la liste des biens louables sans garage associe
	 * @throws DAOException
	 */
	List<BienLouable> getAllBienLouableNoGarageLink() throws DAOException;
}