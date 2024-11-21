package projet.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import projet.classes.*;

import java.util.ArrayList;
import java.util.List;

public class BatimentTest {

    private Batiment batiment;
    private BienLouable bien_louable;

    @Before
    public void setUp() {
        batiment = new Batiment("123 Rue de la Paix", "75001", "Paris");

        // création d'un bien louable pour le test de la fonction ajout
        bien_louable = new BienLouable("123456789101", "Appartement 101", batiment, new ArrayList<>());
    }

    @Test
    public void testGettersAndConstructeur() {
        assertEquals("123 Rue de la Paix", batiment.getAdresse());
        assertEquals("75001", batiment.getCode_postal());
        assertEquals("Paris", batiment.getVille());
        assertTrue(batiment.getBien_louable().isEmpty()); // Vérifie que la liste est vide initialement
    }
    
    @Test
    public void testAjouterBienLouable() {
        batiment.ajouterBienLouable(bien_louable);
        assertEquals(1, batiment.getBien_louable().size());
        assertEquals(bien_louable, batiment.getBien_louable().get(0));
    }
}
