package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petr Růžanský
 */
public class Street {

    private List<Coordinate> coordinates = new ArrayList<>();
    private String Id;

    private final double SELECTION_FUZZINESS = 3;

    private List<Stop> Stops;

    public Street(String Id, Coordinate... points) {
        this.Id = Id;
        this.Stops = new ArrayList<>();
        for (Coordinate coordinate : points) {
            coordinates.add(coordinate);
        }
    }

    public boolean addStop(Stop stop) {

        for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate a = coordinates.get(i);
            Coordinate b = coordinates.get(i + 1);

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