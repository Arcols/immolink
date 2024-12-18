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

import static org.junit.Assert.*;

public class BailDAOTest {

    private BailDAO bailDAO;
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private BienLouable bienLouable;

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
        bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null);
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
    public void testCreateRuntimeException() throws DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);
        try {
            bailDAO.create(bail);
            fail("Aucune exception levée, mais une exception était attendue.");
        } catch (RuntimeException e) {
            // Si RuntimeException est levée, le test passe
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetAllLoyer() throws SQLException, DAOException {
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Bail bail2 = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"));
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

    @Test
    public void testGetAllBaux() throws SQLException, DAOException {
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Bail bail2 = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"));
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        List<Bail> baux = bailDAO.getAllBaux();
        assertEquals(2, baux.size());
        assertTrue(baux.contains(bail1));
        assertTrue(baux.contains(bail2));
    }

    @Test
    public void testGetIdBienLouable() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);
        int idBienLouable = bailDAO.getIdBienLouable(idBail);

        assertNotEquals(0, idBienLouable);
        int idBienRead = bienLouableDAO.getId(bienLouable.getNumero_fiscal());
        assertEquals(idBienRead, idBienLouable);
    }

    @Test
    public void testGetBailFromId() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);
        Bail baiReadl = bailDAO.getBailFromId(idBail);
        assertEquals(bail, baiReadl);
    }

    @Test
    public void testDelete() throws DAOException {
        LouerDAO louerDAO = new LouerDAO();
        LocataireDAO locataireDAO = new LocataireDAO();

        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        Locataire locataire1 = new Locataire("Doe", "John", "0606060606", "john.doe@example.com", Date.valueOf("2021-01-01"), "M");
        Locataire locataire2 = new Locataire("Smith", "Jane", "0707070707", "jane.smith@example.com", Date.valueOf("2021-01-01"), "F");
        locataireDAO.addLocataire(locataire1);
        locataireDAO.addLocataire(locataire2);
        louerDAO.create(locataire1, bail, 50);
        louerDAO.create(locataire2, bail, 50);

        // Verify the association exists
        List<Integer> idLocs = louerDAO.getIdLoc(idBail);
        assertNotNull(idLocs);
        assertEquals(2, idLocs.size());

        // Delete the bail
        bailDAO.delete(idBail);

        // Verify the bail and associations are removed
        Bail deletedBail = bailDAO.getBailFromId(idBail);
        assertNull(deletedBail);

        idLocs = louerDAO.getIdLoc(idBail);
        assertTrue(idLocs.isEmpty());
    }

}