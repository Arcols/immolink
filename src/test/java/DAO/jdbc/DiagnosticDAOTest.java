package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import enumeration.TypeLogement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiagnosticDAOTest {
    private DiagnosticDAO diagnosticDAO;
    private Path tempFilePath;
    private BatimentDAO batimentDAO;
    private BienLouableDAO bienLouableDAO;

    @Before
    public void setUp() throws SQLException, IOException, DAOException {
        Connection cn = ConnectionDB.getInstance();
        cn.setAutoCommit(false);
        diagnosticDAO = new DiagnosticDAO();
        batimentDAO = new BatimentDAO();

        Batiment batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        batimentDAO.create(batiment);

        BienLouable bienLouable = new BienLouable("123456789101", "Paris", "123 Rue de la Paix", "Apt 1", new ArrayList<>(),null,TypeLogement.APPARTEMENT);
        bienLouableDAO = new BienLouableDAO();
        bienLouableDAO.create(bienLouable, TypeLogement.APPARTEMENT, 3, 100.0);

        tempFilePath = Files.createTempFile("test", ".pdf");
    }

    @After
    public void tearDown() throws SQLException, IOException {
        ConnectionDB.rollback();
        ConnectionDB.setAutoCommit(true);
        ConnectionDB.destroy();
        Files.deleteIfExists(tempFilePath);
    }

    @Test
    public void testCreate() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789101");

        Diagnostic diagnosticRecupere = diagnosticDAO.read("123456789101", "D123");
        assertEquals("D123", diagnosticRecupere.getReference());
        assertEquals(tempFilePath.toString(), diagnosticRecupere.getPdfPath());
        assertEquals(Date.valueOf("2025-01-01"), diagnosticRecupere.getDateInvalidite());
    }

    @Test
    public void testRead() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789101");

        Diagnostic diagnosticRecupere = diagnosticDAO.read("123456789101", "D123");
        assertEquals("D123", diagnosticRecupere.getReference());
        assertEquals(tempFilePath.toString(), diagnosticRecupere.getPdfPath());
        assertEquals(Date.valueOf("2025-01-01"), diagnosticRecupere.getDateInvalidite());
    }

    @Test
    public void testUpdatePath() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789101");

        Path newTempFilePath = Files.createTempFile("new_test", ".pdf");
        diagnosticDAO.updatePath(diagnostic, "123456789101", newTempFilePath.toString());
        Diagnostic diagnosticRecupere = diagnosticDAO.read("123456789101", "D123");
        assertEquals(newTempFilePath.toString(), diagnosticRecupere.getPdfPath());
        Files.deleteIfExists(newTempFilePath); // Clean up the new temporary file
    }

    @Test
    public void testUpdateDate() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789101");

        diagnosticDAO.updateDate(diagnostic, "123456789101", Date.valueOf("2026-01-01"));
        Diagnostic diagnosticRecupere = diagnosticDAO.read("123456789101", "D123");
        assertEquals(Date.valueOf("2026-01-01"), diagnosticRecupere.getDateInvalidite());
    }

    @Test
    public void testDelete() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        diagnosticDAO.create(diagnostic, "123456789101");

        diagnosticDAO.delete("123456789101", "D123");
        assertNull(diagnosticDAO.read("123456789101", "D123"));
    }

    @Test
    public void testReadAllDiag() throws SQLException, DAOException, IOException {
        Diagnostic diagnostic1 = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2025-01-01"));
        Diagnostic diagnostic2 = new Diagnostic("D124", tempFilePath.toString(), Date.valueOf("2026-01-01"));
        diagnosticDAO.create(diagnostic1, "123456789101");
        diagnosticDAO.create(diagnostic2, "123456789101");

        List<Diagnostic> diagnostics = diagnosticDAO.readAllDiag(bienLouableDAO.getId("123456789101"));
        assertEquals(2, diagnostics.size());
        assertTrue(diagnostics.stream().anyMatch(d -> d.getReference().equals("D123")));
        assertTrue(diagnostics.stream().anyMatch(d -> d.getReference().equals("D124")));
    }

    @Test
    public void testReadDiagPerimes() throws SQLException, DAOException, IOException {
        Diagnostic expiredDiagnostic = new Diagnostic("D123", tempFilePath.toString(), Date.valueOf("2022-01-01"));
        diagnosticDAO.create(expiredDiagnostic, "123456789101");

        Diagnostic validDiagnostic = new Diagnostic("D124", tempFilePath.toString(), Date.valueOf("2030-01-01"));
        diagnosticDAO.create(validDiagnostic, "123456789101");

        List<String> expiredDiagnostics = diagnosticDAO.readDiagPerimes();

        assertNotNull(expiredDiagnostics);
        assertTrue(expiredDiagnostics.stream().anyMatch(d -> d.contains("D123")));
        assertFalse(expiredDiagnostics.stream().anyMatch(d -> d.contains("D124")));
    }
}