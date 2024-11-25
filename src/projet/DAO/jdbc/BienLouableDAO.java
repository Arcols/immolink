package projet.DAO.jdbc;

import java.sql.SQLException;
import java.util.List;

import projet.DAO.DAOException;
import projet.classes.Batiment;
import projet.classes.BienLouable;
import projet.classes.Garage;
import projet.classes.Logement;
import projet.enumeration.TypeLogement;

public class BienLouableDAO implements projet.DAO.BienLouableDAO {

	@Override
	public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface, boolean haveGarage)
			throws DAOException, IllegalArgumentException {
		switch (type) {
		case APPARTEMENT:
			Logement appart;
			try {
				appart = new Logement(nb_piece, surface, bien.getNumero_fiscal(), bien.getVille(), bien.getAdresse(),
						bien.getComplement_adresse(), bien.getDiagnostic(), haveGarage);
				LogementDAO crea_appart = new LogementDAO();
				crea_appart.create(appart);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case GARAGE:
			try {
				Garage garage = new Garage(bien.getNumero_fiscal(), bien.getVille(), bien.getAdresse(),
						bien.getComplement_adresse());
				GarageDAO crea_garage = new GarageDAO();
				crea_garage.create(garage);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case BATIMENT:
			Batiment batiment = new Batiment(bien.getNumero_fiscal(), bien.getVille(), bien.getAdresse());
			BatimentDAO crea_bat = new BatimentDAO();
			crea_bat.create(batiment);
		}
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
