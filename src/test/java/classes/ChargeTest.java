package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ChargeTest {

    private Charge charge;
    private List<Facture> factures;

    @Before
    public void setUp() {
        factures = new ArrayList<>();
        factures.add(new Facture("F123456", "Electricity", Date.valueOf("2023-10-01"), 150.0));
        factures.add(new Facture("F654321", "Water", Date.valueOf("2023-11-01"), 75.0));
        charge = new Charge(factures);
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals(factures, charge.getFacture());
    }

    @Test
    public void testGetType() {
        assertNull(charge.getType());
    }
}