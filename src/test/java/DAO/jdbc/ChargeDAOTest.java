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

import static org.junit.Assert.*;

public class ChargeDAOTest {

    private ChargeDAO chargeDAO;
    private FactureDAO factureDAO;
    private BailDAO bailDAO;
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private Connection cn;
    private int idBail;

    @Before
    public void setUp() throws SQLException, DAOException {
        cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        chargeDAO = new ChargeDAO();
        factureDAO = new FactureDAO();
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
        int idCharge = chargeDAO.getId("Electricity", idBail);
        assertTrue(idCharge > 0);
    }

    @Test
    public void testGetMontant() throws DAOException {
        double montant = chargeDAO.getMontant(Date.valueOf("2023-01-01"),chargeDAO.getId("Water", idBail));
        assertEquals(75.0, montant, 0.0);
    }

    @Test
    public void testGetId() throws DAOException {
        int idCharge = chargeDAO.getId("Water", idBail);
        assertTrue(idCharge > 0);
    }
}