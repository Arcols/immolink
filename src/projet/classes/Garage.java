package projet.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import projet.DAO.db.ConnectionDB;


public class Garage extends BienLouable {

    public Garage(String numero_fiscal, String ville ,String adresse,String complement_adresse) throws SQLException {
        super(numero_fiscal, ville,adresse, complement_adresse,null);
        insertIntoTable(this.getNumero_fiscal(),this.getVille(),this.getAdresse(), this.getComplement_adresse());
    }
    
    private void insertIntoTable(String numero_fiscal,String ville,String adresse, String complement_adresse) {
        ConnectionDB db;
		try {
			db = new ConnectionDB();
			String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement,idBat) VALUES (?, ?, ?,?)";
			PreparedStatement pstmt = db.getConnection().prepareStatement(query);
			pstmt.setString(1, numero_fiscal); 
			pstmt.setString(2, complement_adresse); 
	        pstmt.setInt(3,TypeLogement.GARAGE.getValue()); 
	        pstmt.setInt(4, foundIDBatInDB(db, ville, adresse));
	        pstmt.executeUpdate();
	        pstmt.close();
	        db.closeConnection(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
}
