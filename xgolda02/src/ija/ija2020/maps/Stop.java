package ija.ija2020.maps;

import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Representing the stop. The stop has to have unique Id, but coordinate can be
 * unsigned.
 * 
 * @author Petr Ruzansky
 */
public class Stop {
    // Id (name) of the stop
    private String Id;
    // Coordinate of Stop in 2d space
    private Coordinate coordinate;
    // the Street on which the stop is located
    private Street street;
    // After which point of street is the Stop located
    private int afterWhichPointOnStreet;
    // Time in ms that took vehicle to stay on stop
    private int waitTime;

    /**
     * 
     * @param Id         unique ID of stop
     * @param coordinate coordination in 2D space
     */
    public Stop(String Id, Coordinate coordinate) {
        this.Id = Id;
        this.coordinate = coordinate;
    }

    /**
     * Retun ID of the street
     * 
     * @return the ID of the street
     */
    public String getId() {
        return this.Id;
    }

    /**
     * Return coordinate of the stop
     * 
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public int getAfterWhichPointOfStreet() {
        return afterWhichPointOnStreet;
    }

    /**
     * Set street where stop is locates
     * 
     * @param street street where stop is located
     */
    public void setStreet(Street street, int afterWhichPointOnStreet) {
        this.street = street;
        this.afterWhichPointOnStreet = afterWhichPointOnStreet;
    }

    /**
     * Retrun the Street where stop is located
     * 
     * @return the Street where stop is located
     */
    public Street getStreet() {
        return this.street;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Stop)) {
            return false;
        }
        Stop stop = (Stop) o;
        return this.Id == stop.getId();
    }

}