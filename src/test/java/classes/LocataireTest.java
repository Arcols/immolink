package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

public class LocataireTest {

    private Locataire locataire;
    private Bail bail;
    private Charge charge;

    @Before
    public void setUp() {
        locataire = new Locataire("Doe", "John", "0606060606","ee.ee@ee.ee",  Date.valueOf("2020-01-01"), "M");
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("Doe", locataire.getNom());
        assertEquals("John", locataire.getPrénom());
        assertEquals("0606060606", locataire.getTéléphone());
        assertEquals("ee.ee@ee.ee", locataire.getMail());
        assertEquals(Date.valueOf("2020-01-01"), locataire.getDateArrive());
        assertEquals("M", locataire.getGenre());
        assertTrue(locataire.getBeaux().isEmpty());
        assertTrue(locataire.getCharges().isEmpty());
    }

    @Test
    public void testConstructeurSansMail() {
        Locataire locataireSansMail = new Locataire("Smith", "Jane", "0707070707", Date.valueOf("2021-01-01"), "F");
        assertEquals("Smith", locataireSansMail.getNom());
        assertEquals("Jane", locataireSansMail.getPrénom());
        assertEquals("0707070707", locataireSansMail.getTéléphone());
        assertNull(locataireSansMail.getMail());
        assertEquals(Date.valueOf("2021-01-01"), locataireSansMail.getDateArrive());
        assertEquals("F", locataireSansMail.getGenre());
        assertTrue(locataireSansMail.getBeaux().isEmpty());
        assertTrue(locataireSansMail.getCharges().isEmpty());
    }

    @Test
    public void testAddCharge() {
        locataire.addCharge(charge);
        assertEquals(1, locataire.getCharges().size());
        assertEquals(charge, locataire.getCharges().get(0));
    }

    @Test
    public void testAddBail() {
        locataire.addBail(bail);
        assertEquals(1, locataire.getBeaux().size());
        assertEquals(bail, locataire.getBeaux().get(0));
    }

    @Test
    public void testSetters() {
        locataire.setNom("Smith");
        assertEquals("Smith", locataire.getNom());

        locataire.setPrénom("Jane");
        assertEquals("Jane", locataire.getPrénom());

        locataire.setTéléphone("0707070707");
        assertEquals("0707070707", locataire.getTéléphone());

        locataire.setMail("new.email@example.com");
        assertEquals("new.email@example.com", locataire.getMail());

        locataire.setDateArrive(Date.valueOf("2021-01-01"));
        assertEquals(Date.valueOf("2021-01-01"), locataire.getDateArrive());

        locataire.setGenre("F");
        assertEquals("F", locataire.getGenre());
    }

    @Test
    public void testToString() {
        String expected = "Locataire{, nom='Doe', prénom='John', téléphone='0606060606', genre='M', mail='ee.ee@ee.ee', date_arrive=2020-01-01, beaux=[], charges=[]}";
        assertEquals(expected, locataire.toString());
    }

    @Test
    public void testEquals() {
        Locataire locataire2 = new Locataire("Doe", "John","0606060606","ee.ee@ee.ee",   Date.valueOf("2020-01-01"), "M");
        assertTrue(locataire.equals(locataire2));

        Locataire locataire3 = new Locataire("Smith", "Jane","0707070707","jj.jj@jj.jj",   Date.valueOf("2021-01-01"), "F");
        assertFalse(locataire.equals(locataire3));

        Locataire locataire4 = new Locataire("Doe", "John", "0606060606", Date.valueOf("2020-01-01"), "M");
        assertTrue(locataire.equals(locataire4));

        Locataire locataire5 = new Locataire("Doe", "John", "0606060606", Date.valueOf("2020-01-01"), "F");
        assertFalse(locataire.equals(locataire5));
    }
}