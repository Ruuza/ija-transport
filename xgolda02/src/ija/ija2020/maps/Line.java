package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.IntStream;

public class Line {

    // Id/Name of the Line
    private String ID;
    // Path of the route
    private List<SimpleImmutableEntry<Coordinate, Stop>> route = new ArrayList<>();
    // Where is the start position of the line
    // private Coordinate startPosition;

    // contain list of vehicles under this Line
    private List<Vehicle> vehicles = new ArrayList<>();

    // Array of streets to create path with
    private Street[] inputStreets;

    // List of stops to stop at
    private List<Stop> stops = new ArrayList<>();

    // Last point used to start street with
    private int lastPoint;

    public Line(String id, Street... streets) {
        if (id == null)
            throw new IllegalArgumentException("null argument");
        // if (startPosition == null)
        // throw new IllegalArgumentException("null argument");
        this.ID = id;
        // this.startPosition = startPosition;

        if (streets.length < 2) {
            throw new IllegalArgumentException("At least two streets expected as argument");
        }

        this.inputStreets = streets;

        generateRoute();
    }

    public void generateRoute() {

        this.route = new ArrayList<>();

        route.add(new SimpleImmutableEntry<Coordinate, Stop>(inputStreets[0].getCoordinates().get(0), null));
        this.lastPoint = 0;

        for (int i = 0; i < inputStreets.length; i++) {
            if (inputStreets[i] == null) {
                throw new IllegalArgumentException("street is null");
            }

            Street actualStreet = inputStreets[i];
            Street nextStreet;

            if (i == inputStreets.length - 1) {
                nextStreet = inputStreets[0];
            } else {
                nextStreet = inputStreets[i + 1];
            }

            addStreet​(actualStreet, nextStreet);
        }

        if (inputStreets[0].isPoint(getLastStreetPoint()) != -1) {
            if (getStartCoordinateOfRoute().equals(route.get(route.size() - 1).getKey())) {
                return;
            } else {
                route.add(new SimpleImmutableEntry<Coordinate, Stop>(getStartCoordinateOfRoute(), null));
            }
        } else {
            throw new IllegalArgumentException(
                    "the route doesn't end where start's. First and last street in argument has to be the same");
        }
    }

    private void addStreet​(Street actualStreet, Street nextStreet) {

        int[] streetCross = actualStreet.follows(nextStreet);

        if (streetCross.length != 2) {
            throw new IllegalArgumentException("street doesn't cross");
        }

        List<Stop> stopsOnActualStreet = new ArrayList<>();

        for (Stop stop : stops) {
            if (stop.getStreet().equals(actualStreet)) {
                stopsOnActualStreet.add(stop);
            }
        }

        // Fill the coordinates for actualStreet
        if (lastPoint > streetCross[0]) {
            for (int i = lastPoint - 1; i >= 0; i--) {
                for (Stop stop : stopsOnActualStreet) {
                    if (stop.getAfterWhichPointOfStreet() == i) {
                        route.add(new SimpleImmutableEntry<Coordinate, Stop>(stop.getCoordinate(), stop));
                    }
                }
                route.add(new SimpleImmutableEntry<Coordinate, Stop>(actualStreet.getCoordinates().get(i), null));
            }
        } else {
            for (int i = lastPoint + 1; i <= streetCross[0]; i++) {
                for (Stop stop : stopsOnActualStreet) {
                    if (stop.getAfterWhichPointOfStreet() == i - 1) {
                        route.add(new SimpleImmutableEntry<Coordinate, Stop>(stop.getCoordinate(), stop));
                    }
                }
                route.add(new SimpleImmutableEntry<Coordinate, Stop>(actualStreet.getCoordinates().get(i), null));
            }
        }

        lastPoint = streetCross[1];

    }

    public boolean addStop(Stop stop) {
        if (stops.contains(stop)) {
            return false;
        } else {
            stops.add(stop);
            generateRoute();
            return true;
        }

    }

    public boolean removeStop(Stop stop) {
        if (stops.contains(stop)) {
            stops.remove(stop);
            generateRoute();
        }
        return false;
    }

    private Coordinate getLastStreetPoint() {
        for (int i = route.size() - 1; i >= 0; i--) {
            if (route.get(i).getValue() == null) {
                return route.get(i).getKey();
            }
        }
        throw new IllegalArgumentException("no street point's in route");
    }

    public Coordinate getStartCoordinateOfRoute() {
        return route.get(0).getKey();
    }

    public List<SimpleImmutableEntry<Coordinate, Stop>> getRoute() {
        return Collections.unmodifiableList(route);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Line))
            return false;
        Line lineObj = (Line) obj;
        if (this.ID == lineObj.ID) {
            return true;
        } else {
            return false;
        }
    }

}