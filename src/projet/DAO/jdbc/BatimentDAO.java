package projet.DAO.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projet.DAO.DAOException;
import projet.DAO.db.ConnectionDB;
import projet.classes.Batiment;

public class BatimentDAO implements projet.DAO.BatimentDAO {

	@Override
	public void create(Batiment batiment) throws DAOException {
		ConnectionDB cn;
		try {
			cn = new ConnectionDB();
			String query = "INSERT INTO batiment (numero_fiscal,ville, adresse,code_postal) VALUES (?,?,?,?)";
			PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
			pstmt.setString(1, batiment.getNumeroFiscal());
			pstmt.setString(2, batiment.getVille());
			pstmt.setString(3, batiment.getAdresse());
			pstmt.setString(4, "31000");
			pstmt.executeUpdate();
			pstmt.close();
			cn.closeConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Batiment read(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Batiment batiment) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, List<String>> searchAllBatiments() throws SQLException {
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

	@Override
	public int getIdBat(String ville, String adresse) throws DAOException {
		ConnectionDB db;
		try {
			db = new ConnectionDB();
			String query_id_batiment = "SELECT id FROM batiment WHERE adresse = ? AND ville = ?";
			PreparedStatement pstmt_id_batiment = null;
			ResultSet rs = null;
			pstmt_id_batiment = db.getConnection().prepareStatement(query_id_batiment);
			pstmt_id_batiment.setString(1, adresse); // Utilisation des paramètres passés
			pstmt_id_batiment.setString(2, ville);
			rs = pstmt_id_batiment.executeQuery();
			if (rs.next()) { // Vérifie s'il y a un résultat
				return rs.getInt("id");
			} else {
				throw new SQLException("Pas de données pour ce couple ville adresse (bizarre)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
