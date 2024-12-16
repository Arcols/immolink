package classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

public class Garage extends classes.BienLouable {
	/**
	 * Constructeur de la classe Garage
	 * @param numero_fiscal
	 * @param ville
	 * @param adresse
	 * @param complement_adresse
	 */
	public Garage(String numero_fiscal, String ville, String adresse, String complement_adresse) {
		super(numero_fiscal, ville, adresse, complement_adresse, null,null);
	}

	/**
	 * Récupérer le type du logement
	 * @return le type du logement
	 */
	public TypeLogement getTypeLogement(){
		return TypeLogement.GARAGE;
	}

}
