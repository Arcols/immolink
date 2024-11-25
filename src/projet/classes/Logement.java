package projet.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import projet.DAO.db.ConnectionDB;

public class Logement extends BienLouable {
	private int nb_piece;
	private double surface;

	// Constructeur si il n'y a pas de garage présent
	public Logement(int nb_piece, double surface, String numero_fiscal, String ville ,String adresse,
			String complement_adresse,List<Diagnostic> diagnostic,Boolean haveGarage) throws IllegalArgumentException, SQLException {
		super(numero_fiscal, ville,adresse,complement_adresse, diagnostic);
		if (surface < 9.0F) {
			throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
		}
		this.nb_piece = nb_piece;
		this.surface = surface;
		insertIntoTable(nb_piece, surface, numero_fiscal, ville, adresse,complement_adresse, diagnostic,haveGarage);
	}

	private void insertIntoTable(int nb_piece, double surface, String numero_fiscal,String ville ,String adresse,
			String complement_adresse, List<Diagnostic> diagnostics,Boolean haveGarage) throws SQLException {
		ConnectionDB db = new ConnectionDB();
		String query;
		if(haveGarage) {
			query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement,Nombre_pieces,Surface,idBat,garage_assoc) VALUES (?,?,?,?,?,?,?)";
		}else {
			query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse,type_logement,Nombre_pieces,Surface,idBat) VALUES (?,?,?,?,?,?)";
		}
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setString(1, numero_fiscal);
		pstmt.setString(2, complement_adresse);
		pstmt.setInt(3, TypeLogement.APPARTEMENT.getValue());
		pstmt.setInt(4, nb_piece);
		pstmt.setDouble(5, surface);
		pstmt.setInt(6, foundIDBatInDB(db,ville,adresse));
		if(haveGarage) {
			pstmt.setInt(7, getIdGarageAssocié(db, numero_fiscal));
		}
		pstmt.executeUpdate();
		pstmt.close();
		int idBien = getIdBien(db, numero_fiscal);
		for (Diagnostic diagnostic : diagnostics) {
			addDiagnosticToBd(db, idBien, diagnostic);
		}
		db.closeConnection();
	}

	private int getIdGarageAssocié(ConnectionDB db, String numero_fiscal) throws SQLException {
	    String query_id_garage = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
	    PreparedStatement pstmt_id_bienlouable = null;
	    ResultSet rs = null;
	    try {
	        pstmt_id_bienlouable = db.getConnection().prepareStatement(query_id_garage);
	        pstmt_id_bienlouable.setString(1, numero_fiscal);
	        pstmt_id_bienlouable.setInt(2, TypeLogement.GARAGE.getValue());
	        rs = pstmt_id_bienlouable.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        } else {
	            throw new SQLException("Pas de données pour ce numéro fiscal et type de logement (garage)");
	        }
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (pstmt_id_bienlouable != null) {
	            pstmt_id_bienlouable.close();
	        }
	    }
	}

	private int getIdBien(ConnectionDB db, String numero_fiscal) throws SQLException {
	    String query_id_bien = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
	    PreparedStatement pstmt_id_bienlouable = null;
	    ResultSet rs = null;
	    try {
	        pstmt_id_bienlouable = db.getConnection().prepareStatement(query_id_bien);
	        pstmt_id_bienlouable.setString(1, numero_fiscal);
	        pstmt_id_bienlouable.setInt(2, TypeLogement.APPARTEMENT.getValue());
	        rs = pstmt_id_bienlouable.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        } else {
	            throw new SQLException("Pas de données pour ce numéro fiscal et type de logement (appartement)");
	        }
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (pstmt_id_bienlouable != null) {
	            pstmt_id_bienlouable.close();
	        }
	    }
	}

	private void addDiagnosticToBd(ConnectionDB db, int idBien, Diagnostic diagnostic) throws SQLException {
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
	 * In : / Out : boolean Return true if Logement get a Garage
	 */
	/*public boolean hasGarage() {
		return garage != null;
	}*/

	/*
	 * In : / Out : Garage or null Return a garage or null if there is no garage
	 */
	/*
	public Garage getGarage() {
		return this.garage;
	}*/

	public int getNbPiece() {
		return this.nb_piece;
	}

	public double getSurface() {
		return this.surface;
	}
}
