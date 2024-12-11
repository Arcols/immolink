package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DevisDAOTest {
    private DevisDAO devisDAO;
    private Devis devis;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        devisDAO = new DevisDAO();

        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        BatimentDAO batimentDAO = new BatimentDAO();
        batimentDAO.create(batiment);

        BienLouableDAO bienLouableDAO = new BienLouableDAO();
        BienLouable bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        devis = new Devis("123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "101010101010");
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {

        Devis devisRecupere = devisDAO.read("123456789012");
        assertEquals("123456789012", devisRecupere.getNumDevis());
        assertEquals("Renovation", devisRecupere.getNature());
        assertEquals(1000.0f, devisRecupere.getMontantDevis(), 0.0f);
    }

    @Test
    public void testRead() throws SQLException, DAOException {

        Devis devisRecupere = devisDAO.read("123456789012");
        assertEquals("123456789012", devisRecupere.getNumDevis());
        assertEquals("Renovation", devisRecupere.getNature());
        assertEquals(1000.0f, devisRecupere.getMontantDevis(), 0.0f);
    }

    @Test
    public void testGetId() throws SQLException, DAOException {
        Integer id = devisDAO.getId(devis);
        assertNotNull(id);
    }
}