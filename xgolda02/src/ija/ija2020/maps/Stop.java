package ija.ija2020.maps;

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
     * 
     * @param Id unique ID of stop
     */
    public Stop(String Id) {
        this.Id = Id;
        this.coordinate = null;
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

    /**
     * Set street where stop is locates
     * 
     * @param street street where stop is located
     */
    public void setStreet(Street street) {
        this.street = street;
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