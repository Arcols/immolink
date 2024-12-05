package DAO.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;

public class GarageDAO implements DAO.GarageDAO {

	@Override
	public void create(Garage garage) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String requete = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, idBat, garage_assoc) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setString(1, garage.getNumero_fiscal());
			pstmt.setString(2, garage.getComplement_adresse());
			pstmt.setInt(3, TypeLogement.GARAGE.getValue());
			pstmt.setString(4, null);
			pstmt.setString(5, null);
			pstmt.setString(7, null);
			BatimentDAO bat = new BatimentDAO();
			pstmt.setInt(6, bat.getIdBat(garage.getVille(), garage.getAdresse()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Garage read(int id) throws DAOException {
		try{
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT * FROM bienlouable WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String numero_fiscal = rs.getString("numero_fiscal");
				String complement_d_adresse = rs.getString("complement_adresse");
				BatimentDAO batDAO = new BatimentDAO();
				Batiment bat = batDAO.readId(rs.getInt("idBat"));
				String ville = bat.getVille();
				String adresse = bat.getAdresse();
				Garage garage = new Garage(numero_fiscal,ville,adresse,complement_d_adresse);
				return garage;
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

	@Override
	public Integer getIdGarage(String numero_fiscal) throws DAOException {
		Integer idGarage = null;
		try {
			Connection cn = ConnectionDB.getInstance();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idGarage;
	}

	@Override
	public void delete(int id) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "DELETE FROM bienlouable WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Garage> findAll() throws DAOException {
		List<Garage> all_garage = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT * FROM bienlouable WHERE type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, TypeLogement.GARAGE.getValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String numero_fiscal = rs.getString("numero_fiscal");
				String complement_d_adresse = rs.getString("complement_adresse");
				BatimentDAO batDAO = new BatimentDAO();
				Batiment bat = batDAO.readId(rs.getInt("idBat"));
				String ville = bat.getVille();
				String adresse = bat.getAdresse();
				Garage garage = new Garage(numero_fiscal,ville,adresse,complement_d_adresse);
				all_garage.add(garage);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return all_garage;
	}

}
