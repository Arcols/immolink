package classes;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import enumeration.TypeLogement;
import org.junit.Before;
import org.junit.Test;

public class LogementTest {
    private File tempFile;
    private File tempFile2;
    private Diagnostic diagnostic1;
    private Diagnostic diagnostic2;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        tempFile2 = File.createTempFile("testFile2", ".pdf");
        Files.write(tempFile2.toPath(), "New PDF Data".getBytes());

        diagnostic1 = new Diagnostic("RefDiag1", tempFile.getAbsolutePath());
        diagnostic2 = new Diagnostic("RefDiag2", tempFile2.getAbsolutePath());
    }

    @Test
    public void testLogementCreationSansGarage() throws IllegalArgumentException, SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        Logement logement = new Logement(2, 25.0, "123456789101", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics, TypeLogement.MAISON);

        assertEquals(2, logement.getNbPiece());
        assertEquals(25.0, logement.getSurface(), 0.01);
    }

    @Test
    public void testLogementCreationAvecGarage() throws SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        Logement logement = new Logement(3, 45.0, "123456789101", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics,TypeLogement.MAISON);

        assertEquals(3, logement.getNbPiece());
        assertEquals(45.0, logement.getSurface(), 0.01);
        }

    @Test(expected = IllegalArgumentException.class)
    public void testLogementSurfaceMinimale() throws IllegalArgumentException, SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        new Logement(1, 8.5, "123456789101", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics,TypeLogement.MAISON);
    }
}