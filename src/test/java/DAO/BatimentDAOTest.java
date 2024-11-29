package DAO;

import DAO.jdbc.BatimentDAO;
import DAO.db.ConnectionDB;
import classes.Batiment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BatimentDAOTest {
    private ConnectionDB connection;
    private BatimentDAO batimentDAO;

    @Before
    public void setUp() throws SQLException {
        batimentDAO = new BatimentDAO();
        connection.getConnection();
        connection.setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.closeConnection();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        batimentDAO.create(batiment);

        Batiment batimentRecupere = batimentDAO.read("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Paris", batimentRecupere.getVille());
        assertEquals("123 Rue de la Paix", batimentRecupere.getAdresse());
    }

    @Test
    public void testRead() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        batimentDAO.create(batiment);

        Batiment batimentRecupere = batimentDAO.read("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Paris", batimentRecupere.getVille());
        assertEquals("123 Rue de la Paix", batimentRecupere.getAdresse());
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        batimentDAO.create(batiment);

        batiment.setVille("Lyon");
        batiment.setAdresse("456 Rue de Lyon");
        batimentDAO.update(batiment);

        Batiment batimentRecupere = batimentDAO.read("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Lyon", batimentRecupere.getVille());
        assertEquals("456 Rue de Lyon", batimentRecupere.getAdresse());
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        batimentDAO.create(batiment);

        batimentDAO.delete("123456789101");
        assertNull(batimentDAO.read("123456789101"));
    }

    @Test
    public void testSearchAllBatiments() throws SQLException, DAOException {
        Batiment batiment1 = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        Batiment batiment2 = new Batiment("123456789102", "Lyon", "456 Rue de Lyon");
        batimentDAO.create(batiment1);
        batimentDAO.create(batiment2);

        Map<String, List<String>> batiments = batimentDAO.searchAllBatiments();
        assertEquals(2, batiments.size());
        assertTrue(batiments.get("Paris").contains("123 Rue de la Paix"));
        assertTrue(batiments.get("Lyon").contains("456 Rue de Lyon"));
    }

    @Test
    public void testGetIdBat() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        batimentDAO.create(batiment);

        int id = batimentDAO.getIdBat("Paris", "123 Rue de la Paix");
        assertTrue(id > 0);
    }
}