package classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

public class Garage extends classes.BienLouable {
	/**
	 * Constructeur de la classe Garage
	 * @param numero_fiscal le numéro fiscal du bien
	 * @param ville la ville du bien
	 * @param adresse l'adresse du bien
	 * @param complement_adresse le complément d'adresse du bien
	 * @param typeLogement le type de logement
	 */
	public Garage(String numero_fiscal, String ville, String adresse, String complement_adresse,TypeLogement typeLogement) {
		super(numero_fiscal, ville, adresse, complement_adresse, null,null,typeLogement);
	}

}
