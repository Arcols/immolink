package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import classes.Garage;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TravauxAssocieDAOTest {

    private TravauxAssocieDAO travauxAssocieDAO;
    private DevisDAO devisDAO;
    private BienLouableDAO bienLouableDAO;
    private BienLouable bienLouable;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);

        travauxAssocieDAO = new TravauxAssocieDAO();
        devisDAO = new DevisDAO();
        bienLouableDAO = new BienLouableDAO();
        BatimentDAO batimentDAO = new BatimentDAO();
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        bienLouable = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Complément", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        Devis devis = new Devis("123456789015", "F123456789015", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "123456789102", TypeLogement.APPARTEMENT);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testFindAllForAppartement() throws DAOException {
        String numFiscal = bienLouable.getNumeroFiscal();
        List<Integer> devisIds = travauxAssocieDAO.findAll(numFiscal, TypeLogement.APPARTEMENT);
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllForBatiment() throws DAOException {
        Batiment batiment = new Batiment("123456789103", "Lyon", "456 Rue de la Liberté", "69000");
        BatimentDAO batimentDAO = new BatimentDAO();
        batimentDAO.create(batiment);
        Devis devisBatiment = new Devis("123456789016", "F123456789016", 2000.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Liberté", "Entreprise B");
        devisDAO.create(devisBatiment, "123456789103", TypeLogement.BATIMENT);

        List<Integer> devisIds = travauxAssocieDAO.findAll("123456789103", TypeLogement.BATIMENT);
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllForGarage() throws DAOException {
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.create(new Garage("123456789104", "Paris", "123 Rue de la Paix", "31000", TypeLogement.GARAGE_PAS_ASSOCIE));
        Devis devisGarage = new Devis("123456789017", "F123456789017", 1500.0f, "Réparation", 250.0f, Date.valueOf("2024-03-01"), Date.valueOf("2024-08-01"), "TypeC", "789 Rue de la Mer", "Entreprise C");
        devisDAO.create(devisGarage, "123456789104", TypeLogement.GARAGE_PAS_ASSOCIE);

        List<Integer> devisIds = travauxAssocieDAO.findAll("123456789104", TypeLogement.GARAGE_PAS_ASSOCIE);
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllForMaison() throws DAOException {
        BienLouable maison = new BienLouable("123456789108", "Paris", "123 Rue de la Paix", "Complément", new ArrayList<>(), null, TypeLogement.MAISON);
        bienLouableDAO.create(maison, TypeLogement.MAISON, 3, 75.0);
        Devis devis = new Devis("123456789015", "F123456789015", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, maison.getNumeroFiscal(), TypeLogement.MAISON);

        String numFiscal = maison.getNumeroFiscal();
        List<Integer> devisIds = travauxAssocieDAO.findAll(numFiscal, TypeLogement.MAISON);
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllWithDateForAppartement() throws DAOException {
        String numFiscal = bienLouable.getNumeroFiscal();
        List<Integer> devisIds = travauxAssocieDAO.findAllWithDate(numFiscal, TypeLogement.APPARTEMENT,Date.valueOf("2024-01-01"));
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllWithDateForGarage() throws DAOException {
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.create(new Garage("123456789104", "Paris", "123 Rue de la Paix", "31000", TypeLogement.GARAGE_PAS_ASSOCIE));
        Devis devisGarage = new Devis("123456789017", "F123456789017", 1500.0f, "Réparation", 250.0f, Date.valueOf("2024-03-01"), Date.valueOf("2024-08-01"), "TypeC", "789 Rue de la Mer", "Entreprise C");
        devisDAO.create(devisGarage, "123456789104", TypeLogement.GARAGE_PAS_ASSOCIE);

        List<Integer> devisIds = travauxAssocieDAO.findAllWithDate("123456789104", TypeLogement.GARAGE_PAS_ASSOCIE,Date.valueOf("2024-03-01"));
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllWithDateForBatiment() throws DAOException {
        Batiment batiment = new Batiment("123456789103", "Lyon", "456 Rue de la Liberté", "69000");
        BatimentDAO batimentDAO = new BatimentDAO();
        batimentDAO.create(batiment);
        Devis devisBatiment = new Devis("123456789016", "F123456789016", 2000.0f, "Construction", 300.0f, Date.valueOf("2024-02-01"), Date.valueOf("2024-07-01"), "TypeB", "456 Rue de la Liberté", "Entreprise B");
        devisDAO.create(devisBatiment, "123456789103", TypeLogement.BATIMENT);

        List<Integer> devisIds = travauxAssocieDAO.findAllWithDate("123456789103", TypeLogement.BATIMENT,Date.valueOf("2024-02-01"));
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }

    @Test
    public void testFindAllWithDateForMaison() throws DAOException {
        BienLouable maison = new BienLouable("123456789108", "Paris", "123 Rue de la Paix", "Complément", new ArrayList<>(), null, TypeLogement.MAISON);
        bienLouableDAO.create(maison, TypeLogement.MAISON, 3, 75.0);
        Devis devis = new Devis("123456789015", "F123456789015", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, maison.getNumeroFiscal(), TypeLogement.MAISON);

        String numFiscal = maison.getNumeroFiscal();
        List<Integer> devisIds = travauxAssocieDAO.findAllWithDate(numFiscal, TypeLogement.MAISON, Date.valueOf("2024-01-01"));
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }
}