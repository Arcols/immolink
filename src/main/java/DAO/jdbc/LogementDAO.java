package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Logement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LogementDAO implements DAO.LogementDAO {

	@Override
	public void create(Logement appart) throws DAOException {
		ConnectionDB cn;
		try {
			cn = new ConnectionDB();
			String requete = "INSERT INTO Logement bienlouable VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.getConnection().prepareStatement(requete);
			pstmt.setString(1, appart.getNumero_fiscal());
			pstmt.setString(2, appart.getComplement_adresse());
			pstmt.setInt(3, 0);
			pstmt.setInt(4, appart.getNbPiece());
			pstmt.setDouble(5, appart.getSurface());
			pstmt.setInt(6, 1);

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
		// TODO Auto-generated method stub
		return null;
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
