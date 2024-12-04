package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BienLouableTest {

    private File tempFile;
    private File tempFile2;
    private Diagnostic diagnostic1;
    private Diagnostic diagnostic2;
    private BienLouable bienLouable;

    @Before
    public void setUp() throws IOException {
        // Create temporary files for diagnostics
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        tempFile2 = File.createTempFile("testFile2", ".pdf");
        Files.write(tempFile2.toPath(), "New PDF Data".getBytes());

        // Initialize diagnostics
        diagnostic1 = new Diagnostic("RefDiag1", tempFile.getAbsolutePath());
        diagnostic2 = new Diagnostic("RefDiag2", tempFile2.getAbsolutePath());

        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        // Initialize BienLouable
        bienLouable = new BienLouable("123456789101", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics,null);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("123456789101", bienLouable.getNumero_fiscal());
        assertEquals("Appartement 12B", bienLouable.getComplement_adresse());
        assertEquals("123 Rue de la Paix", bienLouable.getAdresse());
        assertEquals("Paris", bienLouable.getVille());
        assertEquals(1, bienLouable.getDiagnostic().size());
        assertEquals(diagnostic1, bienLouable.getDiagnostic().get(0));
        assertTrue(bienLouable.getTravaux().isEmpty());
    }

    @Test
    public void testAjouterTravaux() {
        Devis devis = new Devis("DEV123", 1000.0f, "Rénovation", 200.0f);
        bienLouable.ajouterTravaux(devis);
        assertEquals(1, bienLouable.getTravaux().size());
        assertEquals(devis, bienLouable.getTravaux().get(0));
    }

    @Test
    public void testModifierDiagnostic() throws IOException {
        Diagnostic updatedDiagnostic = new Diagnostic("RefDiag1", tempFile2.getAbsolutePath());
        bienLouable.modifierDiagnostic(updatedDiagnostic);

        // Verify that the diagnostic has been updated with the new PDF data
        assertEquals(tempFile2.getAbsolutePath(), bienLouable.getDiagnostic().get(0).getPdfPath());
    }

    @Test
    public void testModifierDiagnosticAucunChangement() throws IOException {
        Diagnostic newDiagnostic = new Diagnostic("RefDiag3", tempFile2.getAbsolutePath());
        bienLouable.modifierDiagnostic(newDiagnostic);

        // Verify that the list of diagnostics remains unchanged
        assertEquals(1, bienLouable.getDiagnostic().size());
        assertEquals(diagnostic1, bienLouable.getDiagnostic().get(0));
        assertEquals(tempFile.getAbsolutePath(), bienLouable.getDiagnostic().get(0).getPdfPath());
    }

    @Test
    public void testGetDiagnostics() {
        assertEquals(1, bienLouable.getDiagnostic().size());
        assertEquals(diagnostic1, bienLouable.getDiagnostic().get(0));
    }

    @Test
    public void testGetTravaux() {
        assertTrue(bienLouable.getTravaux().isEmpty());
        Devis devis = new Devis("DEV456", 2000.0f, "Peinture", 300.0f);
        bienLouable.ajouterTravaux(devis);
        assertEquals(1, bienLouable.getTravaux().size());
        assertEquals(devis, bienLouable.getTravaux().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumeroFiscalInvalide() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        new BienLouable("123456", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics,null);
    }
}