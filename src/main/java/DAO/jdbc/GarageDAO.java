package DAO.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Garage;

public class GarageDAO implements DAO.GarageDAO {

	@Override
	public void create(Garage garage) throws DAOException {
		try {
			ConnectionDB cn = ConnectionDB.getInstance();
			String requete = "INSERT INTO Logement bienlouable VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmt = cn.getConnection().prepareStatement(requete);
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
			cn.closeConnection();
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
