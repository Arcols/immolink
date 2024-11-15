package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import classes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class BienLouableTest {

    private File temp_file;
    private File temp_file2;
    private Diagnostic diagnostic1;
    private Diagnostic diagnostic2;
    private BienLouable bien_louable;
    private Batiment batiment;

    @Before
    public void setUp() throws IOException {
        // Création de fichiers temporaires pour les tests qui concerneront les diagnostics
        temp_file = File.createTempFile("testFile", ".pdf");
        Files.write(temp_file.toPath(), "Test PDF Data".getBytes());

        temp_file2 = File.createTempFile("testFile2", ".pdf");
        Files.write(temp_file2.toPath(), "New PDF Data".getBytes());

        // Initialisation des objets nécessaires
        diagnostic1 = new Diagnostic("RefDiag1", temp_file.getAbsolutePath());
        diagnostic2 = new Diagnostic("RefDiag2", temp_file2.getAbsolutePath());
        
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        batiment = new Batiment("123 Rue de la Paix", "75001", "Paris");
        bien_louable = new BienLouable("123456789101", "Appartement 12B", batiment, diagnostics);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("123456789101", bien_louable.getNumero_fiscal());
        assertEquals("Appartement 12B", bien_louable.getComplement_adresse());
        assertEquals(batiment, bien_louable.getBatiment());
        assertEquals(1, bien_louable.getDiagnostic().size());
        assertEquals(diagnostic1, bien_louable.getDiagnostic().get(0));
        assertTrue(bien_louable.getTravaux().isEmpty());
    }
    
    @Test
    public void testAjouterTravaux() {
        Devis devis = new Devis("DEV123", 1000.0f, "Rénovation", 200.0f);
        bien_louable.ajouterTravaux(devis);
        assertEquals(1, bien_louable.getTravaux().size());
        assertEquals(devis, bien_louable.getTravaux().get(0));
    }

    @Test
    public void testModifierDiagnostic() throws IOException {
        Diagnostic updated_diagnostic = new Diagnostic("RefDiag1", temp_file2.getAbsolutePath());
        bien_louable.modifierDiagnostic(updated_diagnostic);

        // Vérifie que le diagnostic a été mis à jour avec les nouvelles données PDF
        assertArrayEquals("New PDF Data".getBytes(), bien_louable.getDiagnostic().get(0).getPdfData());
    }

    @Test
    public void testModifierDiagnosticAucunChangement() throws IOException {
        Diagnostic new_diagnostic = new Diagnostic("RefDiag3", temp_file2.getAbsolutePath());
        bien_louable.modifierDiagnostic(new_diagnostic);

        // Vérifie que la liste des diagnostics reste inchangée
        assertEquals(1, bien_louable.getDiagnostic().size());
        assertEquals(diagnostic1, bien_louable.getDiagnostic().get(0));
        assertArrayEquals("Test PDF Data".getBytes(), bien_louable.getDiagnostic().get(0).getPdfData());
    }

    @Test
    public void testGetDiagnostics() {
        assertEquals(1, bien_louable.getDiagnostic().size());
        assertEquals(diagnostic1, bien_louable.getDiagnostic().get(0));
    }

    @Test
    public void testGetTravaux() {
        assertTrue(bien_louable.getTravaux().isEmpty());
        Devis devis = new Devis("DEV456", 2000.0f, "Peinture", 300.0f);
        bien_louable.ajouterTravaux(devis);
        assertEquals(1, bien_louable.getTravaux().size());
        assertEquals(devis, bien_louable.getTravaux().get(0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNumeroFiscalInvalide() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(diagnostic1);

        new BienLouable("123456", "Appartement 12B", batiment, diagnostics);
    }
}
