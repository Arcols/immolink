package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.*;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class BienLouableDAOTest {
    private BienLouableDAO bienLouableDAO;
    private BatimentDAO batimentDAO;
    private GarageDAO garageDAO;
    private BailDAO bailDAO;
    private BienLouable bienLouable;
    private Bail bail;
    private Connection cn;

    @Before
    public void setUp() throws SQLException, DAOException {
        cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        bienLouableDAO = new BienLouableDAO();
        batimentDAO = new BatimentDAO();
        garageDAO = new GarageDAO();
        bailDAO = new BailDAO();

        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        batimentDAO.create(batiment);
        bienLouable = new BienLouable("101010101010", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);
        bail = new Bail(true, "101010101010", 1000.0, 200.0, 500.0, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));

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
        assertEquals("101010101010", bienLouableRecupere.getNumeroFiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplementAdresse());
    }

    @Test
    public void testReadFisc() throws SQLException, DAOException {
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals("101010101010", bienLouableRecupere.getNumeroFiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplementAdresse());
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {
        Garage garage = new Garage("010101010101", "Paris", "123 Rue de la Paix", "garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
    }

    @Test
    public void testDeleteWithDevis() throws DAOException, SQLException {

        Batiment batiment = new Batiment("123456789101", "Paris", "125 Rue de la Paix", "31000");
        batimentDAO.create(batiment);

        BienLouable bienLouableWithDevis = new BienLouable("123456789104", "Paris", "125 Rue de la Paix", "Apt 4", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouableWithDevis, TypeLogement.APPARTEMENT, 3, 90.0);

        Devis devis = new Devis("123456789013","12345",1500.0f, "Renovation", 300.0f, Date.valueOf("2024-03-01"), Date.valueOf("2024-09-01"), "TypeB", "125 Rue de Paris", "EntrepriseB");
        new DevisDAO().create(devis, "123456789104", TypeLogement.APPARTEMENT);
        bienLouableWithDevis.ajouterTravaux(devis);

        Bail bailWithDevis = new Bail(true, "123456789104", 1400.0, 350.0, 750.0, Date.valueOf("2024-03-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bailWithDevis);

        Integer idWithDevis = bienLouableDAO.getId("123456789104");
        bienLouableDAO.delete(idWithDevis);

        assertNull(bienLouableDAO.readFisc("123456789104"));
        assertNull(new DevisDAO().read(devis.getNumDevis()));
        assertNull(bailDAO.getBailFromId(bailDAO.getId(bailWithDevis)));
    }

    @Test
    public void testDeleteWithoutDevis() throws DAOException, SQLException {
        Batiment batiment2 = new Batiment("123456789101", "Paris", "126 Rue de la Paix", "31000");
        batimentDAO.create(batiment2);
        BienLouable bienLouableNoDevis = new BienLouable("123456789105", "Paris", "126 Rue de la Paix", "Apt 5", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouableNoDevis, TypeLogement.APPARTEMENT, 3, 95.0);

        Bail bailNoDevis = new Bail(true, "123456789105", 1500.0, 400.0, 800.0, Date.valueOf("2024-04-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bailNoDevis);

        Integer idNoDevis = bienLouableDAO.getId("123456789105");
        bienLouableDAO.delete(idNoDevis);

        assertNull(bienLouableDAO.readFisc("123456789105"));
        assertNull(bailDAO.getBailFromId(bailDAO.getId(bailNoDevis)));
    }

    @Test
    public void testDeleteWithGarage() throws DAOException, SQLException {
        Batiment batiment3 = new Batiment("123456789101", "Paris", "127 Rue de la Paix", "31000");
        batimentDAO.create(batiment3);
        BienLouable bienLouableWithGarage = new BienLouable("123456789106", "Paris", "127 Rue de la Paix", "Apt 6", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouableWithGarage, TypeLogement.APPARTEMENT, 3, 100.0);

        Garage garage = new Garage("G12345678910", "Paris", "127 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouableWithGarage, garage);

        Bail bailWithGarage = new Bail(true, "123456789106", 1600.0, 450.0, 850.0, Date.valueOf("2024-05-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bailWithGarage);

        Integer idWithGarage = bienLouableDAO.getId("123456789106");
        bienLouableDAO.delete(idWithGarage);

        assertNull(bienLouableDAO.readFisc("123456789106"));
        assertNull(bailDAO.getBailFromId(bailDAO.getId(bailWithGarage)));
        assertNull(garageDAO.read(garageDAO.getIdGarage("G12345678910", TypeLogement.GARAGE_ASSOCIE)));
        assertNotNull(garageDAO.read(garageDAO.getIdGarage("G12345678910", TypeLogement.GARAGE_PAS_ASSOCIE)));
    }

    @Test
    public void testDeleteWithDiagnostic() throws DAOException, SQLException, IOException {
        DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
        batimentDAO = new BatimentDAO();
        Batiment batiment4 = new Batiment("123456789108", "Paris", "130 Rue de la Paix", "31000");
        batimentDAO.create(batiment4);
        BienLouable bienLouableWithDiagnostic = new BienLouable("123456789111", "Paris", "130 Rue de la Paix", "Apt 1", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO = new BienLouableDAO();
        bienLouableDAO.create(bienLouableWithDiagnostic, TypeLogement.APPARTEMENT, 3, 100.0);

        File tempFilePath = Files.createTempFile("test", ".pdf").toFile();
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789111");

        Bail bailWithDiag = new Bail(true, "123456789111", 1500.0, 400.0, 800.0, Date.valueOf("2024-04-01"), Date.valueOf("2024-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bailWithDiag);

        Integer idWithDiag = bienLouableDAO.getId("123456789111");
        bienLouableDAO.delete(idWithDiag);

        assertNull(bienLouableDAO.readFisc("123456789111"));
        assertNull(bailDAO.getBailFromId(bailDAO.getId(bailWithDiag)));
        assertNull(diagnosticDAO.read(bienLouable.getNumeroFiscal(), diagnostic.getReference()));
    }

    @Test
    public void testReadId() throws SQLException, DAOException {
        Integer id = bienLouableDAO.getId("101010101010");
        BienLouable bienLouableRecupere = bienLouableDAO.readId(id);
        assertNotNull(bienLouableRecupere);
        assertEquals("101010101010", bienLouableRecupere.getNumeroFiscal());
        assertEquals("Apt 1", bienLouableRecupere.getComplementAdresse());
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {
        BienLouable bienLouable2 = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable2, TypeLogement.APPARTEMENT, 3, 80.0);

        List<BienLouable> bienLouables = bienLouableDAO.findAll();
        assertEquals(2, bienLouables.size());
    }

    @Test
    public void testLierUnGarageAuBienLouable() throws SQLException, DAOException {
        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);
        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());
        assertEquals(garageDAO.getIdGarage("G12345678910", TypeLogement.GARAGE_ASSOCIE), bienLouableRecupere.getIdgarage());
    }

    @Test
    public void testGetBailFromBien() throws DAOException {
        Bail bailRécupéré = bienLouableDAO.getBailFromBien(bienLouable);
        assertNotNull(bailRécupéré);
        assertEquals(bail.getFiscBien(), bailRécupéré.getFiscBien());

        BienLouable bienLouableInexistant = new BienLouable("999999999999", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
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

    @Test
    public void testGetAllComplNoBail() throws SQLException, DAOException {
        BienLouable bienLouableNoBail = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Apt 2", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouableNoBail, TypeLogement.APPARTEMENT, 3, 80.0);

        Map<String, List<String>> complementsNoBail = bienLouableDAO.getAllComplBail();

        assertNotNull(complementsNoBail);
        assertTrue(complementsNoBail.containsKey("123 Rue de la Paix"));
        assertTrue(complementsNoBail.get("123 Rue de la Paix").contains("Apt 2"));
    }

    @Test
    public void testGetListeBeauxFromBien() throws DAOException {

        Bail bail1 = new Bail(true, "101010101010", 1200.0, 250.0, 600.0, Date.valueOf("2028-02-01"), Date.valueOf("2029-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        Bail bail2 = new Bail(true, "101010101010", 1300.0, 300.0, 700.0, Date.valueOf("2025-01-01"), Date.valueOf("2025-12-31"), 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail1);
        bailDAO.create(bail2);

        List<Integer> idBeaux = bienLouableDAO.getListeBeauxFromBien(bienLouable);

        assertNotNull(idBeaux);
        assertEquals(3, idBeaux.size());
        assertTrue(idBeaux.contains(bailDAO.getId(this.bail)));
        assertTrue(idBeaux.contains(bailDAO.getId(bail1)));
        assertTrue(idBeaux.contains(bailDAO.getId(bail2)));
    }

    @Test
    public void testDélierGarage() throws DAOException {

        Garage garage = new Garage("G12345678910", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouable, garage);

        BienLouable bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertNotNull(bienLouableRecupere.getIdgarage());

        bienLouableDAO.délierGarage(bienLouableDAO.getId("101010101010"));

        bienLouableRecupere = bienLouableDAO.readFisc("101010101010");
        assertEquals(Optional.ofNullable(0), Optional.ofNullable(bienLouableRecupere.getIdgarage()));
    }

    @Test
    public void testGetAllBienLouableNoGarageLink() throws DAOException, SQLException {

        batimentDAO = new BatimentDAO();
        Batiment batiment5 = new Batiment("123456789108", "Paris", "129 Rue de la Paix", "31000");
        Batiment batiment6 = new Batiment("123456789108", "Paris", "130 Rue de la Paix", "31000");
        batimentDAO.create(batiment5);
        batimentDAO.create(batiment6);

        BienLouable bienLouableNoGarage2 = new BienLouable("987654321102", "Paris", "129 Rue de la Paix", "Apt 8", new ArrayList<>(), null, TypeLogement.MAISON);
        bienLouableDAO.create(bienLouableNoGarage2, TypeLogement.MAISON, 5, 120.0);

        BienLouable bienLouableWithGarage = new BienLouable("987654321103", "Paris", "130 Rue de la Paix", "Apt 9", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouableWithGarage, TypeLogement.APPARTEMENT, 4, 95.0);

        Garage garage = new Garage("G12345678911", "Paris", "130 Rue de la Paix", "Garage 2", TypeLogement.GARAGE_PAS_ASSOCIE);
        garageDAO.create(garage);
        bienLouableDAO.lierUnGarageAuBienLouable(bienLouableWithGarage, garage);

        List<BienLouable> bienLouablesNoGarage = bienLouableDAO.getAllBienLouableNoGarageLink();

        assertNotNull(bienLouablesNoGarage);
        assertEquals(2, bienLouablesNoGarage.size());
        assertTrue(bienLouablesNoGarage.stream().anyMatch(b -> b.getNumeroFiscal().equals("101010101010")));
        assertTrue(bienLouablesNoGarage.stream().anyMatch(b -> b.getNumeroFiscal().equals("987654321102")));
        assertFalse(bienLouablesNoGarage.stream().anyMatch(b -> b.getNumeroFiscal().equals("987654321103")));
    }

    @Test
    public void testGetAllBienLouableNoGarageLinkWithHouse() throws DAOException {
        BienLouable house = new BienLouable("H12345678910", "Paris", "123 Rue de la Paix", "House 1", new ArrayList<>(), null, TypeLogement.MAISON);
        bienLouableDAO.create(house, TypeLogement.MAISON, 5, 120.0);

        List<BienLouable> bienLouablesNoGarage = bienLouableDAO.getAllBienLouableNoGarageLink();

        assertNotNull(bienLouablesNoGarage);
        assertTrue(bienLouablesNoGarage.stream().anyMatch(b -> b.getNumeroFiscal().equals("H12345678910")));
    }

    @Test
    public void testGetAllBienLouableNoGarageLinkWithAppartment() throws DAOException {
        BienLouable house = new BienLouable("H12345678910", "Paris", "123 Rue de la Paix", "House 1", new ArrayList<>(), null, TypeLogement.MAISON);
        bienLouableDAO.create(house, TypeLogement.APPARTEMENT, 5, 120.0);

        List<BienLouable> bienLouablesNoGarage = bienLouableDAO.getAllBienLouableNoGarageLink();

        assertNotNull(bienLouablesNoGarage);
        assertTrue(bienLouablesNoGarage.stream().anyMatch(b -> b.getNumeroFiscal().equals("H12345678910")));
    }

    @Test
    public void testGetBailFromBienAndDate() throws DAOException {
        BienLouable bienLouable = new BienLouable("BL3456789101", "Paris", "123 Rue de la Paix", "31000", new ArrayList<>(), null, TypeLogement.APPARTEMENT);
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 75.0);

        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin, 150.0, 10, Date.valueOf("2023-01-01"));
        bailDAO.create(bail);

        Bail retrievedBail = bienLouableDAO.getBailFromBienAndDate(bienLouable, Date.valueOf("2024-01-01"));

        assertNotNull(retrievedBail);
        assertEquals(bail, retrievedBail);
    }

}