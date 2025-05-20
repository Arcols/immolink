package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

public class LocataireTest {

    private Locataire locataire;
    private Bail bail;

    @Before
    public void setUp() {
        locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
    }

    @Test
    public void testConstructeurEtGetters() {
        assertEquals("Doe", locataire.getNom());
        assertEquals("John", locataire.getPrenom());
        assertEquals("Paris", locataire.getLieuNaissance());
        assertEquals("1990-01-01", locataire.getDateNaissance());
        assertEquals("0606060606", locataire.getTelephone());
        assertEquals("ee.ee@ee.ee", locataire.getMail());
        assertEquals(Date.valueOf("2020-01-01"), locataire.getDateArrive());
        assertEquals("M", locataire.getGenre());
        assertTrue(locataire.getbaux().isEmpty());
    }

    @Test
    public void testConstructeurSansMail() {
        Locataire locataireSansMail = new Locataire("Smith", "Jane", "Lyon", "1992-02-02", "0707070707", Date.valueOf("2021-01-01"), "F");
        assertEquals("Smith", locataireSansMail.getNom());
        assertEquals("Jane", locataireSansMail.getPrenom());
        assertEquals("Lyon", locataireSansMail.getLieuNaissance());
        assertEquals("1992-02-02", locataireSansMail.getDateNaissance());
        assertEquals("0707070707", locataireSansMail.getTelephone());
        assertNull(locataireSansMail.getMail());
        assertEquals(Date.valueOf("2021-01-01"), locataireSansMail.getDateArrive());
        assertEquals("F", locataireSansMail.getGenre());
        assertTrue(locataireSansMail.getbaux().isEmpty());
    }

    @Test
    public void testAddBail() {
        locataire.addBail(bail);
        assertEquals(1, locataire.getbaux().size());
        assertEquals(bail, locataire.getbaux().get(0));
    }

    @Test
    public void testSetters() {
        locataire.setNom("Smith");
        assertEquals("Smith", locataire.getNom());

        locataire.setPrenom("Jane");
        assertEquals("Jane", locataire.getPrenom());

        locataire.setLieuNaissance("Lyon");
        assertEquals("Lyon", locataire.getLieuNaissance());

        locataire.setDateNaissance("1992-02-02");
        assertEquals("1992-02-02", locataire.getDateNaissance());

        locataire.setTelephone("0707070707");
        assertEquals("0707070707", locataire.getTelephone());

        locataire.setMail("new.email@example.com");
        assertEquals("new.email@example.com", locataire.getMail());

        locataire.setDateArrive(Date.valueOf("2021-01-01"));
        assertEquals(Date.valueOf("2021-01-01"), locataire.getDateArrive());

        locataire.setGenre("F");
        assertEquals("F", locataire.getGenre());
    }

    @Test
    public void testEquals() {
        Locataire locataire1 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        Locataire locataire2 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        Locataire locataire3 = new Locataire("Smith", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        Locataire locataire4 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", null, Date.valueOf("2020-01-01"), "M");
        Locataire locataire5 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "different@mail.com", Date.valueOf("2020-01-01"), "M");

        // Test equality with identical locataires
        assertEquals(locataire1, locataire2);

        // Test inequality with different locataires
        assertNotEquals(locataire1, locataire3);

        // Test inequality with locataire having null mail
        assertNotEquals(locataire1, locataire4);

        // Test equality with locataire having null mail
        Locataire locataire6 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", null, Date.valueOf("2020-01-01"), "M");
        assertEquals(locataire4, locataire6);

        // Test inequality with different mail
        assertNotEquals(locataire1, locataire5);
    }

    @Test
    public void testEqualsWithSelf() {
        assertEquals(locataire, locataire);
    }

    @Test
    public void testEqualsWithNull() {
        assertNotEquals(null, locataire);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Locataire locataire = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        assertNotEquals(locataire, new Object());
    }

    @Test
    public void testEqualsWithNullMail() {
        Locataire locataire1 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", null, Date.valueOf("2020-01-01"), "M");
        Locataire locataire2 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", null, Date.valueOf("2020-01-01"), "M");
        assertTrue(locataire1.equals(locataire2));
    }

    @Test
    public void testEqualsWithDifferentMail() {
        Locataire locataire1 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "ee.ee@ee.ee", Date.valueOf("2020-01-01"), "M");
        Locataire locataire2 = new Locataire("Doe", "John", "Paris", "1990-01-01", "0606060606", "different@mail.com", Date.valueOf("2020-01-01"), "M");
        assertFalse(locataire1.equals(locataire2));
    }

}