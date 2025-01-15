package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.Garage;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class GarageDAOTest {
    private GarageDAO garageDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        Garage garage = new Garage("G12345678999", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO = new GarageDAO();
        garageDAO.create(garage);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        Integer id = garageDAO.getIdGarage("G12345678999",TypeLogement.GARAGE_PAS_ASSOCIE);
        assertNotNull(id);
    }

    @Test
    public void testRead() throws SQLException, DAOException {
        Integer id = garageDAO.getIdGarage("G12345678999",TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garageRecupere = garageDAO.read(id);
        assertEquals("G12345678999", garageRecupere.getNumeroFiscal());
        assertEquals("Garage 1", garageRecupere.getComplementAdresse());
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        Integer id = garageDAO.getIdGarage("G12345678999",TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.delete(id,TypeLogement.GARAGE_PAS_ASSOCIE);
        assertNull(garageDAO.read(id));
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789102", "Paris", "124 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        Garage garage2 = new Garage("G12456789399", "Paris", "124 Rue de la Paix", "Garage 2",TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage3 = new Garage("G12456789389", "Paris", "124 Rue de la Paix", "Garage 3",TypeLogement.GARAGE_ASSOCIE);
        garageDAO.create(garage2);
        garageDAO.create(garage3);

        List<Garage> garages = garageDAO.findAll();
        assertEquals(3, garages.size());
    }
}