package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Diagnostic;
import classes.Garage;
import classes.Logement;
import enumeration.TypeLogement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogementDAO implements DAO.LogementDAO {

	public void create(Logement appart,TypeLogement typeLogement) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String requete = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, garage_assoc,idBat) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setString(1, appart.getNumeroFiscal());
			pstmt.setString(2, appart.getComplementAdresse());
			pstmt.setInt(3, typeLogement.getValue());
			pstmt.setInt(4, appart.getNbPiece());
			pstmt.setDouble(5, appart.getSurface());
			BatimentDAO bat = new BatimentDAO();
			pstmt.setNull(6, java.sql.Types.INTEGER); // si on veut ajouter un garagon on utilisera
														// ajouterUnGarageAuLogement par la suite
			pstmt.setInt(7, bat.getIdBat(appart.getVille(), appart.getAdresse()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Logement read(int id) throws DAOException {
		Logement l = null;
		try {
			Connection cn = ConnectionDB.getInstance();
			String requete = "SELECT numero_fiscal, complement_adresse, type_logement, Nombre_pieces, Surface, garage_assoc,IdBat FROM bienlouable WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String num_fisc = rs.getString("numero_fiscal");
				String compl = rs.getString("complement_adresse");
				Integer type = rs.getInt("type_logement");
				Integer nb_pieces = rs.getInt("Nombre_pieces");
				Double surface = rs.getDouble("Surface");
				Integer garage = rs.getInt("garage_assoc");
				Integer id_bat = rs.getInt("idBat");
				String ville = new BatimentDAO().readId(id_bat).getVille();
				String adresse = new BatimentDAO().readId(id_bat).getAdresse();
				List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(id);
				Integer type_logement = rs.getInt("type_logement");
				l = new Logement(nb_pieces, surface, num_fisc, ville, adresse, compl, diags,TypeLogement.fromInt(type_logement));
			}
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return l;
	}

	@Override
	public void delete(int id) throws DAOException {
		try {
			Connection cn = ConnectionDB.getInstance();
			String requete = "DELETE FROM bienlouable WHERE id = ?";
			PreparedStatement pstmt = cn.prepareStatement(requete);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Logement> findAll() throws DAOException {
		List<Logement> lApparts = new ArrayList<>();
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT id FROM bienlouable WHERE type_logement = ? OR type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, TypeLogement.APPARTEMENT.getValue());
			pstmt.setInt(2, TypeLogement.MAISON.getValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				Logement l = read(id);
				lApparts.add(l);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return lApparts;
	}

	@Override
	public void lierUnGarageAuBienLouable(Logement logement, Garage garage,TypeLogement typeLogement) throws DAOException {
		Integer idGarage;
		Integer idBat;
		try{
			Connection cn = ConnectionDB.getInstance();
			idGarage = new GarageDAO().getIdGarage(garage.getNumeroFiscal(),TypeLogement.GARAGE_PAS_ASSOCIE);
			String query = "UPDATE bienlouable SET garage_assoc = ? WHERE numero_fiscal = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setInt(1, idGarage);
			pstmt.setString(2, logement.getNumeroFiscal());
			pstmt.setInt(3, typeLogement.getValue());
			pstmt.executeUpdate();
			pstmt.close();
			new GarageDAO().updateTypeGarage(idGarage,TypeLogement.GARAGE_PAS_ASSOCIE,TypeLogement.GARAGE_ASSOCIE);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer getId(String num_fiscal,TypeLogement typeLogement) throws DAOException {
		Integer id;
		try {
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, num_fiscal);
			pstmt.setInt(2, typeLogement.getValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			} else {
				id = null;
			}
			pstmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return id;
	}

	public Integer getGarageAssocie(Logement logement,TypeLogement typeLogement){
		Integer idGarage;
		try{
			Connection cn = ConnectionDB.getInstance();
			String query = "SELECT garage_assoc FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
			PreparedStatement pstmt = cn.prepareStatement(query);
			pstmt.setString(1, logement.getNumeroFiscal());
			pstmt.setInt(2, typeLogement.getValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				idGarage = rs.getInt("garage_assoc");
			} else {
				idGarage = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return idGarage;
	}


}
