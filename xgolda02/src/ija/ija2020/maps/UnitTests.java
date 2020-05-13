package ija.ija2020.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;

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

        // Assertions.assertFalse(s1.follows(s2), "Ulice first a second na sebe
        // nenavazuji");
        // Assertions.assertTrue(s1.follows(s3), "Ulice first a third na sebe
        // navazuji");
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

        // c1 = new Coordinate(170, 200);
        // stop1 = new Stop("stop1", c1);
        // Assertions.assertFalse(s1.addStop(stop1), "Zastavka je mimo ulici");
        // Assertions.assertTrue(s1.getStops().isEmpty(), "Ulice je prazdna");

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

        // c1 = new Coordinate(-45, -33);
        // c2 = new Coordinate(35, 42);
        // c3 = new Coordinate(-20, -5);
        // s1 = new Street("first", c1, c2);
        // stop1 = new Stop("stop6", c3);
        // Assertions.assertFalse(s1.addStop(stop1), "Zastavka neni na ulici");
        // Assertions.assertTrue(s1.getStops().isEmpty(), "Ulice je prazdna");

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

    @Test
    public void testLine1() {
        Coordinate c1, c2, c3, c4, c5, c6, c7;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c1, c7);

        Line l1 = new Line("line1", s0, s1, s3, s4, s0);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c3);
        Assertions.assertEquals(route.get(3).getKey(), c4);
        Assertions.assertEquals(route.get(4).getKey(), c5);
        Assertions.assertEquals(route.get(5).getKey(), c7);
        Assertions.assertEquals(route.get(6).getKey(), c1);
    }

    @Test
    public void testLine2() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c3);
        Assertions.assertEquals(route.get(3).getKey(), c4);
        Assertions.assertEquals(route.get(4).getKey(), c5);
        Assertions.assertEquals(route.get(5).getKey(), c7);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c1);
    }

    @Test
    public void testLine3() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s1, s0);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c3);
        Assertions.assertEquals(route.get(3).getKey(), c4);
        Assertions.assertEquals(route.get(4).getKey(), c5);
        Assertions.assertEquals(route.get(5).getKey(), c7);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c3);
        Assertions.assertEquals(route.get(10).getKey(), c1);
    }

    @Test
    public void testLineWithStops1() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9;
        Stop stop1;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        stop1 = new Stop("Stop1", c9);
        s6.addStop(stop1);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c3);
        Assertions.assertEquals(route.get(3).getKey(), c4);
        Assertions.assertEquals(route.get(4).getKey(), c5);
        Assertions.assertEquals(route.get(5).getKey(), c7);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c9);
        Assertions.assertEquals(route.get(9).getKey(), c1);

    }

    @Test
    public void testLineWithStops2() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
        Stop stop1, stop2;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        s6.addStop(stop1);
        s0.addStop(stop2);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c10);
        Assertions.assertEquals(route.get(3).getKey(), c3);
        Assertions.assertEquals(route.get(4).getKey(), c4);
        Assertions.assertEquals(route.get(5).getKey(), c5);
        Assertions.assertEquals(route.get(6).getKey(), c7);
        Assertions.assertEquals(route.get(7).getKey(), c8);
        Assertions.assertEquals(route.get(8).getKey(), c4);
        Assertions.assertEquals(route.get(9).getKey(), c9);
        Assertions.assertEquals(route.get(10).getKey(), c1);

    }

    @Test
    public void testLineWithStops3() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        Stop stop1, stop2, stop3;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c2, c3);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        c11 = new Coordinate(580, 236);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        stop3 = new Stop("Stop3", c11);
        s6.addStop(stop1);
        s0.addStop(stop2);
        s4.addStop(stop3);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);
        l1.addStop(stop3);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c2);
        Assertions.assertEquals(route.get(2).getKey(), c10);
        Assertions.assertEquals(route.get(3).getKey(), c3);
        Assertions.assertEquals(route.get(4).getKey(), c4);
        Assertions.assertEquals(route.get(5).getKey(), c5);
        Assertions.assertEquals(route.get(6).getKey(), c7);
        Assertions.assertEquals(route.get(7).getKey(), c11);
        Assertions.assertEquals(route.get(8).getKey(), c8);
        Assertions.assertEquals(route.get(9).getKey(), c4);
        Assertions.assertEquals(route.get(10).getKey(), c9);
        Assertions.assertEquals(route.get(11).getKey(), c1);

    }

    @Test
    public void testLineWithStops3PlusMiddleRoute() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        Stop stop1, stop2, stop3;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c3, c2);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        c11 = new Coordinate(580, 236);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        stop3 = new Stop("Stop3", c11);
        s6.addStop(stop1);
        s0.addStop(stop2);
        s4.addStop(stop3);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);
        l1.addStop(stop3);

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c3);
        Assertions.assertEquals(route.get(2).getKey(), c4);
        Assertions.assertEquals(route.get(3).getKey(), c5);
        Assertions.assertEquals(route.get(4).getKey(), c7);
        Assertions.assertEquals(route.get(5).getKey(), c11);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c9);
        Assertions.assertEquals(route.get(9).getKey(), c1);

    }

    @Test
    public void testVehicles() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        Stop stop1, stop2, stop3;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c3, c2);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        c11 = new Coordinate(580, 236);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        stop3 = new Stop("Stop3", c11);
        s6.addStop(stop1);
        s0.addStop(stop2);
        s4.addStop(stop3);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);
        l1.addStop(stop3);

        Vehicle v1, v2, v3;
        v1 = new Vehicle("vehicle1");
        v2 = new Vehicle("vehicle2");
        v3 = new Vehicle("vehicle3");

        l1.addVehicle(v1);
        l1.addVehicle(v2);
        l1.addVehicle(v3);

        l1.addDeployTime(60000);
        l1.addDeployTime(120000);
        l1.addDeployTime(10000);
        l1.addDeployTime(140000);

        Assertions.assertFalse(l1.checkDeploy(9000));
        Assertions.assertTrue(l1.checkDeploy(10000));
        Assertions.assertFalse(l1.checkDeploy(40000));
        Assertions.assertTrue(l1.checkDeploy(70000));
        Assertions.assertTrue(l1.checkDeploy(130000));
        Assertions.assertFalse(l1.checkDeploy(150000));

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c3);
        Assertions.assertEquals(route.get(2).getKey(), c4);
        Assertions.assertEquals(route.get(3).getKey(), c5);
        Assertions.assertEquals(route.get(4).getKey(), c7);
        Assertions.assertEquals(route.get(5).getKey(), c11);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c9);
        Assertions.assertEquals(route.get(9).getKey(), c1);

    }

    @Test
    public void testVehicles2() {
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        Stop stop1, stop2, stop3;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        Street s0 = new Street("street0", c1, c3, c2);
        Street s1 = new Street("street1", c3, c4, c5);
        Street s2 = new Street("street2", c5, c6);
        Street s3 = new Street("street3", c5, c7);
        Street s4 = new Street("street4", c8, c7);
        Street s5 = new Street("street5", c4, c8);
        Street s6 = new Street("street6", c1, c4);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        c11 = new Coordinate(580, 236);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        stop3 = new Stop("Stop3", c11);
        s6.addStop(stop1);
        s0.addStop(stop2);
        s4.addStop(stop3);
        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);
        l1.addStop(stop3);

        Vehicle v1, v2, v3;
        v1 = new Vehicle("vehicle1");
        v2 = new Vehicle("vehicle2");
        v3 = new Vehicle("vehicle3");

        l1.addVehicle(v1);
        l1.addVehicle(v2);
        l1.addVehicle(v3);

        l1.addDeployTime(60000);
        l1.addDeployTime(120000);
        l1.addDeployTime(10000);
        l1.addDeployTime(140000);

        Assertions.assertFalse(l1.checkDeploy(9000));
        Assertions.assertTrue(l1.checkDeploy(10000));

        v1.getSimulatedPosition(20000);
        v1.getSimulatedPosition(25000);
        v1.getSimulatedPosition(30000);

        Assertions.assertFalse(l1.checkDeploy(40000));
        Assertions.assertTrue(l1.checkDeploy(70000));
        Assertions.assertTrue(l1.checkDeploy(130000));
        Assertions.assertFalse(l1.checkDeploy(150000));

        List<SimpleImmutableEntry<Coordinate, Stop>> route = l1.getRoute();

        Assertions.assertEquals(route.get(0).getKey(), c1);
        Assertions.assertEquals(route.get(1).getKey(), c3);
        Assertions.assertEquals(route.get(2).getKey(), c4);
        Assertions.assertEquals(route.get(3).getKey(), c5);
        Assertions.assertEquals(route.get(4).getKey(), c7);
        Assertions.assertEquals(route.get(5).getKey(), c11);
        Assertions.assertEquals(route.get(6).getKey(), c8);
        Assertions.assertEquals(route.get(7).getKey(), c4);
        Assertions.assertEquals(route.get(8).getKey(), c9);
        Assertions.assertEquals(route.get(9).getKey(), c1);

    }
}