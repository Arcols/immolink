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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Garage garage = (Garage) o;
		return this.getNumero_fiscal().equals(garage.getNumero_fiscal())
				&& this.getVille().equals(garage.getVille())
				&& this.getAdresse().equals(garage.getAdresse())
				&& this.getComplement_adresse().equals(garage.getComplement_adresse())
				&& this.getTypeLogement().equals(garage.getTypeLogement());
	}
}
