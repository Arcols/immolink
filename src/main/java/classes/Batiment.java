package classes;

import DAO.db.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Batiment extends BienImmobilier {
	private String adresse;

	private String numero_fiscal;
	// private String code_postal;

	private String ville;

	public List<BienLouable> bien_louable;

	// Un batiment est initialisé sans bien louable
	public Batiment(String numero_fiscal, String ville, String adresse) {
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numéro fiscal invalide");
		}
		this.adresse = adresse;
		// this.code_postal = code_postal;
		this.ville = ville;
		this.numero_fiscal = numero_fiscal;
		this.bien_louable = new ArrayList<BienLouable>();
	}

	public String getAdresse() {
		return this.adresse;
	}

	// public String getCode_postal() {return this.code_postal;}

	public String getVille() {
		return this.ville;
	}

	public String getNumeroFiscal() {
		return this.numero_fiscal;
	}

	public List<BienLouable> getBien_louable() {
		return this.bien_louable;
	}

	public void ajouterBienLouable(BienLouable bien_louable) {
		this.bien_louable.add(bien_louable);
	}

	public void supprimerBienLouable(BienLouable bien_louable) {
		this.bien_louable.remove(bien_louable);
	}

	// search information about all batiments in the database
	// Out : Map<String,List<String>> :
	public static Map<String, List<String>> searchAllBatiments() throws SQLException {
		Map<String, List<String>> batiments = new HashMap<>();

		ConnectionDB db = new ConnectionDB();
		String query = "SELECT adresse, ville FROM batiment";
		try {
			PreparedStatement pstmt = db.getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String adresse = rs.getString("adresse");
				String ville = rs.getString("ville");
				batiments.putIfAbsent(ville, new ArrayList<>());
				batiments.get(ville).add(adresse);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return batiments;
	}

}
