package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a street on map. Street has it's own ID (name) and is defined by
 * coordinates as point to point representation.
 * 
 * @author Petr Růžanský
 */
public class Street {
    // Coordinates of street from start to end.
    private List<Coordinate> coordinates = new ArrayList<>();
    // Name of the Street (id)
    private String Id;

    // Treshold of precision to determine cointinuity of streets and check if stops
    // belong to street
    public static final double SELECTION_FUZZINESS = 3;

    // Stops on the street
    private List<Stop> Stops = new ArrayList<>();

    // avg Speed of vehicles on the Street in meters/s
    private float speedModifier = 1;

    /**
     * 
     * @param Id Unique name of street
     * @param points points that define the shape and position of the street
     */
    public Street(String Id, Coordinate... points) {
        this.Id = Id;
        for (Coordinate coordinate : points) {
            coordinates.add(coordinate);
        }
    }

    /**
     * 
     * @param Id Unique name of street
     * @param points list of points that define the shape and position of the street
     */
    public Street(String Id, List<Coordinate> c) {
        this.Id = Id;
        this.coordinates = c;
    }

    /**
     * 
     * @param Id Unique name of street
     * @param speedModifier modifier of the speed on the route from 0.2 to 1.
     * @param points list of points that define the shape and position of the street
     */
    public Street(String Id, float speedModifier, Coordinate... points) {
        this.Id = Id;
        for (Coordinate coordinate : points) {
            coordinates.add(coordinate);
        }
        this.speedModifier = speedModifier;
    }

    /**
     * Add stop in the stop list and check if Stop belongs to the Street by
     * Coordinates
     * 
     * @param stop Stop to be added
     * @return true if belongs, else false
     */
    public boolean addStop(Stop stop) {

        for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate a = coordinates.get(i);
            Coordinate b = coordinates.get(i + 1);

            /*
             * CALCULATION, IF POINT IS ON LINE IS USED by help from STACKOVERFLOW.COM
             * https://stackoverflow.com/a/13741803/11087259
             */

            Coordinate leftPoint;
            Coordinate rightPoint;

            // Normalize start/end to left right to make the offset calc simpler.
            if (a.getX() <= b.getX()) {
                leftPoint = a;
                rightPoint = b;
            } else {
                leftPoint = b;
                rightPoint = a;
            }

            // If point is out of bounds, no need to do further checks.
            if (stop.getCoordinate().getX() + SELECTION_FUZZINESS < leftPoint.getX()
                    || rightPoint.getX() < stop.getCoordinate().getX())
                continue;
            else if (stop.getCoordinate().getY() + SELECTION_FUZZINESS < Math.min(leftPoint.getY(), rightPoint.getY())
                    || Math.max(leftPoint.getY(), rightPoint.getY()) < stop.getCoordinate().getY()
                            - SELECTION_FUZZINESS)
                continue;

            double deltaX = rightPoint.getX() - leftPoint.getX();
            double deltaY = rightPoint.getY() - leftPoint.getY();

            // If the line is straight, the earlier boundary check is enough to determine
            // that the point is on the line.
            // Also prevents division by zero exceptions.
            boolean lineContains;
            if (deltaX != 0 && deltaY != 0) {

                double slope = deltaY / deltaX;
                double offset = leftPoint.getY() - leftPoint.getX() * slope;
                double calculatedY = stop.getCoordinate().getX() * slope + offset;

                // Check calculated Y matches the points Y coord with some easing.
                lineContains = stop.getCoordinate().getY() - SELECTION_FUZZINESS <= calculatedY
                        && calculatedY <= stop.getCoordinate().getY() + SELECTION_FUZZINESS;

            } else {
                lineContains = true;
            }

            if (lineContains) {
                this.Stops.add(stop);
                stop.setStreet(this, i);
                return true;
            }
        }

        throw new IllegalArgumentException("Stop coordinate isn't on street");

    }

    /**
     * @return the id
     */
    public String getId() {
        return Id;
    }

    /**
     * Check if entered point is one of the points in Street
     * 
     * @param point finding point
     * @return index of point, -1 if is not aviable;
     */
    public int isPoint(Coordinate point) {

        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).equals(point)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Return list of Coordinates that defines street. First is always the start of
     * 
     * 
     * 
     * @return the list of Coordinates
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * @return List of stops. If Street has not any, return none
     */
    public List<Stop> getStops() {
        return Stops;
    }

    /**
     * Return start coordinate of street
     * 
     * @return first coordinate of the street (Start)
     */
    public Coordinate begin() {
        return this.coordinates.get(0);
    }

    /**
     * Return end coordinate of street
     * 
     * @return last coordinate of the street (End)
     */
    public Coordinate end() {
        return this.coordinates.get(coordinates.size() - 1);
    }

    /**
     * @return the speedModifier
     */
    public float getSpeedModifier() {
        return speedModifier;
    }

    /**
     * @param speedModifier the speedModifier to set in range of 0.2 to 1 included;
     */
    public void setSpeedModifier(float speedModifier) {
        if (speedModifier > 1 || speedModifier < 0.2) {
            throw new IllegalArgumentException("Speed modifier has to be in range 0.2 to 1 included");
        }
        this.speedModifier = speedModifier;
    }

    /**
     * Check if this street follows another street
     * 
     * @param street the other street to compare
     * @return array of two ints, which represents index of point of the street,
     *         where the streets cross, first for this street and second one from
     *         street s. Return empty array, if streets doesn't follow
     */
    public int[] follows(Street street) {
        for (int i = 0; i < this.coordinates.size(); i++) {
            for (int j = 0; j < street.getCoordinates().size(); j++) {
                Coordinate coord1 = this.coordinates.get(i);
                Coordinate coord2 = street.getCoordinates().get(j);

                if (coord1.equals(coord2)) {
                    int[] A = { i, j };
                    return A;
                }
            }
        }

        return new int[0];

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