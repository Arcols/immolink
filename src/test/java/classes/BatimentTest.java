package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatimentTest {

    private Batiment batiment;
    private BienLouable bienLouable;

    @Before
    public void setUp() throws IOException {
        batiment = new Batiment("123456789101", "Paris", "123 Rue de la Paix");
        List<Diagnostic> diagnostics = new ArrayList<>();
        diagnostics.add(new Diagnostic("RefDiag1", "path/to/diagnostic1.pdf"));
        bienLouable = new BienLouable("123456789102", "Paris", "123 Rue de la Paix", "Appartement 12B", diagnostics);
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
        new Batiment("123456", "Paris", "123 Rue de la Paix");
    }
}