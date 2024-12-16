package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegimeDAOTest {

    private RegimeDAO regimeDAO;

    @Before
    public void setUp() throws SQLException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        regimeDAO = new RegimeDAO();
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testGetValeur() throws DAOException {
        regimeDAO.updateValeur(15.5F);
        Float valeur = regimeDAO.getValeur();
        assertNotNull(valeur);
    }

    @Test
    public void testUpdateValeur() throws DAOException {
        Float nouvelleValeur = 15.5f;
        regimeDAO.updateValeur(nouvelleValeur);
        Float updatedValeur = regimeDAO.getValeur();
        assertEquals(nouvelleValeur, updatedValeur);
    }
}