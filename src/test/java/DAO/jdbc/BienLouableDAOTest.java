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
    private ConnectionDB db;
    private Connection cn;
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private GarageDAO garageDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        db = ConnectionDB.getInstance();
        cn = db.getConnection();
        bienLouableDAO = new BienLouableDAO();
        batimentDAO = new BatimentDAO();
        garageDAO = new GarageDAO();

        // Create a temporary building
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
    }

    @After
    public void tearDown() throws SQLException {
        cn.close();
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

        bienLouableDAO.ajouterUnGarageAuBienLouable(bienLouable, new Garage("101010101010", "Ville ", "adresse","complément"));
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
    public void testFindAll() throws SQLException, DAOException {
        BienLouable bienLouable1 = new BienLouable("123456789101", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        // bienLouable2 = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable1, TypeLogement.APPARTEMENT, 3, 75.0);
        //bienLouableDAO.create(bienLouable2, TypeLogement.APPARTEMENT, 3, 80.0);

        List<BienLouable> bienLouables = bienLouableDAO.findAll();
        assertEquals(2, bienLouables.size());
    }
}