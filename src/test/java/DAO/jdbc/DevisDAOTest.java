package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
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

public class DevisDAOTest {
    private DevisDAO devisDAO;
    private BienLouableDAO bienLouableDAO;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        devisDAO = new DevisDAO();
        bienLouableDAO = new BienLouableDAO();
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);

        BienLouable bienLouable = new BienLouable("123456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
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
        Devis devis = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "123456789101", TypeLogement.APPARTEMENT);

        Devis devisRecupere = devisDAO.read("123456789012");
        assertEquals("123456789012", devisRecupere.getNumDevis());
        assertEquals("Renovation", devisRecupere.getNature());
        assertEquals(1000.0f, devisRecupere.getMontantDevis(), 0.0f);
    }

    @Test
    public void testRead() throws SQLException, DAOException {
        Devis devis = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "123456789101", TypeLogement.APPARTEMENT);

        Devis devisRecupere = devisDAO.read("123456789012");
        assertEquals("123456789012", devisRecupere.getNumDevis());
        assertEquals("Renovation", devisRecupere.getNature());
        assertEquals(1000.0f, devisRecupere.getMontantDevis(), 0.0f);
    }

    @Test
    public void testGetId() throws SQLException, DAOException {
        Devis devis = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "123456789101", TypeLogement.APPARTEMENT);

        Integer id = devisDAO.getId(devis);
        assertNotNull(id);
    }

    @Test
    public void testGetAllDevisFromABien() throws SQLException, DAOException {
        Devis devis1 = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        Devis devis2 = new Devis("123456789013", "F123456789013", 1500.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Paix", "Entreprise B");
        devisDAO.create(devis1, "123456789101", TypeLogement.APPARTEMENT);
        devisDAO.create(devis2, "123456789101", TypeLogement.APPARTEMENT);

        List<Devis> devisList = devisDAO.getAllDevisFromABien("123456789101", TypeLogement.APPARTEMENT);
        assertEquals(2, devisList.size());
    }

    @Test
    public void testGetMontantTotalDevis() throws SQLException, DAOException {
        Devis devis1 = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        Devis devis2 = new Devis("123456789013", "F123456789013", 1500.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Paix", "Entreprise B");
        devisDAO.create(devis1, "123456789101", TypeLogement.APPARTEMENT);
        devisDAO.create(devis2, "123456789101", TypeLogement.APPARTEMENT);

        double montantTotalDevis = devisDAO.getMontantTotalDevis("123456789101", TypeLogement.APPARTEMENT);
        assertEquals(2500.0, montantTotalDevis, 0.0);
    }

    @Test
    public void testGetMontantTotalTravaux() throws SQLException, DAOException {
        Devis devis1 = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        Devis devis2 = new Devis("123456789013", "F123456789013", 1500.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Paix", "Entreprise B");
        devisDAO.create(devis1, "123456789101", TypeLogement.APPARTEMENT);
        devisDAO.create(devis2, "123456789101", TypeLogement.APPARTEMENT);

        double montantTotalTravaux = devisDAO.getMontantTotalTravaux("123456789101", TypeLogement.APPARTEMENT);
        assertEquals(500.0, montantTotalTravaux, 0.0);
    }

    @Test
    public void testGetAllDevisFromABienAndDate() throws DAOException, SQLException {

        Devis devis1 = new Devis("123456789012", "F123456789012", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        Devis devis2 = new Devis("123456789013", "F123456789013", 1500.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Paix", "Entreprise B");
        devisDAO.create(devis1, "123456789101", TypeLogement.APPARTEMENT);
        devisDAO.create(devis2, "123456789101", TypeLogement.APPARTEMENT);

        List<Devis> devisList = devisDAO.getAllDevisFromABienAndDate("123456789101", TypeLogement.APPARTEMENT, Date.valueOf("2024-01-01"));

        assertNotNull(devisList);
        assertEquals(2, devisList.size());
    }
}