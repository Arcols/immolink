package classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

public class Garage extends classes.BienLouable {

	public Garage(String numero_fiscal, String ville, String adresse, String complement_adresse) throws SQLException {
		super(numero_fiscal, ville, adresse, complement_adresse, null);
		this.insertIntoTable(this.getNumero_fiscal(), this.getVille(), this.getAdresse(), this.getComplement_adresse());
	}

	private void insertIntoTable(String numero_fiscal, String ville, String adresse, String complement_adresse) {
		ConnectionDB db;
		try {
			db = new ConnectionDB();
			String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement,idBat) VALUES (?, ?, ?,?)";
			PreparedStatement pstmt = db.getConnection().prepareStatement(query);
			pstmt.setString(1, numero_fiscal);
			pstmt.setString(2, complement_adresse);
			pstmt.setInt(3, TypeLogement.GARAGE.getValue());
			pstmt.setInt(4, this.foundIDBatInDB(db, ville, adresse));
			pstmt.executeUpdate();
			pstmt.close();
			db.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
