package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BailDAOTest {

    private BailDAO bailDAO;
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    
    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        bailDAO = new BailDAO();
        bienLouableDAO = new BienLouableDAO();
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        // Create and insert a BienLouable
        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);

        int id = bailDAO.getId(bail);
        assertNotEquals(0, id);
    }

    @Test
    public void testGetAllLoyer() throws SQLException, DAOException {
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Bail bail2 = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"));
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        double totalLoyer = bailDAO.getAllLoyer();
        assertEquals(2500.0, totalLoyer, 0.0);
    }

    @Test
    public void testGetId() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);

        int id = bailDAO.getId(bail);
        assertNotEquals(0, id);
    }
}