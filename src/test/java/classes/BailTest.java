package classes;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class BailTest {

    @Test
    public void testBailConstructorAndGetters() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);

        assertTrue(bail.isSoldeDeCompte());
        assertEquals("BL3456789101", bail.getFiscBien());
        assertEquals(1000.0, bail.getLoyer(), 0.0);
        assertEquals(200.0, bail.getCharge(), 0.0);
        assertEquals(500.0, bail.getDepotGarantie(), 0.0);
        assertEquals(dateDebut, bail.getDateDebut());
        assertEquals(dateFin, bail.getDateFin());
    }

    @Test
    public void testBailSetters() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);

        bail.setSoldeDeCompte(false);
        bail.setFiscBien("BL9876543210");
        bail.setLoyer(1500.0);
        bail.setCharge(300.0);
        bail.setDepotGarantie(600.0);
        bail.setDateDebut(Date.valueOf("2024-02-01"));
        bail.setDateFin(Date.valueOf("2024-11-30"));

        assertFalse(bail.isSoldeDeCompte());
        assertEquals("BL9876543210", bail.getFiscBien());
        assertEquals(1500.0, bail.getLoyer(), 0.0);
        assertEquals(300.0, bail.getCharge(), 0.0);
        assertEquals(600.0, bail.getDepotGarantie(), 0.0);
        assertEquals(Date.valueOf("2024-02-01"), bail.getDateDebut());
        assertEquals(Date.valueOf("2024-11-30"), bail.getDateFin());
    }

    @Test
    public void testBailEqualsAndHashCode() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        Bail bail2 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        Bail bail3 = new Bail(false, "BL9876543210", 1500.0, 300.0, 600.0, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"),100.0,100,Date.valueOf("2024-02-01"));

        assertEquals(bail1, bail2);
        assertNotEquals(bail1, bail3);
        assertEquals(bail1.hashCode(), bail2.hashCode());
        assertNotEquals(bail1.hashCode(), bail3.hashCode());
    }

    @Test
    public void testBailEquals() {
        Date dateDebut = Date.valueOf("2024-01-01");
        Date dateFin = Date.valueOf("2024-12-31");
        Bail bail1 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        Bail bail2 = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        Bail bail3 = new Bail(false, "BL9876543210", 1500.0, 300.0, 600.0, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"),100.0,100,Date.valueOf("2024-02-01"));

        assertTrue(bail1.equals(bail1));

        assertFalse(bail1.equals(null));

        assertFalse(bail1.equals("Some String"));

        Bail bailDifferentSolde = new Bail(false, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentSolde));

        Bail bailDifferentFisc = new Bail(true, "BL9876543210", 1000.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentFisc));

        Bail bailDifferentLoyer = new Bail(true, "BL3456789101", 1500.0, 200.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentLoyer));

        Bail bailDifferentCharge = new Bail(true, "BL3456789101", 1000.0, 300.0, 500.0, dateDebut, dateFin,100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentCharge));

        Bail bailDifferentDepot = new Bail(true, "BL3456789101", 1000.0, 200.0, 600.0, dateDebut, dateFin,100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentDepot));

        Bail bailDifferentDateDebut = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, Date.valueOf("2024-02-01"), dateFin,100.0,100,Date.valueOf("2024-02-01"));
        assertFalse(bail1.equals(bailDifferentDateDebut));

        Bail bailDifferentDateFin = new Bail(true, "BL3456789101", 1000.0, 200.0, 500.0, dateDebut, Date.valueOf("2024-11-30"),100.0,100,dateDebut);
        assertFalse(bail1.equals(bailDifferentDateFin));

        assertTrue(bail1.equals(bail2));
    }
}