package classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

public class Garage extends classes.BienLouable {

	public Garage(String numero_fiscal, String ville, String adresse, String complement_adresse) throws SQLException {
		super(numero_fiscal, ville, adresse, complement_adresse, null,null);
	}
}
