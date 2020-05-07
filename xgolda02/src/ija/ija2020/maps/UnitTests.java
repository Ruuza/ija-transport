package ija.ija2020.maps;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * @author Petr Ruzansky
 * @author Koci
 */
public class UnitTests {
    @Test
    public void testStreet01() {
        Coordinate c1, c2, c3;
        Street s1, s2, s3;

        c1 = new Coordinate(100, 100);
        c2 = new Coordinate(100, 200);
        c3 = new Coordinate(150, 250);
        s1 = new Street("first", c1, c2, c3);
        Assertions.assertNotNull(s1, "Ulice s pravouhlymi zlomy - lze vytvorit");

        c1 = new Coordinate(100, 100);
        c2 = new Coordinate(100, 200);
        c3 = new Coordinate(150, 200);
        s1 = new Street("first", c1, c2, c3);
        Assertions.assertNotNull(s1, "Ulice s pravouhlymi zlomy - lze vytvorit");

        c1 = new Coordinate(200, 200);
        c2 = new Coordinate(250, 200);
        s2 = new Street("second", c1, c2);
        Assertions.assertNotNull(s2, "Ulice second - lze vytvorit");

        c1 = new Coordinate(150, 200);
        c2 = new Coordinate(250, 200);
        s3 = new Street("third", c1, c2);
        Assertions.assertNotNull(s3, "Ulice third - lze vytvorit");

        Assertions.assertEquals(s1.begin(), new Coordinate(100, 100), "Overeni pocatecni souradnice");
        Assertions.assertEquals(s1.end(), new Coordinate(150, 200), "Overeni koncove souradnice");
        Assertions.assertEquals(s1.getCoordinates().get(1), new Coordinate(100, 200), "Overeni prostredni souradnice");

        Assertions.assertFalse(s1.follows(s2), "Ulice first a second na sebe nenavazuji");
        Assertions.assertTrue(s1.follows(s3), "Ulice first a third na sebe navazuji");
    }

    @Test
    public void testStreet02() {
        Coordinate c1, c2, c3;
        Street s1;
        Stop stop1;

        c1 = new Coordinate(100, 100);
        c2 = new Coordinate(100, 200);
        c3 = new Coordinate(150, 200);
        s1 = new Street("first", c1, c2, c3);
        Assertions.assertNotNull(s1, "Ulice first - lze vytvorit");

        c1 = new Coordinate(170, 200);
        stop1 = new Stop("stop1", c1);
        Assertions.assertFalse(s1.addStop(stop1), "Zastavka je mimo ulici");
        Assertions.assertTrue(s1.getStops().isEmpty(), "Ulice je prazdna");

        c1 = new Coordinate(130, 200);
        stop1 = new Stop("stop2", c1);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(-20, -10);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop3", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(-21, -11);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop4", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(-20, -9);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(-20, -5);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop6", c3);
        Assertions.assertFalse(s1.addStop(stop1), "Zastavka neni na ulici");
        Assertions.assertTrue(s1.getStops().isEmpty(), "Ulice je prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(-16, -5);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(2, 11);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-45, -33);
        c2 = new Coordinate(35, 42);
        c3 = new Coordinate(2, 12);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(-1985, -1703);
        c2 = new Coordinate(1690, 1388);
        c3 = new Coordinate(35, -4);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(4, 2);
        c2 = new Coordinate(5, 6);
        c3 = new Coordinate(5, 4);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        // c1 = new Coordinate(4, 2);
        // c2 = new Coordinate(5, 6);
        // c3 = new Coordinate(4, 5);
        // s1 = new Street("first", c1, c2);
        // stop1 = new Stop("stop5", c3);
        // Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        // Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(4, 2);
        c2 = new Coordinate(5, 6);
        c3 = new Coordinate(5, 5);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");

        c1 = new Coordinate(4, 2);
        c2 = new Coordinate(5, 6);
        c3 = new Coordinate(4, 4);
        s1 = new Street("first", c1, c2);
        stop1 = new Stop("stop5", c3);
        Assertions.assertTrue(s1.addStop(stop1), "Zastavka je na ulici");
        Assertions.assertFalse(s1.getStops().isEmpty(), "Ulice neni prazdna");
    }
}