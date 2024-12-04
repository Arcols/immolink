package DAO.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Garage;
import enumeration.TypeLogement;

public class GarageDAO implements DAO.GarageDAO {

	private Connection cn;

	@Override
	public void create(Garage garage) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String requete = "INSERT INTO Logement bienlouable VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setString(1, garage.getNumero_fiscal());
			pstmt.setString(2, garage.getComplement_adresse());
			pstmt.setInt(3, 2);
			pstmt.setString(4, null);
			pstmt.setString(5, null);
			pstmt.setString(6, null);
			BatimentDAO bat = new BatimentDAO();
			pstmt.setInt(7, bat.getIdBat(garage.getVille(), garage.getAdresse()));
			pstmt.executeUpdate();
			pstmt.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Garage read(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdGarage(String numero_fiscal) throws DAOException {
		ConnectionDB db;
		Connection cn = null;
		Integer idGarage = null;
		try {
			db = ConnectionDB.getInstance();
			cn = db.getConnection();
			String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, numero_fiscal);
			pstmt.setInt(2, TypeLogement.GARAGE.getValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				idGarage = rs.getInt("id");
			}
			rs.close();
			pstmt.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idGarage;
	}

	@Override
	public void update(Garage bien) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Garage> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
