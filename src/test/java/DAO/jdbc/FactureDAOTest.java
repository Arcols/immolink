package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Facture;
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

public class FactureDAOTest {

    private FactureDAO factureDAO;
    private ChargeDAO chargeDAO;
    private BailDAO bailDAO;
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private Connection cn;
    private int idBail;

    @Before
    public void setUp() throws SQLException, DAOException {
        cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        factureDAO = new FactureDAO();
        chargeDAO = new ChargeDAO();
        bailDAO = new BailDAO();
        bienLouableDAO = new BienLouableDAO();
        batimentDAO = new BatimentDAO();

        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);

        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, bienLouable.getTypeLogement(), 3, 75.0);

        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);
        idBail = bailDAO.getId(bail);

        chargeDAO.create("Electricity", idBail);
        chargeDAO.create("Water", idBail);

        int idChargeElectricity = chargeDAO.getId("Electricity", idBail);
        int idChargeWater = chargeDAO.getId("Water", idBail);

        Facture facture1 = new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0);
        Facture facture2 = new Facture("F654321", "Water", Date.valueOf("2023-11-01"), 75.0);
        factureDAO.create(facture1, idChargeElectricity);
        factureDAO.create(facture2, idChargeWater);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws DAOException {
        chargeDAO.create("Gas", idBail);
        int idChargeGas = chargeDAO.getId("Gas", idBail);
        Facture facture = new Facture("F789012", "Gas", Date.valueOf("2023-12-01"), 100.0);
        factureDAO.create(facture, chargeDAO.getId("Gas", idBail));

        List<Facture> factures = factureDAO.getAllByAnnee(Date.valueOf("2023-01-01"), idChargeGas);
        assertNotNull(factures);
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F789012")));
    }

    @Test
    public void testGetAllByAnnee() throws DAOException {
        int idChargeElectricity = chargeDAO.getId("Electricity", idBail);
        List<Facture> factures = factureDAO.getAllByAnnee(Date.valueOf("2023-01-01"), idChargeElectricity);
        assertNotNull(factures);
        assertEquals(1, factures.size());
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F123456")));
    }

    @Test
    public void testGetAll() throws DAOException {
        chargeDAO.create("Internet", idBail);
        int idChargeInternet = chargeDAO.getId("Internet", idBail);
        Facture facture1 = new Facture("F111111", "Internet", Date.valueOf("2023-09-01"), 50.0);
        Facture facture2 = new Facture("F222222", "Internet", Date.valueOf("2023-10-01"), 60.0);
        factureDAO.create(facture1, idChargeInternet);
        factureDAO.create(facture2, idChargeInternet);

        List<Facture> factures = factureDAO.getAll(idChargeInternet);

        assertNotNull(factures);
        assertEquals(2, factures.size());
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F111111")));
        assertTrue(factures.stream().anyMatch(f -> f.getNumero().equals("F222222")));
    }
}