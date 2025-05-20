package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Locataire;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class LocataireDAOTest {

    private LocataireDAO locataireDAO;
    private Locataire locataire;

    @Before
    public void setUp() throws SQLException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        locataireDAO = new LocataireDAO();
        locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        locataireDAO.addLocataire(locataire);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testAddLocataire() throws SQLException {
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrenom("Doe", "John", "0606060606");
        assertEquals("Doe", locataireRecupere.getNom());
        assertEquals("John", locataireRecupere.getPrenom());
        assertEquals("0606060606", locataireRecupere.getTelephone());
        assertEquals("ee.ee@ee.ee", locataireRecupere.getMail());
        assertEquals(Date.valueOf("2020-01-01"), locataireRecupere.getDateArrive());
        assertEquals("M", locataireRecupere.getGenre());
    }

    @Test
    public void testGetAllLocataires() throws SQLException {
        Locataire locataire2 = new Locataire("Smith", "Jane", "Lyon", "1992-02-02", "0707070707", "jj.jj@jj.jj", Date.valueOf("2021-01-01"), "F");
        locataireDAO.addLocataire(locataire2);

        List<Locataire> locataires = locataireDAO.getAllLocataire();
        assertTrue(locataires.stream().anyMatch(l -> l.getNom().equals("Doe") && l.getPrenom().equals("John")));
        assertTrue(locataires.stream().anyMatch(l -> l.getNom().equals("Smith") && l.getPrenom().equals("Jane")));
    }

    @Test
    public void testgetLocataireByNomPrenom() throws SQLException {
        Locataire locataireRecupere = locataireDAO.getLocataireByNomPrenom("Doe", "John", "0606060606");
        assertNotNull(locataireRecupere);
        assertEquals("Doe", locataireRecupere.getNom());
        assertEquals("John", locataireRecupere.getPrenom());
        assertEquals("0606060606", locataireRecupere.getTelephone());
        assertEquals("ee.ee@ee.ee", locataireRecupere.getMail());
        assertEquals(Date.valueOf("2020-01-01"), locataireRecupere.getDateArrive());
        assertEquals("M", locataireRecupere.getGenre());
    }

    @Test
    public void testGetLocFromId() throws SQLException {
        Locataire locataireRecupere = locataireDAO.getLocFromId(locataireDAO.getId(locataire));
        assertNotNull(locataireRecupere);
        assertEquals("Doe", locataireRecupere.getNom());
        assertEquals("John", locataireRecupere.getPrenom());
        assertEquals("0606060606", locataireRecupere.getTelephone());
        assertEquals("ee.ee@ee.ee", locataireRecupere.getMail());
        assertEquals(Date.valueOf("2020-01-01"), locataireRecupere.getDateArrive());
        assertEquals("M", locataireRecupere.getGenre());

        Locataire locataireInexistant = locataireDAO.getLocFromId(-1);
        assertNull(locataireInexistant);
    }

    @Test
    public void testGetBauxLocataire() throws DAOException, SQLException {
        Locataire locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        locataireDAO.addLocataire(locataire);
        int idLocataire = locataireDAO.getId(locataire);

        Batiment batiment4 = new Batiment("123456789108", "Paris", "123 Rue de la Paix", "31000");
        new BatimentDAO().create(batiment4);

        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        new BienLouableDAO().create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        new BailDAO().create(bail);
        int idBail = new BailDAO().getId(bail);

        new LouerDAO().create(locataire, bail, 100);

        List<Integer> bauxIds = locataireDAO.getBauxLocataire(idLocataire);

        assertNotNull(bauxIds);
        assertEquals(1, bauxIds.size());
        assertEquals(idBail, bauxIds.get(0).intValue());
    }
}