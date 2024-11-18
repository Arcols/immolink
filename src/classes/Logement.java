package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import classes.BienLouable.TypeLogement;

public class Logement extends BienLouable {
    private int nb_piece;
    private double surface;
    public Garage garage;
    
    // Constructeur si il n'y a pas de garage présent
    public Logement(int nb_piece, double surface, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) throws IllegalArgumentException{
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        if (surface<9){
            throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
        }
        this.nb_piece = nb_piece;
        this.surface = surface;
        this.garage = null; 
    }

    // Constructeur si il y a un garage
    public Logement(int nb_piece, double surface, Garage garage, String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) {
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
        if (surface<9){
            throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
        }
        this.nb_piece = nb_piece;
        this.surface = surface;
        this.garage = garage;
    }
    
    private void insertIntoTable(int nb_piece, double surface, Garage garage, String numero_fiscal, String complement_adresse, List<Diagnostic> diagnostics) throws SQLException{
        ConnectionDB db = new ConnectionDB();
		String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement,Nombre_pieces,Surface,garage_assoc) VALUES (?, ?, ?)";
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setString(1, numero_fiscal); 
		pstmt.setString(2, complement_adresse); 
        pstmt.setInt(3,TypeLogement.AUTRES.getValue());
        pstmt.setInt(4, nb_piece); 
		pstmt.setDouble(5, surface); 
        pstmt.setInt(6,getIdGarageAssocié(db,numero_fiscal)); 
        pstmt.executeUpdate();
        pstmt.close();
        int idBien = getIdBien(db, numero_fiscal);
        for(Diagnostic diagnostic: diagnostics) {
        	addDiagnosticToBd(db,idBien,diagnostic);
        }
        db.closeConnection(); 
    }
    private int getIdGarageAssocié(ConnectionDB db,String numero_fiscal) throws SQLException {
    	String query_id_garage = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
        PreparedStatement pstmt_id_bienlouable = db.getConnection().prepareStatement(query_id_garage);
        pstmt_id_bienlouable.setString(1, numero_fiscal);
        pstmt_id_bienlouable.setInt(2, TypeLogement.GARAGE.getValue());
        ResultSet rs = pstmt_id_bienlouable.executeQuery();
        return rs.getInt("id");      
    }

    private int getIdBien(ConnectionDB db,String numero_fiscal) throws SQLException {
    	String query_id_garage = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
        PreparedStatement pstmt_id_bienlouable = db.getConnection().prepareStatement(query_id_garage);
        pstmt_id_bienlouable.setString(1, numero_fiscal);
        pstmt_id_bienlouable.setInt(2, TypeLogement.AUTRES.getValue());
        ResultSet rs = pstmt_id_bienlouable.executeQuery();
        return rs.getInt("id");    
    }
    
    private void addDiagnosticToBd(ConnectionDB db,int idBien,Diagnostic diagnostic) throws SQLException {
		String query = "INSERT INTO diagnostiques (id,pdf_diag, type, date_expiration) VALUES (?,?, ?, ?)";
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setInt(1, idBien); 
		pstmt.setBytes(2, diagnostic.getPdfData()); 
		pstmt.setString(3, diagnostic.getReference()); 
        pstmt.setDate(4, diagnostic.getDateInvalidite()); 
        pstmt.executeUpdate();
        pstmt.close();
    }
    /*
     * In : /
     * Out : boolean
     * Return true if Logement get a Garage
     */

    public boolean hasGarage(){
        return garage != null;
    }

    /*
     * In : /
     * Out : Garage or null
     * Return a garage or null if there is no garage
     */
    public Garage getGarage(){
        return this.garage;
    }

    public int getNbPiece(){
        return this.nb_piece;
    }

    public double getSurface(){
        return this.surface;
    }
}
