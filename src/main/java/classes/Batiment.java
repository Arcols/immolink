package classes;

import DAO.BatimentDAO;
import DAO.DAOException;
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
		try {
			this.insertIntoTable();
		} catch (DAOException e) {
            throw new RuntimeException(e);
        }
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
	public Map<String, List<String>> getAllBatiments() throws SQLException {
		DAO.jdbc.BatimentDAO res = new DAO.jdbc.BatimentDAO();
        try {
            return res.searchAllBatiments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	private void insertIntoTable() throws DAOException {
		DAO.jdbc.BatimentDAO insert = new DAO.jdbc.BatimentDAO();
		insert.create(this);
	}
}
