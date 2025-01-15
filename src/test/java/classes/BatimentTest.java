package classes;

import static org.junit.Assert.*;

import enumeration.TypeLogement;
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
        tempFile = File.createTempFile("testFile", ".pdf");
        Files.write(tempFile.toPath(), "Test PDF Data".getBytes());

        batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix", "31000");
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(new Diagnostic("RefDiag1", tempFile.getAbsolutePath()));
        bienLouable = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics, null,TypeLogement.MAISON);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("123456789101", batiment.getNumeroFiscal());
        assertEquals("123 Rue de la Paix", batiment.getAdresse());
        assertEquals("Paris", batiment.getVille());
        assertEquals("31000", batiment.getCodePostal());
        assertTrue(batiment.getBienLouable().isEmpty());
    }

    @Test
    public void testAjouterBienLouable() {
        batiment.ajouterBienLouable(bienLouable);
        assertEquals(1, batiment.getBienLouable().size());
        assertEquals(bienLouable, batiment.getBienLouable().get(0));
    }

    @Test
    public void testSupprimerBienLouable() {
        batiment.ajouterBienLouable(bienLouable);
        batiment.supprimerBienLouable(bienLouable);
        assertTrue(batiment.getBienLouable().isEmpty());
    }

    @Test
    public void testSetters() {
        batiment.setVille("Lyon");
        assertEquals("Lyon", batiment.getVille());

        batiment.setAdresse("456 Rue de Lyon");
        assertEquals("456 Rue de Lyon", batiment.getAdresse());

        batiment.setNumeroFiscal("987654321098");
        assertEquals("987654321098", batiment.getNumeroFiscal());

        List<BienLouable> bienLouables = new ArrayList<>();
        bienLouables.add(bienLouable);
        batiment.setBienLouable(bienLouables);
        assertEquals(1, batiment.getBienLouable().size());
        assertEquals(bienLouable, batiment.getBienLouable().get(0));
    }

    @Test
    public void testToString() {
        String expected = "Batiment{adresse='123 Rue de la Paix', numero_fiscal='123456789101', ville='Paris', code_postal='31000', bien_louable=[]}";
        assertEquals(expected, batiment.toString());
    }

    @Test
    public void testGetTypeLogement() {
        assertEquals(TypeLogement.BATIMENT, batiment.getTypeLogement());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumeroFiscalInvalide() {
        new Batiment("123456", "Paris", "123 Rue de la Paix", "31000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodePostalInvalide() {
        new Batiment("123456789101", "Paris", "123 Rue de la Paix", "310");
    }
}