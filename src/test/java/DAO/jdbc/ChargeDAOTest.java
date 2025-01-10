package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Charge;
import classes.Facture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChargeDAOTest {

    private ChargeDAO chargeDAO;
    private FactureDAO factureDAO;
    private Connection cn;

    @Before
    public void setUp() throws SQLException, DAOException {
        cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        chargeDAO = new ChargeDAO();
        factureDAO = new FactureDAO();

        // Create initial data
        Facture facture1 = new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0);
        Facture facture2 = new Facture("F654321", "Water", Date.valueOf("2023-11-01"), 75.0);
        factureDAO.create(facture1, 1);
        factureDAO.create(facture2, 1);
        chargeDAO.create("Electricity", 1);
        chargeDAO.create("Water", 1);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws DAOException {
        int idCharge = chargeDAO.getId("Electricity", 1);
        assertTrue(idCharge > 0);
    }

    @Test
    public void testGetMontant() throws DAOException {
        double montant = chargeDAO.getMontant(Date.valueOf("2023-01-01"), 1);
        assertEquals(225.0, montant, 0.0);
    }

    @Test
    public void testGetId() throws DAOException {
        int idCharge = chargeDAO.getId("Water", 1);
        assertTrue(idCharge > 0);
    }
}