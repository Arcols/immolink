package classes;

import static org.junit.Assert.*;

import enumeration.TypeLogement;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

public class GarageTest {

    private Garage garage;
    private File tempFile;

    @Before
    public void setUp() throws IOException {

        tempFile = File.createTempFile("testFile2", ".pdf");
        Files.write(tempFile.toPath(), "New PDF Data".getBytes());

        garage = new Garage("123456789101", "Paris", "123 Rue de la Paix", "Garage 1",TypeLogement.GARAGE_PAS_ASSOCIE);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("123456789101", garage.getNumeroFiscal());
        assertEquals("Garage 1", garage.getComplementAdresse());
        assertEquals("123 Rue de la Paix", garage.getAdresse());
        assertEquals("Paris", garage.getVille());
        assertTrue(garage.getTravaux().isEmpty());
        assertNull(garage.getIdgarage());
    }

    @Test
    public void testAjouterTravaux() {
        String num_devis = "123456789012";
        String num_facture = "987654321098";
        float montant = 1000.0f;
        String nature = "Renovation";
        float montant_nondeductible = 200.0f;
        Date date_debut = Date.valueOf("2024-01-01");
        Date date_fin = Date.valueOf("2024-06-01");
        String type = "TypeA";
        String adresse = "123 Rue de Paris";
        String nom_entreprise = "EntrepriseA";

        Devis devis = new Devis(num_devis, num_facture, montant, nature, montant_nondeductible, date_debut, date_fin, type, adresse, nom_entreprise);

        garage.ajouterTravaux(devis);
        assertEquals(1, garage.getTravaux().size());
        assertEquals(devis, garage.getTravaux().get(0));
    }


    @Test
    public void testGetTravaux() {
        assertTrue(garage.getTravaux().isEmpty());
        String num_devis = "123456789012";
        String num_facture = "987654321098";
        float montant = 1000.0f;
        String nature = "Renovation";
        float montant_nondeductible = 200.0f;
        Date date_debut = Date.valueOf("2024-01-01");
        Date date_fin = Date.valueOf("2024-06-01");
        String type = "TypeA";
        String adresse = "123 Rue de Paris";
        String nom_entreprise = "EntrepriseA";

        Devis devis = new Devis(num_devis, num_facture, montant, nature, montant_nondeductible, date_debut, date_fin, type, adresse, nom_entreprise);

        garage.ajouterTravaux(devis);
        assertEquals(1, garage.getTravaux().size());
        assertEquals(devis, garage.getTravaux().get(0));
    }

    @Test
    public void testGetTypeLogement() {
        assertEquals(TypeLogement.GARAGE_PAS_ASSOCIE, garage.getTypeLogement());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumeroFiscalInvalide() {
        new Garage("123456", "Paris", "123 Rue de la Paix", "Garage 1",TypeLogement.GARAGE_PAS_ASSOCIE);
    }

    @Test
    public void testEquals() {
        Garage garage1 = new Garage("123456789101", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage2 = new Garage("123456789101", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage3 = new Garage("987654321098", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage4 = new Garage("123456789101", "Lyon", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage5 = new Garage("123456789101", "Paris", "123 Rue de la Paix", "Garage 2", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage6 = new Garage("123456789101", "Paris", "124 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_PAS_ASSOCIE);
        Garage garage7 = new Garage("123456789101", "Paris", "123 Rue de la Paix", "Garage 1", TypeLogement.GARAGE_ASSOCIE);

        assertTrue(garage1.equals(garage2));
        assertFalse(garage1.equals(garage3));
        assertFalse(garage1.equals(garage4));
        assertFalse(garage1.equals(garage5));
        assertFalse(garage1.equals(garage6));
        assertFalse(garage1.equals(garage7));
    }

    @Test
    public void testEqualsWithSelf() {
        assertTrue(garage.equals(garage));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(garage.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        assertFalse(garage.equals(new Object()));
    }
}