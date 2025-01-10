package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Facture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class FactureDAOTest {

    private FactureDAO factureDAO;
    private Connection cn;

    @Before
    public void setUp() throws SQLException {
        cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        factureDAO = new FactureDAO();
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws DAOException {
        Facture facture = new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0);
        factureDAO.create(facture, 1);

        List<Facture> factures = factureDAO.getAllByAnnee(Date.valueOf("2023-01-01"), 1);
        assertNotNull(factures);
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F123456")));
    }

    @Test
    public void testGetAllByAnnee() throws DAOException {
        Facture facture1 = new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0);
        Facture facture2 = new Facture("F654321", "Water", Date.valueOf("2023-11-01"), 75.0);
        factureDAO.create(facture1, 1);
        factureDAO.create(facture2, 1);

        List<Facture> factures = factureDAO.getAllByAnnee(Date.valueOf("2023-01-01"), 1);
        assertNotNull(factures);
        assertEquals(2, factures.size());
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F123456")));
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F654321")));
    }
}