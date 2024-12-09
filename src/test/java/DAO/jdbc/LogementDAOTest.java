package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.Garage;
import classes.Logement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class LogementDAOTest {
    private LogementDAO logementDAO;
    private BatimentDAO batimentDAO;
    private GarageDAO garageDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        logementDAO = new LogementDAO();
        batimentDAO = new BatimentDAO();
        garageDAO = new GarageDAO();

        // Create a temporary building
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>());
        logementDAO.create(logement);
        Integer id = logementDAO.getId("101010101010");
        Logement logementRecupere = logementDAO.read(id);
        assertEquals("101010101010", logementRecupere.getNumero_fiscal());
        assertEquals("Apt 1", logementRecupere.getComplement_adresse());
    }

    @Test
    public void testRead() throws SQLException, DAOException {
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>());
        logementDAO.create(logement);

        Logement logementRecupere = logementDAO.read(logementDAO.getId("101010101010"));
        assertEquals("101010101010", logementRecupere.getNumero_fiscal());
        assertEquals("Apt 1", logementRecupere.getComplement_adresse());
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>());
        logementDAO.create(logement);

        Integer id = logementDAO.getId("101010101010");
        logementDAO.delete(id);
        assertNull(logementDAO.read(id));
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        Logement logement1 = new Logement(3, 75.0, "123456789101", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>());
        Logement logement2 = new Logement(3, 80.0, "123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>());
        logementDAO.create(logement1);
        logementDAO.create(logement2);

        List<Logement> logements = logementDAO.findAll();
        assertEquals(2, logements.size());
    }

    @Test
    public void testLierUnGarageAuBienLouable() throws SQLException, DAOException {

        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>());
        logementDAO.create(logement);

        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1");
        garageDAO.create(garage);
        logementDAO.lierUnGarageAuBienLouable(logement, garage);

        Logement logementRecupere = logementDAO.read(logementDAO.getId(logement.getNumero_fiscal()));
        Integer idGarage = logementDAO.getGarageAssocie(logementRecupere);
        assertNotNull(logementRecupere);
        assertEquals((Integer) garageDAO.getIdGarage(garage.getNumero_fiscal()),idGarage);
    }

}