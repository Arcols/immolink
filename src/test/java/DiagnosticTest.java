import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import DAO.db.ConnectionDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import classes.*;

public class DiagnosticTest {

    private File tempFile;
    private File tempFile2;
    private ConnectionDB db;
    private Connection connection;

    @Before
    public void setUp() throws IOException, SQLException {
        // Create a temporary file before each test
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        // Second file for update tests
        tempFile2 = File.createTempFile("testFile2", ".pdf");
        Files.write(tempFile2.toPath(), "New PDF Data".getBytes());

        // Initialize the connection and start a transaction
        db = new ConnectionDB();
        connection = db.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("BEGIN;");
        statement.close();
    }

    @After
    public void tearDown() throws SQLException {
        // Rollback all changes
        Statement statement = connection.createStatement();
        statement.execute("ROLLBACK;");
        statement.close();
        db.closeConnection();
    }

    @Test
    public void testGettersAndConstructeur() throws IOException, SQLException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        assertEquals("RéfTest", diagnostic.getReference());
        assertEquals(tempFile.getAbsolutePath(), diagnostic.getPdfPath());
    }

    @Test
    public void testOuvrirPdf() throws IOException, SQLException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());

        try {
            diagnostic.ouvrirPdf();
        } catch (Exception e) {
            fail("La méthode ouvrirPdf a levé une exception : " + e.getMessage());
        }
    }

    @Test
    public void testChargementFichierEnOctets_CheminInvalide() throws SQLException {
        try {
            new Diagnostic("RéfTest", "chemin_invalide.pdf");
            fail("Une exception aurait dû être levée pour un chemin de fichier invalide.");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Invalid file path"));
        }
    }

    @Test
    public void testIsSameRef() throws IOException, SQLException {
        Diagnostic diagnostic1 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic2 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic3 = new Diagnostic("AutreRef", tempFile.getAbsolutePath());

        assertTrue(diagnostic1.isSameRef(diagnostic2));
        assertFalse(diagnostic1.isSameRef(diagnostic3));
    }

    @Test
    public void testMiseAJourDiagnostic() throws IOException, SQLException {
        Diagnostic diagnostic1 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic2 = new Diagnostic("RéfTest", tempFile2.getAbsolutePath());

        diagnostic1.miseAJourDiagnostic(diagnostic2);

        assertEquals(tempFile2.getAbsolutePath(), diagnostic1.getPdfPath());
    }

    @Test
    public void testConstructeurAvecDateInvalidite() throws IOException, SQLException {
        // Create an invalidity date for today
        Date dateInvalidite = new Date(System.currentTimeMillis());
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath(), dateInvalidite);

        assertEquals("RéfTest", diagnostic.getReference());
        assertEquals(tempFile.getAbsolutePath(), diagnostic.getPdfPath());
        assertEquals(dateInvalidite, diagnostic.getDateInvalidite());
    }

    @Test
    public void testEstExpireSansDateInvalidite() throws IOException, SQLException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        assertFalse(diagnostic.estExpire()); // Diagnostic without invalidity date cannot be expired
    }

    @Test
    public void testEstExpireAvecDateInvaliditeNonExpiree() throws IOException, SQLException {
        // Create a future invalidity date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5); // 5 days in the future
        Date dateInvalidite = new Date(calendar.getTimeInMillis());

        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath(), dateInvalidite);
        assertFalse(diagnostic.estExpire()); // Should not be expired
    }

    @Test
    public void testEstExpireAvecDateInvaliditeExpiree() throws IOException, SQLException {
        // Create a past invalidity date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5); // 5 days in the past
        Date dateInvalidite = new Date(calendar.getTimeInMillis());

        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath(), dateInvalidite);
        assertTrue(diagnostic.estExpire()); // Should be expired
    }
}