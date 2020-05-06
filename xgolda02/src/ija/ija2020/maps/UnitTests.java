package ija.ija2020.maps;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
}