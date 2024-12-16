package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Garage;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BienLouableDAOTest {
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private GarageDAO garageDAO;
    private BailDAO bailDAO;
    private BienLouable bienLouable;
    private Bail bail;

    @Before
    public void setUp() throws SQLException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        bienLouableDAO = new BienLouableDAO();
        batimentDAO = new BatimentDAO();
        garageDAO = new GarageDAO();
        bailDAO = new BailDAO();

        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        bail = new Bail(true, "101010101010", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));

        bailDAO.create(bail);
    }

    @After
    public void tearDown() throws SQLException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
    }

    @Test
    public void testCreate() throws SQLException, DAOException {
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testReadFisc() throws SQLException, DAOException {
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {
        Garage garage = new Garage("101010101010", "Paris ", "123 Rue de la Paix","garage 1");
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
    }


    @Test
    @Ignore
    public void testDelete() throws SQLException, DAOException {
        Integer id = bienLouableDAO.getId("101010101010");
        List<Integer> idBeaux = bailDAO.getIDBeaux("101010101010");
        for (Integer idBail : idBeaux) {
            bailDAO.deleteBail(idBail);
        }
        bienLouableDAO.delete(id);
        assertNull(bienLouableDAO.readFisc("101010101010"));
    }

    @Test
    public void testReadId() throws SQLException, DAOException {
        Integer id = bienLouableDAO.getId("101010101010");
        BienLouable bienLouableRecupere = bienLouableDAO.readId(id);
        assertNotNull(bienLouableRecupere);
        assertEquals("101010101010", bienLouableRecupere.getNumero_fiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplement_adresse());
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        BienLouable bienLouable2 = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(),null);
        bienLouableDAO.create(bienLouable2, TypeLogement.APPARTEMENT, 3, 80.0);

        List<BienLouable> bienLouables = bienLouableDAO.findAll();
        assertEquals(2, bienLouables.size());
    }


    @Test
    public void testLierUnGarageAuBienLouable() throws SQLException, DAOException {
        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1");
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
        assertEquals(garageDAO.getIdGarage("G12345678910"), bienLouableRecupere.getIdgarage());
    }
    @Test
    public void testGetBailFromBien() throws DAOException {
        Bail bailRécupéré = bienLouableDAO.getBailFromBien(bienLouable);
        assertNotNull(bailRécupéré);
        assertEquals(bail.getFisc_bien(), bailRécupéré.getFisc_bien());

        BienLouable bienLouableInexistant = new BienLouable("999999999999", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null);
        Bail bailInexistant = bienLouableDAO.getBailFromBien(bienLouableInexistant);
        assertNull(bailInexistant);
    }

    @Test
    public void testGetAllcomplements() throws SQLException {
        Map<String, List<String>> complements = bienLouableDAO.getAllcomplements();
        assertNotNull(complements);
        assertTrue(complements.containsKey("123 Rue de la Paix"));
        assertTrue(complements.get("123 Rue de la Paix").contains("Apt 1"));
        Map<String, List<String>> complementsInexistants = bienLouableDAO.getAllcomplements();
        assertFalse(complementsInexistants.containsKey("999 Rue Imaginaire"));
    }

    @Test
    public void testGetTypeFromCompl() throws DAOException {
        Integer type = bienLouableDAO.getTypeFromCompl("Paris", "123 Rue de la Paix", "Apt 1");
        assertNotNull(type);
        assertEquals(TypeLogement.APPARTEMENT.getValue(), type.intValue());

        Integer typeInexistant = bienLouableDAO.getTypeFromCompl("Paris", "999 Rue Imaginaire", "Apt 99");
        assertNull(typeInexistant);
    }

    @Test
    public void testGetNbPieceFromCompl() throws DAOException {
        Integer nbPieces = bienLouableDAO.getNbPieceFromCompl("Paris", "123 Rue de la Paix", "Apt 1");
        assertNotNull(nbPieces);
        assertEquals(3, nbPieces.intValue());
        Integer nbPiecesInexistant = bienLouableDAO.getNbPieceFromCompl("Paris", "999 Rue Imaginaire", "Apt 99");
        assertNull(nbPiecesInexistant);
    }

    @Test
    public void testGetSurfaceFromCompl() throws DAOException {
        Double surface = bienLouableDAO.getSurfaceFromCompl("Paris", "123 Rue de la Paix", "Apt 1");
        assertNotNull(surface);
        assertEquals(75.0, surface, 0.0);
        Double surfaceInexistante = bienLouableDAO.getSurfaceFromCompl("Paris", "999 Rue Imaginaire", "Apt 99");
        assertNull(surfaceInexistante);
    }

    @Test
    public void testGetFiscFromCompl() throws DAOException {
        String numeroFiscal = bienLouableDAO.getFiscFromCompl("Paris", "123 Rue de la Paix", "Apt 1");
       
        assertNotNull(numeroFiscal);
        assertEquals("101010101010", numeroFiscal);
        String numeroFiscalInexistant = bienLouableDAO.getFiscFromCompl("Paris", "999 Rue Imaginaire", "Apt 99");
        assertNull(numeroFiscalInexistant);
    }
}