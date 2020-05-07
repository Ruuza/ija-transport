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
    private List<Stop> Stops;

    public Street(String Id, Coordinate... points) {
        this.Id = Id;
        this.Stops = new ArrayList<>();
        for (Coordinate coordinate : points) {
            coordinates.add(coordinate);
        }
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
                stop.setStreet(this);
                return true;
            }
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
     * Return list of Coordinates that defines street. First is always the start of
     * the Street, the last is end
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
     * Check if this street follows another street
     * 
     * @param s the other street to compare
     * @return true if streets follows, else false
     */
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