package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BatimentTest {

    private Batiment batiment;
    private BienLouable bienLouable;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        // Create a temporary file for testing
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix","31000");
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(new Diagnostic("RefDiag1", tempFile.getAbsolutePath()));
        bienLouable = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics,null);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("123456789101", batiment.getNumeroFiscal());
        assertEquals("123 Rue de la Paix", batiment.getAdresse());
        assertEquals("Paris", batiment.getVille());
        assertTrue(batiment.getBien_louable().isEmpty());
    }

    @Test
    public void testAjouterBienLouable() {
        batiment.ajouterBienLouable(bienLouable);
        assertEquals(1, batiment.getBien_louable().size());
        assertEquals(bienLouable, batiment.getBien_louable().get(0));
    }

    @Test
    public void testSupprimerBienLouable() {
        batiment.ajouterBienLouable(bienLouable);
        batiment.supprimerBienLouable(bienLouable);
        assertTrue(batiment.getBien_louable().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumeroFiscalInvalide() {
        new Batiment("123456", "Paris", "123 Rue de la Paix","31000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodePostalInvalide() {
        new Batiment("123456789101", "Paris", "123 Rue de la Paix","310");
    }
}