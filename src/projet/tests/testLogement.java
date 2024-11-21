package projet.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projet.classes.*;

public class testLogement {
	private File temp_file;
    private File temp_file2;
    private Diagnostic diagnostic1;
    private Diagnostic diagnostic2;
    private BienLouable bien_louable;
    private Batiment batiment;

    @Test
    public void testLogementCreationSansGarage() {
        // Les batiments et diagnostics sont vides car ils ne sont pas utiles dans nos tests
        List<Diagnostic> diagnostics = new ArrayList<>(); 
        Batiment batiment = new Batiment(null, null, null); 

        Logement logement = new Logement(2, 25.0f, "123456789101", "Appartement 12B", batiment, diagnostics);

        // Vérification des attributs du constructeur
        assertEquals(2, logement.getNbPiece());
        assertEquals(25.0f, logement.getSurface(), 0.01f);
        assertNull(logement.getGarage());
        assertFalse(logement.hasGarage());
    }

    @Test
    public void testLogementCreationAvecGarage() throws SQLException {
        // Les batiments et diagnostics sont vides car ils ne sont pas utiles dans nos tests
        List<Diagnostic> diagnostics = new ArrayList<>();
        Batiment batiment = new Batiment(null, null, null); 
        Garage garage = new Garage("123456789100", null, batiment); 

        Logement logement = new Logement(3, 45.0f, garage, "123456789101", "Appartement 12B", batiment, diagnostics);

        // Vérification des attributs spécifiques
        assertEquals(3, logement.getNbPiece());
        assertEquals(45.0f, logement.getSurface(), 0.01f);
        assertNotNull(logement.getGarage());
        assertTrue(logement.hasGarage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogementSurfaceMinimale() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        Batiment batiment = new Batiment(null, null, null); 

        new Logement(1, 8.5f, "123456789101", "Appartement 12B", batiment, diagnostics);
    }
}
