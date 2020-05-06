package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petr Růžanský
 */
public class Street {

    private List<Coordinate> coordinates = new ArrayList<>();
    private String Id;

    private List<Stop> Stops;

    public Street(String Id, Coordinate startOfStreet, Coordinate endOfStreet) {
        this.Id = Id;
        this.Stops = new ArrayList<>();
        coordinates.add(startOfStreet);
        coordinates.add(endOfStreet);
    }

    public Street(String Id, Coordinate startOfStreet, Coordinate endOfStreet, Coordinate... curves) {
        this.Id = Id;
        this.Stops = new ArrayList<>();
        coordinates.add(startOfStreet);
        for (Coordinate coordinate : curves) {
            coordinates.add(coordinate);
        }
        coordinates.add(endOfStreet);
    }

    public boolean addStop(Stop stop) {

        // this.Stops.add(stop);
        // stop.setStreet(this);
        for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate a = coordinates.get(i);
            Coordinate b = coordinates.get(i + 1);

            // distance(A, C) + distance(B, C) == distance(A, B)

            double x = a.distance(stop.getCoordinate()) + b.distance(stop.getCoordinate());
            double y = a.distance(b);

            System.out.println("x:" + x);
            System.out.println("y:" + y);

            if (x == y)
                return true;
        }

        return false;

    }

    /**
     * @return the id
     */
    public String getId() {
        return Id;
    }

    /**
     * @return the coordinates
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * @return the stops
     */
    public List<Stop> getStops() {
        return Stops;
    }

    public Coordinate begin() {
        return this.coordinates.get(0);
    }

    public Coordinate end() {
        return this.coordinates.get(coordinates.size() - 1);
    }

    public boolean follows(Street s) {
        if (this.begin().equals(s.end()) || this.begin().equals(s.begin()) || this.end().equals(s.end())
                || this.end().equals(s.begin()))
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Street)) {
            return false;
        }
        Street street = (Street) o;
        return this.Id == street.getId();
    }

}