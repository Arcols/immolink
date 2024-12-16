package classes;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class BailTest {

    @Test
    public void testBailConstructorAndGetters() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin);

        assertTrue(bail.isSolde_de_compte());
        assertEquals("BL3456789101", bail.getFisc_bien());
        assertEquals(1000.0, bail.getLoyer(), 0.0);
        assertEquals(200.0, bail.getCharge(), 0.0);
        assertEquals(500.0, bail.getDepot_garantie(), 0.0);
        assertEquals(dateDebut, bail.getDate_debut());
        assertEquals(dateFin, bail.getDate_fin());
    }

    @Test
    public void testBailSetters() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin);

        bail.setSolde_de_compte(false);
        bail.setFisc_bien("BL9876543210");
        bail.setLoyer(1500.0);
        bail.setCharge(300.0);
        bail.setDepot_garantie(600.0);
        bail.setDate_debut(Date.valueOf("2024-02-01"));
        bail.setDate_fin(Date.valueOf("2024-11-30"));

        assertFalse(bail.isSolde_de_compte());
        assertEquals("BL9876543210", bail.getFisc_bien());
        assertEquals(1500.0, bail.getLoyer(), 0.0);
        assertEquals(300.0, bail.getCharge(), 0.0);
        assertEquals(600.0, bail.getDepot_garantie(), 0.0);
        assertEquals(Date.valueOf("2024-02-01"), bail.getDate_debut());
        assertEquals(Date.valueOf("2024-11-30"), bail.getDate_fin());
    }

    @Test
    public void testBailEqualsAndHashCode() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin);
        Bail bail2 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin);
        Bail bail3 = new Bail(false, "BL9876543210", 1500.0, 300.0, 600.0, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"));

        assertEquals(bail1, bail2);
        assertNotEquals(bail1, bail3);
        assertEquals(bail1.hashCode(), bail2.hashCode());
        assertNotEquals(bail1.hashCode(), bail3.hashCode());
    }
}