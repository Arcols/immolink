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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        // Create and insert a BienLouable
        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);

        locataireDAO = new LocataireDAO();
        locataire = new Locataire("Doe", "John", "0606060606", "ee.ee@ee.ee", java.sql.Date.valueOf("2020-01-01"), "M");
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
            // Si RuntimeException est levée, le test passe
            assertTrue(e instanceof RuntimeException);
        }
    }
}
