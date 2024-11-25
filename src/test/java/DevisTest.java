
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import classes.Devis;

public class DevisTest {

	private Devis devis;

	@Before
	public void setUp() {
		// Initialisation d'un objet Devis pour les tests
		Devis devis = new Devis("DEV123", 1000.0f, "Travaux de rénovation", 200.0f);
	}

	@Test
	public void testConstructeur() {
		assertEquals("DEV123", devis.getNum_devis());
		assertEquals(1000.0f, devis.getMontant(), 0.0);
		assertEquals("Travaux de rénovation", devis.getNature());
		assertEquals(200.0f, devis.getMontantNondeductible(), 0.0);
	}

	@Test
	public void testGetters() {
		assertEquals("DEV123", devis.getNum_devis());
		assertEquals(1000.0f, devis.getMontant(), 0.0);
		assertEquals("Travaux de rénovation", devis.getNature());
		assertEquals(200.0f, devis.getMontantNondeductible(), 0.0);
		assertEquals(800.0f, devis.getMontantDeductible(), 0.0);
	}
}