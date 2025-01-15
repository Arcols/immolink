package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.Garage;
import classes.Logement;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), TypeLogement.APPARTEMENT);
        logementDAO.create(logement,TypeLogement.APPARTEMENT);
        Integer id = logementDAO.getId("101010101010",TypeLogement.APPARTEMENT);
        Logement logementRecupere = logementDAO.read(id);
        assertEquals("101010101010", logementRecupere.getNumeroFiscal());
        assertEquals("Apt 1", logementRecupere.getComplementAdresse());
    }

    @Test
    public void testRead() throws SQLException, DAOException {
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),TypeLogement.APPARTEMENT);
        logementDAO.create(logement,TypeLogement.APPARTEMENT);

        Logement logementRecupere = logementDAO.read(logementDAO.getId("101010101010",TypeLogement.APPARTEMENT));
        assertEquals("101010101010", logementRecupere.getNumeroFiscal());
        assertEquals("Apt 1", logementRecupere.getComplementAdresse());
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),TypeLogement.APPARTEMENT);
        logementDAO.create(logement,TypeLogement.APPARTEMENT);

        Integer id = logementDAO.getId("101010101010",TypeLogement.APPARTEMENT);
        logementDAO.delete(id);
        assertNull(logementDAO.read(id));
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        Logement logement1 = new Logement(3, 75.0, "123456789101", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),TypeLogement.APPARTEMENT);
        Logement logement2 = new Logement(3, 80.0, "123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(),TypeLogement.APPARTEMENT);
        logementDAO.create(logement1,TypeLogement.APPARTEMENT);
        logementDAO.create(logement2,TypeLogement.APPARTEMENT);

        List<Logement> logements = logementDAO.findAll();
        assertEquals(2, logements.size());
    }

    @Test
    public void testLierUnGarageAuBienLouable() throws SQLException, DAOException {

        Logement logement = new Logement(3, 75.0, "101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),TypeLogement.APPARTEMENT);
        logementDAO.create(logement,TypeLogement.APPARTEMENT);

        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1",TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.create(garage);
        logementDAO.lierUnGarageAuBienLouable(logement, garage,TypeLogement.APPARTEMENT);

        Logement logementRecupere = logementDAO.read(logementDAO.getId(logement.getNumeroFiscal(),TypeLogement.APPARTEMENT));
        Integer idGarage = logementDAO.getGarageAssocie(logementRecupere,TypeLogement.APPARTEMENT);
        assertNotNull(logementRecupere);
        assertEquals((Integer) garageDAO.getIdGarage(garage.getNumeroFiscal(),TypeLogement.GARAGE_ASSOCIE),idGarage);
    }

}