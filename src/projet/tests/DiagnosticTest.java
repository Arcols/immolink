package projet.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import projet.classes.*;


import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

public class DiagnosticTest {

    private File tempFile;

    @Before
    public void setUp() throws IOException {
        // Crée un fichier temporaire avant chaque test
        // Ce PDF sera juste un fichier texte avec l'extension pdf
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());
    }

    @Test
    public void testConstructeurEtChargementFichierEnOctets() throws IOException {
        // Création d'une instance de Diagnostic
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        // Vérifie que les données chargées sont correctes
        assertArrayEquals("Test PDF Data".getBytes(), diagnostic.getPdfData());
    }

    @Test
    public void testGetReference() throws IOException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());
        assertEquals("RéfTest", diagnostic.getReference());
    }

    @Test
    public void testOuvrirPdf() throws IOException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());

        try {
            // Appelle la méthode pour ouvrir le PDF
            diagnostic.ouvrirPdf();
            // Vérifie simplement qu'aucune exception n'est levée
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
            // Le comportement attendu est une exception
            assertTrue(e.getMessage().contains("chemin_invalide"));
        }
    }
    @Test
    public void testConstructeurAvecDatePeremption() throws IOException {
        LocalDate datePeremption = LocalDate.of(2025, 12, 31);
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath(), datePeremption);

        // Vérifie que la date de péremption est correctement définie
        assertEquals(datePeremption, diagnostic.getPeremptionDiagnostic());
    }

    @Test
    public void testConstructeurSansDatePeremption() throws IOException {
        Diagnostic diagnostic = new Diagnostic("RéfTest", tempFile.getAbsolutePath());

        // Vérifie que la date de péremption est null lorsqu'elle n'est pas fournie
        assertNull(diagnostic.getPeremptionDiagnostic());
    }
}
