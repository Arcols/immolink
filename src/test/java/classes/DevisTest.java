package classes;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class DevisTest {

    @Test
    public void testDevisConstructorAndGetters() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Devis devis = new Devis("123456789012", 1000.0f, "Nature", 500.0f, dateDebut, dateFin, "Type", "123 Rue de la Paix", "Entreprise");

        assertEquals("123456789012", devis.getNumDevis());
        assertEquals(1000.0f, devis.getMontantDevis(), 0.0);
        assertEquals("Nature", devis.getNature());
        assertEquals(500.0f, devis.getMontantTravaux(), 0.0);
        assertEquals(dateDebut, devis.getDateDebut());
        assertEquals(dateFin, devis.getDateFin());
        assertEquals("Type", devis.getType());
        assertEquals("123 Rue de la Paix", devis.getAdresseEntreprise());
        assertEquals("Entreprise", devis.getNomEntreprise());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumeroDevisInvalide() {
        new Devis("123456", 1000.0f, "Nature", 500.0f, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), "Type", "123 Rue de la Paix", "Entreprise");
    }
}