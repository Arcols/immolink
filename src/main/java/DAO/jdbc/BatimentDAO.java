package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import enumeration.TypeLogement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BatimentDAO implements DAO.BatimentDAO {

	public void create(Batiment batiment) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "INSERT INTO batiment (numero_fiscal,ville, adresse,code_postal) VALUES (?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, batiment.getNumeroFiscal());
			pstmt.setString(2, batiment.getVille());
			pstmt.setString(3, batiment.getAdresse());
			pstmt.setString(4, batiment.getCodePostal());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public Batiment readFisc(String num_fiscal) throws DAOException {
		Batiment batiment = null;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT adresse, code_postal, ville FROM Batiment WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, num_fiscal);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String adresse = rs.getString("adresse");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				batiment = new Batiment(num_fiscal,ville,adresse,code_postal);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return batiment;
	}

	@Override
	public Batiment readId(int id) throws DAOException {
		Batiment batiment = null;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT numero_fiscal, adresse, code_postal, ville FROM Batiment WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String num_fisc = rs.getString("numero_fiscal");
				String adresse = rs.getString("adresse");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				batiment = new Batiment(num_fisc,ville,adresse,code_postal);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return batiment;
	}

	@Override
	public void update(Batiment batiment) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "UPDATE batiment SET ville = ?, adresse = ?, code_postal = ? WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, batiment.getVille());
			pstmt.setString(2, batiment.getAdresse());
			pstmt.setString(3, batiment.getCodePostal());
			pstmt.setString(4, batiment.getNumeroFiscal());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(String num_fisc) throws DAOException {
		Batiment bat = readFisc(num_fisc);
		List<Integer> listIdBienLouables = getIdBienLouables(getIdBat(bat.getVille(), bat.getAdresse()));
		try {
			Connection cn = ConnectionDB.getInstance();
			for (Integer id : listIdBienLouables) {
				new BienLouableDAO().delete(id);
			}
			String query = "DELETE FROM batiment WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, num_fisc);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, List<String>> searchAllBatiments() throws SQLException {
		Map<String, List<String>> batiments = new HashMap<>();
		String query = "SELECT adresse, ville FROM batiment";
		try {
			Connection cn = ConnectionDB.getInstance();
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
			throw new RuntimeException(e);
		}
		return batiments;
	}

	@Override
	public int getIdBat(String ville, String adresse) throws DAOException {
		int id = -1;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query_id_batiment = "SELECT id FROM batiment WHERE adresse = ? AND ville = ?";
			PreparedStatement pstmt_id_batiment = null;
			ResultSet rs = null;
			pstmt_id_batiment = cn.prepareStatement(query_id_batiment);
			pstmt_id_batiment.setString(1, adresse); // Utilisation des paramètres passés
			pstmt_id_batiment.setString(2, ville);
			rs = pstmt_id_batiment.executeQuery();
			if (rs.next()) { // Vérifie s'il y a un résultat
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return id;
	}

	@Override
	public Map<String, List<String>> searchAllBatimentsWithCompl() throws SQLException {
		Map<String, List<String>> batiments = new HashMap<>();
		String query = "SELECT adresse, ville FROM batiment WHERE id IN (SELECT idBat FROM bienlouable)";
		try {
			Connection cn = ConnectionDB.getInstance();
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
		}
		return batiments;
	}

	@Override
	public List<Integer> getIdBienLouables(Integer idBat) throws DAOException {
		List<Integer> idBienLouables = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT id FROM bienlouable WHERE idBat = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, idBat);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				idBienLouables.add(rs.getInt("id"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return idBienLouables;
	}

	@Override
	public List<Batiment> findAll() {
		List<Batiment> batiments = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT numero_fiscal, adresse, code_postal, ville FROM batiment";
			PreparedStatement pstmt = cn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String num_fisc = rs.getString("numero_fiscal");
				String adresse = rs.getString("adresse");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				batiments.add(new Batiment(num_fisc,ville,adresse,code_postal));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return batiments;
	}

	@Override
	public List<Integer> getBienTypeBat(Integer idBat, TypeLogement type) throws DAOException {
		List<Integer> idBienLouables = new LinkedList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT id FROM bienlouable WHERE idBat = ? AND type_logement = ? ";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, idBat);
			pstmt.setInt(2,type.getValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				idBienLouables.add(rs.getInt("id"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return idBienLouables;
	}

}
