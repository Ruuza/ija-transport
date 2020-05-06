package ija.ija2020.maps;

public class Stop {

    private String Id;
    private Coordinate coordinate;
    private Street street;

    public Stop(String Id, Coordinate coordinate) {
        this.Id = Id;
        this.coordinate = coordinate;
    }

    public Stop(String Id) {
        this.Id = Id;
    }

    public String getId() {
        return this.Id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setStreet(Street s) {
        this.street = s;
    }

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