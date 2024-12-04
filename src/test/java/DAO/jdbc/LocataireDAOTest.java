package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class LocataireDAOTest {

    private ConnectionDB db;
    private Connection cn;
    private LocataireDAO locataireDAO;
    private Locataire locataire;

    @Before
    public void setUp() throws SQLException {
        db = ConnectionDB.getInstance();
        cn = db.getConnection();
        //cn.setAutoCommit(false);
        locataireDAO = new LocataireDAO();
        locataire = new Locataire("Doe", "John", "0606060606", "ee.ee@ee.ee", java.sql.Date.valueOf("2020-01-01"), "M");
        locataireDAO.addLocataire(locataire);
    }

    @After
    public void tearDown() throws SQLException {
        //cn.rollback();
        //cn.setAutoCommit(true);
        cn.close();
    }

    @Test
    public void testAddLocataire() throws SQLException {
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0606060606");
        System.out.println(locataireRecupere);
        assertEquals("Doe", locataireRecupere.getNom());
        assertEquals("John", locataireRecupere.getPrénom());
        assertEquals("0606060606", locataireRecupere.getTéléphone());
        assertEquals("ee.ee@ee.ee", locataireRecupere.getMail());
        assertEquals(java.sql.Date.valueOf("2020-01-01"), locataireRecupere.getDateArrive());
        assertEquals("M", locataireRecupere.getGenre());
    }

    @Test
    public void testUpdateLocataireTel() throws SQLException {
        locataireDAO.updateLocataireTel(locataire, "0707070707");
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0707070707");
        assertEquals("0707070707", locataireRecupere.getTéléphone());
    }

    @Test
    public void testUpdateLocataireMail() throws SQLException {
        locataireDAO.updateLocataireMail(locataire, "new.email@example.com");
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0606060606");
        assertEquals("new.email@example.com", locataireRecupere.getMail());
    }

    @Test
    public void testUpdateLocataireGenre() throws SQLException {
        locataireDAO.updateLocataireGenre(locataire, "F");
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0606060606");
        assertEquals("F", locataireRecupere.getGenre());
    }

    @Test
    public void testDeleteLocataire() throws SQLException {
        locataireDAO.deleteLocataire(locataire);
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0606060606");
        assertNull(locataireRecupere);
    }

    @Test
    public void testGetAllLocataires() throws SQLException {
        Locataire locataire2 = new Locataire("Smith", "Jane", "0707070707", "jj.jj@jj.jj", java.sql.Date.valueOf("2021-01-01"), "F");
        locataireDAO.addLocataire(locataire2);

        List<Locataire> locataires = locataireDAO.getAllLocataire();
        assertTrue(locataires.stream().anyMatch(l -> l.getNom().equals("Doe") && l.getPrénom().equals("John")));
        assertTrue(locataires.stream().anyMatch(l -> l.getNom().equals("Smith") && l.getPrénom().equals("Jane")));
    }

    @Test
    public void testGetLocataireByNomPrénomTel() throws SQLException {
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrénomTel("Doe", "John", "0606060606");
        assertNotNull(locataireRecupere);
        assertEquals("Doe", locataireRecupere.getNom());
        assertEquals("John", locataireRecupere.getPrénom());
        assertEquals("0606060606", locataireRecupere.getTéléphone());
        assertEquals("ee.ee@ee.ee", locataireRecupere.getMail());
        assertEquals(java.sql.Date.valueOf("2020-01-01"), locataireRecupere.getDateArrive());
        assertEquals("M", locataireRecupere.getGenre());
    }

}
