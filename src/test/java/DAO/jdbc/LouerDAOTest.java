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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LouerDAOTest {

    private BailDAO bailDAO;
    private BienLouableDAO bienLouableDAO;
    private LocataireDAO locataireDAO;
    private Locataire locataire;
    private Bail bail;
    private LouerDAO louerDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        bailDAO = new BailDAO();
        bienLouableDAO = new BienLouableDAO();
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);

        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 1.5, 100, Date.valueOf("2024-01-01"));
        bailDAO.create(bail);

        locataireDAO = new LocataireDAO();
        locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        locataireDAO.addLocataire(locataire);

        louerDAO = new LouerDAO();
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        List<Integer> idLocs = louerDAO.getIdLoc(bailDAO.getId(bail));
        assertNotNull(idLocs);
        assertEquals(1, idLocs.size());
    }

    @Test
    public void testGetIdLoc() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        List<Integer> idLocs = louerDAO.getIdLoc(bailDAO.getId(bail));
        assertNotNull(idLocs);
        assertEquals(1, idLocs.size());
        assertEquals(locataireDAO.getId(locataire), idLocs.get(0).intValue());
    }

    @Test
    public void testCreateRuntimeException() throws DAOException {
        louerDAO.create(locataire, bail, 1);
        try {
            louerDAO.create(locataire, bail, 100);
            fail("Aucune exception levée, mais une exception était attendue.");
        } catch (RuntimeException e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetAllLocatairesDesBeaux() throws DAOException {
        Locataire locataire2 = new Locataire("Smith", "Jane", "Lyon", "1992-02-02", "0707070707", "jj.jj@jj.jj", Date.valueOf("2021-01-01"), "F");
        locataireDAO.addLocataire(locataire2);
        louerDAO.create(locataire2, bail, 50);
        louerDAO.create(locataire, bail, 50);

        Map<Integer, List<Integer>> locatairesDunBail = louerDAO.getAllLocatairesDesBeaux();
        int bailId = bailDAO.getId(bail);

        assertNotNull(locatairesDunBail);
        assertTrue(locatairesDunBail.containsKey(bailId));
        List<Integer> locataires = locatairesDunBail.get(bailId);
        assertNotNull(locataires);
        assertEquals(2, locataires.size());
        assertTrue(locataires.contains(locataireDAO.getId(locataire)));
        assertTrue(locataires.contains(locataireDAO.getId(locataire2)));
    }

    @Test
    public void testDelete() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        List<Integer> idLocs = louerDAO.getIdLoc(bailDAO.getId(bail));
        assertNotNull(idLocs);
        assertEquals(1, idLocs.size());

        louerDAO.delete(bailDAO.getId(bail), locataireDAO.getId(locataire));

        idLocs = louerDAO.getIdLoc(bailDAO.getId(bail));
        assertNotNull(idLocs);
        assertTrue(idLocs.isEmpty());
    }

    @Test
    public void testUpdateQuotite() throws SQLException, DAOException {
        BienLouable bienLouable = new BienLouable("BL3456789102", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        Bail bail = new Bail(true, "BL3456789102", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 1.5, 100, Date.valueOf("2024-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        Locataire locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "john.doe@example.com", Date.valueOf("2021-01-01"), "M");
        locataireDAO.addLocataire(locataire);
        int idLocataire = locataireDAO.getId(locataire);

        louerDAO.create(locataire, bail, 50);

        int newQuotite = 75;
        louerDAO.updateQuotite(idBail, idLocataire, newQuotite);

        int updatedQuotite = louerDAO.getQuotité(idBail, idLocataire);

        assertEquals(newQuotite, updatedQuotite);
    }

    @Test
    public void testGetQuotite() throws SQLException, DAOException {
        BienLouable bienLouable = new BienLouable("BL3456789102", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        Bail bail = new Bail(true, "BL3456789102", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 1.5, 100, Date.valueOf("2024-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        Locataire locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "john.doe@example.com", Date.valueOf("2021-01-01"), "M");
        locataireDAO.addLocataire(locataire);
        int idLocataire = locataireDAO.getId(locataire);

        louerDAO.create(locataire, bail, 50);

        int quotite = louerDAO.getQuotité(idBail, idLocataire);

        assertEquals(50, quotite);
    }

    @Test
    public void testLocInBail() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        int idLocataire = locataireDAO.getId(locataire);
        int idBail = bailDAO.getId(bail);

        boolean isLocInBail = louerDAO.locInBail(idLocataire, idBail);
        assertTrue(isLocInBail);

        Locataire locataire2 = new Locataire("Smith", "Jane", "Lyon", "1992-02-02", "0707070707", "jj.jj@jj.jj", Date.valueOf("2021-01-01"), "F");
        locataireDAO.addLocataire(locataire2);
        int idLocataire2 = locataireDAO.getId(locataire2);

        boolean isLocInBail2 = louerDAO.locInBail(idLocataire2, idBail);
        assertFalse(isLocInBail2);
    }

    @Test
    public void testGetStatut() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        boolean statut = louerDAO.getStatut(locataireDAO.getId(locataire));
        assertNotNull(statut);
        assertFalse(statut);
    }

    @Test
    public void testGetStatutBail() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        boolean statutBail = louerDAO.getStatutBail(bailDAO.getId(bail));
        assertNotNull(statutBail);
        assertFalse(statutBail);
    }

    @Test
    public void testGetLoyerPaye() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        Boolean loyerPaye = louerDAO.getLoyerPaye(locataireDAO.getId(locataire), bailDAO.getId(bail));
        assertNotNull(loyerPaye);
        assertFalse(loyerPaye);
    }

    @Test
    public void testUpdatePaiement() throws DAOException {
        louerDAO.create(locataire, bail, 100);
        Date newDate = Date.valueOf(java.time.LocalDate.now());        louerDAO.updatePaiement(bailDAO.getId(bail), locataireDAO.getId(locataire), newDate);
        Boolean loyerPaye = louerDAO.getLoyerPaye(locataireDAO.getId(locataire), bailDAO.getId(bail));
        assertNotNull(loyerPaye);
        assertTrue(loyerPaye);
    }
}
