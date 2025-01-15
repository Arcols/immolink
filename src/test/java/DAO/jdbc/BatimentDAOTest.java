package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BatimentDAOTest {

    private BatimentDAO batimentDAO;

    @Before
    public void setUp() throws SQLException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        batimentDAO = new BatimentDAO();
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        Batiment batimentRecupere = batimentDAO.readFisc("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Paris", batimentRecupere.getVille());
        assertEquals("123 Rue de la Paix", batimentRecupere.getAdresse());
    }

    @Test
    public void testReadFisc() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        Batiment batimentRecupere = batimentDAO.readFisc("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Paris", batimentRecupere.getVille());
        assertEquals("123 Rue de la Paix", batimentRecupere.getAdresse());
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        batiment.setVille("Lyon");
        batiment.setAdresse("456 Rue de Lyon");
        batimentDAO.update(batiment);

        Batiment batimentRecupere = batimentDAO.readFisc("123456789101");
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Lyon", batimentRecupere.getVille());
        assertEquals("456 Rue de Lyon", batimentRecupere.getAdresse());
    }

    @Test
    public void testSearchAllBatiments() throws SQLException, DAOException {
        Batiment batiment1 = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        Batiment batiment2 = new Batiment("123456789102", "Lyon", "456 Rue de Lyon","31000");
        batimentDAO.create(batiment1);
        batimentDAO.create(batiment2);

        Map<String, List<String>> batiments = batimentDAO.searchAllBatiments();
        assertEquals(2, batiments.size());
        assertTrue(batiments.get("Paris").contains("123 Rue de la Paix"));
        assertTrue(batiments.get("Lyon").contains("456 Rue de Lyon"));
    }

    @Test
    public void testGetIdBat() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);
        int id = batimentDAO.getIdBat("Paris", "123 Rue de la Paix");
        assertTrue(id > 0);
    }

    @Test
    public void testReadId() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        int id = batimentDAO.getIdBat("Paris", "123 Rue de la Paix");
        Batiment batimentRecupere = batimentDAO.readId(id);

        assertNotNull(batimentRecupere);
        assertEquals("123456789101", batimentRecupere.getNumeroFiscal());
        assertEquals("Paris", batimentRecupere.getVille());
        assertEquals("123 Rue de la Paix", batimentRecupere.getAdresse());
        assertEquals("31000", batimentRecupere.getCodePostal());

    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);

        BienLouable bienLouable1 = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Appartement 1", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        BienLouable bienLouable2 = new BienLouable("123456789103", "Paris", "123 Rue de la Paix", "Appartement 2", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        new BienLouableDAO().create(bienLouable1, TypeLogement.APPARTEMENT, 3, 105.0);
        new BienLouableDAO().create(bienLouable2, TypeLogement.APPARTEMENT, 3, 105.0);

        batimentDAO.delete("123456789101");

        assertNull(batimentDAO.readFisc("123456789101"));

        assertNull(new BienLouableDAO().readFisc("123456789102"));
        assertNull(new BienLouableDAO().readFisc("123456789103"));
    }

    @Test
    public void testSearchAllBatimentsWithCompl() throws SQLException, DAOException {
        Batiment batiment1 = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        Batiment batiment2 = new Batiment("123456789102", "Lyon", "456 Rue de Lyon", "69000");
        batimentDAO.create(batiment1);
        batimentDAO.create(batiment2);

        BienLouable bienLouable1 = new BienLouable("123456789103", "Paris", "123 Rue de la Paix", "Appartement 1", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        BienLouable bienLouable2 = new BienLouable("123456789104", "Lyon", "456 Rue de Lyon", "Appartement 2", new ArrayList<>(), null,TypeLogement.APPARTEMENT);
        new BienLouableDAO().create(bienLouable1, TypeLogement.APPARTEMENT, 3, 105.0);
        new BienLouableDAO().create(bienLouable2, TypeLogement.APPARTEMENT, 3, 105.0);

        Map<String, List<String>> batiments = batimentDAO.searchAllBatimentsWithCompl();

        assertEquals(2, batiments.size());
        assertTrue(batiments.get("Paris").contains("123 Rue de la Paix"));
        assertTrue(batiments.get("Lyon").contains("456 Rue de Lyon"));
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        Batiment batiment1 = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        Batiment batiment2 = new Batiment("123456789102", "Lyon", "456 Rue de Lyon", "69000");
        batimentDAO.create(batiment1);
        batimentDAO.create(batiment2);

        List<Batiment> batiments = batimentDAO.findAll();

        assertNotNull(batiments);
        assertEquals(2, batiments.size());
        assertTrue(batiments.stream().anyMatch(b -> b.getNumeroFiscal().equals("123456789101")));
        assertTrue(batiments.stream().anyMatch(b -> b.getNumeroFiscal().equals("123456789102")));
    }

    @Test
    public void testGetBienTypeBat() throws DAOException {
        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        int idBat = batimentDAO.getIdBat("Paris", "123 Rue de la Paix");

        BienLouable bien1 = new BienLouable("BL1234567891", "Paris", "123 Rue de la Paix", "Appartement 1", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        BienLouable bien2 = new BienLouable("BL1234567892", "Paris", "123 Rue de la Paix", "Appartement 2", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        new BienLouableDAO().create(bien1, TypeLogement.APPARTEMENT, 3, 75.0);
        new BienLouableDAO().create(bien2, TypeLogement.APPARTEMENT, 3, 75.0);

        List<Integer> bienIds = new BatimentDAO().getBienTypeBat(idBat, TypeLogement.APPARTEMENT);

        assertNotNull(bienIds);
        assertEquals(2, bienIds.size());
    }
}