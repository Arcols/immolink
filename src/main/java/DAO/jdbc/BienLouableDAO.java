package DAO.jdbc;

import java.sql.SQLException;
import java.util.List;

import DAO.DAOException;
import classes.BienLouable;
import enumeration.*;

public class BienLouableDAO implements DAO.BienLouableDAO {

	@Override
	public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface, boolean haveGarage)
			throws DAOException, IllegalArgumentException, SQLException{
		// TODO Auto-generated method stub

	}

	@Override
	public BienLouable read(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BienLouable bien) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BienLouable> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
