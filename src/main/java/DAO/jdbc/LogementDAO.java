package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.Diagnostic;
import classes.Logement;
import enumeration.TypeLogement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.reflect.Array.setInt;

public class LogementDAO implements DAO.LogementDAO {

	@Override
	public void create(Logement appart) throws DAOException {
		ConnectionDB cn;
		try {
			cn = new ConnectionDB();
			String requete = "INSERT INTO bienlouable(numero_fiscal,complement_adresse,type_logement,nombre_pieces,surface,garage_assoc,idBat) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.getConnection().prepareStatement(requete);
			pstmt.setString(1, appart.getNumero_fiscal());
			pstmt.setString(2, appart.getComplement_adresse());
			pstmt.setInt(3, TypeLogement.APPARTEMENT.ordinal());
			pstmt.setInt(4, appart.getNbPiece());
			pstmt.setDouble(5, appart.getSurface());
			pstmt.setNull(6, java.sql.Types.INTEGER); // garage associé ?
			BatimentDAO bat = new BatimentDAO();
			pstmt.setInt(7, bat.getIdBat(appart.getVille(), appart.getAdresse()));
			pstmt.executeUpdate();
			pstmt.close();
			cn.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Logement read(int id) throws DAOException {
		ConnectionDB cn;
		Logement l = null;
		try {
			cn = new ConnectionDB();
			String requete = "SELECT numero_fical, complement_adresse, type_logement, Nombre_pieces, Surface, garage_assoc FROM bienlouable WHERE id = ?";
			PreparedStatement pstmt = cn.getConnection().prepareStatement(requete);
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String num_fisc = rs.getString("numero_ficscal");
				String compl = rs.getString("complement_adresse");
				Integer type = rs.getInt("type_logement");
				Integer nb_pieces = rs.getInt("Nombre_pieces");
				Double surface = rs.getDouble("Surface");
				Integer garage = rs.getInt("garage_assoc");
				String ville = new BatimentDAO().readFisc(num_fisc).getVille();
				String adresse =  new BatimentDAO().readFisc(num_fisc).getAdresse();
				List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(num_fisc);
				Boolean haveG = (garage == 1);
				l = new Logement(nb_pieces,surface,num_fisc,ville,adresse,compl,diags,haveG);
			}
			pstmt.close();
			cn.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
    }

	@Override
	public void update(Logement appart) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Logement> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
