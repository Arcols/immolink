package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatimentDAO implements DAO.BatimentDAO {

	public void create(Batiment batiment) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "INSERT INTO batiment (numero_fiscal,ville, adresse,code_postal) VALUES (?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, batiment.getNumeroFiscal());
			pstmt.setString(2, batiment.getVille());
			pstmt.setString(3, batiment.getAdresse());
			pstmt.setString(4, "31000");
			pstmt.executeUpdate();
			pstmt.close();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Batiment readFisc(String num_fiscal) throws DAOException {
		Batiment batiment = null;
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "SELECT adresse, code_postal, ville FROM Batiment WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, num_fiscal);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String adresse = rs.getString("adresse");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				batiment = new Batiment(num_fiscal,ville,adresse);
			}
			pstmt.close();
			cn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return batiment;
	}
	@Override
	public Batiment readId(int id) throws DAOException {
		Batiment batiment = null;
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "SELECT numero_fiscal, adresse, code_postal, ville FROM Batiment WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String num_fisc = rs.getString("numero_fiscal");
				String adresse = rs.getString("adresse");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				batiment = new Batiment(num_fisc,ville,adresse);
			}
			pstmt.close();
			cn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return batiment;
	}

	@Override
	public void update(Batiment batiment) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "UPDATE batiment SET ville = ?, adresse = ?, code_postal = ? WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, batiment.getVille());
			pstmt.setString(2, batiment.getAdresse());
			pstmt.setString(3, "31000");
			pstmt.setString(4, batiment.getNumeroFiscal());
			pstmt.executeUpdate();
			pstmt.close();
			cn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete(String num_fisc) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "DELETE FROM batiment WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, num_fisc);
			pstmt.executeUpdate();
			pstmt.close();
			cn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, List<String>> searchAllBatiments() throws SQLException {
		Map<String, List<String>> batiments = new HashMap<>();
		String query = "SELECT adresse, ville FROM batiment";
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			PreparedStatement pstmt = cn.prepareStatement(query);
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
			cn.close();
		}
		return batiments;
	}

	@Override
	public int getIdBat(String ville, String adresse) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query_id_batiment = "SELECT id FROM batiment WHERE adresse = ? AND ville = ?";
			PreparedStatement pstmt_id_batiment = null;
			ResultSet rs = null;
			pstmt_id_batiment = cn.prepareStatement(query_id_batiment);
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
