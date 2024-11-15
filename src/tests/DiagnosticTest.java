package tests;

import static org.junit.Assert.*;
import org.junit.Test;

<<<<<<< Updated upstream:src/tests/DiagnosticTest.java
import classes.Diagnostic;
=======
import classes.*;
>>>>>>> Stashed changes:src/projet/tests/DiagnosticTest.java

import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DiagnosticTest {

    private File tempFile;
    private File tempFile2;

    @Before
    public void setUp() throws IOException {
        // Crée un fichier temporaire avant chaque test
        // Ce PDF sera juste un fichier texte avec l'extension pdf
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        // Deuxième fichier pour les tests de mise à jour
        tempFile2 = File.createTempFile("testFile2", ".pdf");
        Files.write(tempFile2.toPath(), "New PDF Data".getBytes());
    }

    @Test
    public void testGettersAndConstructeur() throws IOException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        assertEquals("RéfTest", diagnostic.getReference());
        assertArrayEquals("Test PDF Data".getBytes(), diagnostic.getPdfData());
    }

    @Test
    public void testOuvrirPdf() throws IOException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());

        try {
            diagnostic.ouvrirPdf();
        } catch (Exception e) {
            fail("La méthode ouvrirPdf a levé une exception : " + e.getMessage());
        }
    }

    @Test
    public void testChargementFichierEnOctets_CheminInvalide() {
        try {
            new Diagnostic("RéfTest", "chemin_invalide.pdf");
            fail("Une exception aurait dû être levée pour un chemin de fichier invalide.");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("chemin_invalide"));
        }
    }

    @Test
    public void testIsSameRef() throws IOException {
        Diagnostic diagnostic1 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic2 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic3 = new Diagnostic("AutreRef", tempFile.getAbsolutePath());

        assertTrue(diagnostic1.isSameRef(diagnostic2));
        assertFalse(diagnostic1.isSameRef(diagnostic3));
    }

    @Test
    public void testMiseAJourDiagnostic() throws IOException {
        Diagnostic diagnostic1 = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        Diagnostic diagnostic2 = new Diagnostic("RéfTest", tempFile2.getAbsolutePath());

        diagnostic1.miseAJourDiagnostic(diagnostic2);

        assertArrayEquals("New PDF Data".getBytes(), diagnostic1.getPdfData());
    }
}
