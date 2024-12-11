package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BienLouableDAOTest {
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private GarageDAO garageDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        bienLouableDAO = new BienLouableDAO();
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
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testReadFisc() throws SQLException, DAOException {
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        Garage garage = new Garage("101010101010", "Paris ", "123 Rue de la Paix","garage 1");
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        Integer id = bienLouableDAO.getId("101010101010");
        bienLouableDAO.delete(id);
        assertNull(bienLouableDAO.readFisc("101010101010"));
    }

    @Test
    public void testReadId() throws SQLException, DAOException {
        // Create a BienLouable
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        // Retrieve the ID of the created BienLouable
        Integer id = bienLouableDAO.getId("101010101010");

        // Read the BienLouable by ID
        BienLouable bienLouableRecupere = bienLouableDAO.readId(id);

        // Verify the BienLouable details
        assertNotNull(bienLouableRecupere);
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        BienLouable bienLouable1 = new BienLouable("123456789101", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        BienLouable bienLouable2 = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable1, TypeLogement.APPARTEMENT, 3, 75.0);
        bienLouableDAO.create(bienLouable2, TypeLogement.APPARTEMENT, 3, 80.0);

        List<BienLouable> bienLouables = bienLouableDAO.findAll();
        assertEquals(2, bienLouables.size());
    }


    @Test
    public void testLierUnGarageAuBienLouable() throws SQLException, DAOException {
        // Create a BienLouable
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        // Create a Garage
        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1");
        garageDAO.create(garage);

        // Link the Garage to the BienLouable
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);

        // Retrieve the BienLouable and check if the garage is linked
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
        assertEquals(garageDAO.getIdGarage("G12345678910"), bienLouableRecupere.getIdgarage());
    }
}