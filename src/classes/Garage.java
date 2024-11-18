package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class Garage extends BienLouable {

    public Garage(String numero_fiscal, String complement_adresse, Batiment batiment) throws SQLException {
        super(numero_fiscal, complement_adresse, batiment,null);
        insertIntoTable(this.getNumero_fiscal(), this.getComplement_adresse());
    }
    
    private void insertIntoTable(String numero_fiscal, String complement_adresse) throws SQLException{
        ConnectionDB db = new ConnectionDB();
		String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement) VALUES (?, ?, ?)";
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setString(1, numero_fiscal); 
		pstmt.setString(2, complement_adresse); 
        pstmt.setInt(3,TypeLogement.GARAGE.getValue()); 
        pstmt.executeUpdate();
        pstmt.close();
        
        db.closeConnection(); 
    }
}
