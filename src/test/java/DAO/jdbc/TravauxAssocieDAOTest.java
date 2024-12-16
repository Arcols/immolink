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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TravauxAssocieDAOTest {

    private TravauxAssocieDAO travauxAssocieDAO;
    private String numFiscal;
    private Devis devis;
    private DevisDAO devisDAO;
    private BienLouableDAO bienLouableDAO;
    private BienLouable bienLouable;
    private Devis devis1;

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
        bienLouable = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Complément", new ArrayList<>(), null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        devis = new Devis("123456789015", 1000.0f, "Renovation", 200.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-06-01"), "TypeA", "123 Rue de la Paix", "Entreprise A");
        devisDAO.create(devis, "123456789102", TypeLogement.APPARTEMENT);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testFindAll() throws DAOException {
        String numFiscal = bienLouable.getNumero_fiscal();
        List<Integer> devisIds = travauxAssocieDAO.findAll(numFiscal, TypeLogement.APPARTEMENT);
        assertNotNull(devisIds);
        assertEquals(1, devisIds.size());
    }
}