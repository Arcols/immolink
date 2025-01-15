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
import classes.Garage;
import enumeration.TypeLogement;

public class GarageDAO implements DAO.GarageDAO {

	@Override
	public void create(Garage garage) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String requete = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, idBat, garage_assoc) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setString(1, garage.getNumeroFiscal());
			pstmt.setString(2, garage.getComplementAdresse());
			pstmt.setInt(3, garage.getTypeLogement().getValue());
			pstmt.setString(4, null);
			pstmt.setString(5, null);
			pstmt.setString(7, null);
			BatimentDAO bat = new BatimentDAO();
			pstmt.setInt(6, bat.getIdBat(garage.getVille(), garage.getAdresse()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
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
				Integer type_logement = rs.getInt("type_logement");
				Garage garage = new Garage(numero_fiscal,ville,adresse,complement_d_adresse,TypeLogement.fromInt(type_logement));
				return garage;
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

	@Override
	public Integer getIdGarage(String numero_fiscal, TypeLogement typeGarage) throws DAOException {
		Integer idGarage = -1;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, numero_fiscal);
			pstmt.setInt(2, typeGarage.getValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				idGarage = rs.getInt("id");
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return idGarage;
	}

	@Override
	public void delete(int id,TypeLogement typeLogement) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "DELETE FROM bienlouable WHERE id = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, typeLogement.getValue());
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateTypeGarage(int id, TypeLogement typeActuel, TypeLogement typeApres) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "UPDATE bienlouable SET type_logement = ? WHERE id = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, typeApres.getValue());
			pstmt.setInt(2, id);
			pstmt.setInt(3, typeActuel.getValue());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

        @Override
	public List<Garage> findAll() throws DAOException {
		List<Garage> all_garage = new ArrayList<>();
		all_garage.addAll(findAllGarageAssoc());
		all_garage.addAll(findAllGaragePasAssoc());
		return all_garage;
	}

	@Override
	public List<Garage> findAllGaragePasAssoc() throws DAOException {
		List<Garage> garageAssoc = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT * FROM bienlouable WHERE type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, TypeLogement.GARAGE_PAS_ASSOCIE.getValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String numero_fiscal = rs.getString("numero_fiscal");
				String complement_d_adresse = rs.getString("complement_adresse");
				BatimentDAO batDAO = new BatimentDAO();
				Batiment bat = batDAO.readId(rs.getInt("idBat"));
				String ville = bat.getVille();
				String adresse = bat.getAdresse();
				Garage garage = new Garage(numero_fiscal,ville,adresse,complement_d_adresse,TypeLogement.GARAGE_PAS_ASSOCIE);
				garageAssoc.add(garage);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return garageAssoc;
	}

	@Override
	public List<Garage> findAllGarageAssoc() throws DAOException {
		List<Garage> garageAssoc = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT * FROM bienlouable WHERE type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, TypeLogement.GARAGE_ASSOCIE.getValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String numero_fiscal = rs.getString("numero_fiscal");
				String complement_d_adresse = rs.getString("complement_adresse");
				BatimentDAO batDAO = new BatimentDAO();
				Batiment bat = batDAO.readId(rs.getInt("idBat"));
				String ville = bat.getVille();
				String adresse = bat.getAdresse();
				Garage garage = new Garage(numero_fiscal,ville,adresse,complement_d_adresse,TypeLogement.GARAGE_ASSOCIE);
				garageAssoc.add(garage);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return garageAssoc;
	}

	@Override
	public Integer readIdGarageFromBien(Integer idBien) throws DAOException {
		Integer idGarage = -1;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT garage_assoc FROM bienlouable WHERE numero_fiscal = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, new BienLouableDAO().readId(idBien).getNumeroFiscal());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				idGarage = rs.getInt("garage_assoc");
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return idGarage;
	}

}
