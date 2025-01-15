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

        bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, bienLouable.getTypeLogement(), 3, 75.0);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreateWithSoldeDeCompteTrue() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        int id = bailDAO.getId(bail);
        assertNotEquals(0, id);

        Bail createdBail = bailDAO.getBailFromId(id);
        assertTrue(createdBail.isSoldeDeCompte());
        assertEquals(1000.0, createdBail.getLoyer(), 0.0);
    }

    @Test
    public void testCreateWithSoldeDeCompteFalse() throws SQLException, DAOException {
        Bail bail = new Bail(false, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"), 200.0, 15, Date.valueOf("2024-01-01"));
        bailDAO.create(bail);

        int id = bailDAO.getId(bail);
        assertNotEquals(0, id);

        Bail createdBail = bailDAO.getBailFromId(id);
        assertFalse(createdBail.isSoldeDeCompte());
        assertEquals(1500.0, createdBail.getLoyer(), 0.0);
    }

    @Test
    public void testCreateRuntimeException() throws DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        try {
            bailDAO.create(bail);
            fail("Aucune exception levée, mais une exception était attendue.");
        } catch (RuntimeException e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetAllLoyer() throws SQLException, DAOException {
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        Bail bail2 = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"), 200.0, 15, Date.valueOf("2024-01-01"));
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        double totalLoyer = bailDAO.getAllLoyer();
        assertEquals(2500.0, totalLoyer, 0.0);
    }

    @Test
    public void testGetId() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        int id = bailDAO.getId(bail);
        assertNotEquals(0, id);
    }

    @Test
    public void testGetAllBaux() throws SQLException, DAOException {
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        Bail bail2 = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"), 200.0, 15, Date.valueOf("2024-01-01"));
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        List<Bail> baux = bailDAO.getAllBaux();
        assertEquals(2, baux.size());
        assertTrue(baux.contains(bail1));
        assertTrue(baux.contains(bail2));
    }

    @Test
    public void testGetIdBienLouable() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);
        int idBienLouable = bailDAO.getIdBienLouable(idBail);

        assertNotEquals(0, idBienLouable);
        int idBienRead = bienLouableDAO.getId(bienLouable.getNumeroFiscal());
        assertEquals(idBienRead, idBienLouable);
    }

    @Test
    public void testGetBailFromId() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);
        Bail baiReadl = bailDAO.getBailFromId(idBail);
        assertEquals(bail, baiReadl);
    }

    @Test
    public void testDelete() throws DAOException {
        LouerDAO louerDAO = new LouerDAO();
        LocataireDAO locataireDAO = new LocataireDAO();

        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        Locataire locataire1 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "john.doe@example.com", Date.valueOf("2021-01-01"), "M");
        Locataire locataire2 = new Locataire("Smith", "Jane", "Lyon", "1992-02-02", "0707070707", "jane.smith@example.com", Date.valueOf("2021-01-01"), "F");locataireDAO.addLocataire(locataire1);
        locataireDAO.addLocataire(locataire2);
        louerDAO.create(locataire1, bail, 50);
        louerDAO.create(locataire2, bail, 50);

        List<Integer> idLocs = louerDAO.getIdLoc(idBail);
        assertNotNull(idLocs);
        assertEquals(2, idLocs.size());

        bailDAO.delete(idBail);

        Bail deletedBail = bailDAO.getBailFromId(idBail);
        assertNull(deletedBail);

        idLocs = louerDAO.getIdLoc(idBail);
        assertTrue(idLocs.isEmpty());
    }

    @Test
    public void testUpdateLoyer() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        int idBail = bailDAO.getId(bail);

        double newLoyer = 1200.0;
        bailDAO.updateLoyer(idBail, newLoyer);

        Bail updatedBail = bailDAO.getBailFromId(idBail);

        assertEquals(newLoyer, updatedBail.getLoyer(), 0.0);
    }

    @Test
    public void testGetIDBeaux() throws SQLException, DAOException {
        Batiment batimentTest = new Batiment("987654321987", "Paris", "124 Rue de la Paix", "31000");
        new BatimentDAO().create(batimentTest);

        BienLouable bienLouableTest = new BienLouable("BL3456789102", "Paris", "124 Rue de la Paix", "31000", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        new BienLouableDAO().create(bienLouableTest, TypeLogement.APPARTEMENT, 3, 75.0);

        Bail bail1 = new Bail(true, "BL3456789102", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        Bail bail2 = new Bail(true, "BL3456789102", 1500.0, 300.0, 600.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-11-30"), 200.0, 15, Date.valueOf("2024-01-01"));

        BailDAO bailDAO = new BailDAO();
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        List<Integer> idBaux = bailDAO.getIDBeaux(new BienLouableDAO().getId("BL3456789102"));

        assertEquals(2, idBaux.size());
        assertTrue(idBaux.contains(bailDAO.getId(bail1)));
        assertTrue(idBaux.contains(bailDAO.getId(bail2)));
    }

    @Test
    public void testGetBailFromBienEtDate() throws DAOException {
        Date dateDebut = Date.valueOf("2024-01-01");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        Bail retrievedBail = bailDAO.getBailFromBienEtDate(bienLouable, dateDebut);
        assertNotNull(retrievedBail);
        assertEquals(bail, retrievedBail);
    }

    @Test
    public void testUpdateICC() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        double newICC = 175.0;
        bailDAO.updateICC(idBail, newICC);

        Bail updatedBail = bailDAO.getBailFromId(idBail);
        assertEquals(newICC, updatedBail.getIcc(), 0.0);
    }

    @Test
    public void testUpdateDateDernierAnniversaire() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        int idBail = bailDAO.getId(bail);

        Date newDateDernierAnniversaire = Date.valueOf("2023-12-01");
        bailDAO.updateDateDernierAnniversaire(idBail, newDateDernierAnniversaire);

        Bail updatedBail = bailDAO.getBailFromId(idBail);
        assertEquals(newDateDernierAnniversaire, updatedBail.getDernierAnniversaire());
    }

    @Test
    public void testGetBauxNouvelICC() throws DAOException {
        Bail bailOldICC = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2022-01-01"), Date.valueOf("2022-12-31"), 150.0, 10, Date.valueOf("2021-01-01"));
        bailDAO.create(bailOldICC);

        Bail bailRecentICC = new Bail(true, "BL3456789101", 1500.0, 300.0, 600.0, Date.valueOf("2023-01-01"), Date.valueOf("2023-12-31"), 200.0, 15, Date.valueOf("2022-01-01"));
        bailDAO.create(bailRecentICC);

        List<String> notifications = bailDAO.getBauxNouvelICC();

        assertNotNull(notifications);
        assertTrue(notifications.stream().anyMatch(n -> n.contains("2022-01-01")));
        assertFalse(notifications.stream().anyMatch(n -> n.contains("2023-01-01")));
    }

    @Test
    public void testUpdateProvisionPourCharge() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        int idBail = bailDAO.getId(bail);

        double newProvisionPourCharge = 250.0;
        bailDAO.updateProvisionPourCharge(idBail, newProvisionPourCharge);

        Bail updatedBail = bailDAO.getBailFromId(idBail);

        assertEquals(newProvisionPourCharge, updatedBail.getCharge(), 0.0);
    }

    @Test
    public void testUpdateIndexeEau() throws SQLException, DAOException {
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        int idBail = bailDAO.getId(bail);

        int newIndexEau = 20;
        bailDAO.updateIndexeEau(idBail, newIndexEau);

        Bail updatedBail = bailDAO.getBailFromId(idBail);

        assertEquals(newIndexEau, (int) updatedBail.getIndexEau());
    }

}