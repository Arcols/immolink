package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

public class FactureTest {

    private Facture facture;

    @Before
    public void setUp() {
        facture = new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("F123456", facture.getNumero());
        assertEquals("Electricity", facture.getType());
        assertEquals(Date.valueOf("2023-10-01"), facture.getDate());
        assertEquals(150.0, facture.getMontant(), 0.0);
    }
}