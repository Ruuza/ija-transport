package ija.ija2020.maps;

public class Vehicle {

    // Unique id of the vehicle
    private String Id;

    // Current location of vehicle
    private Coordinate coord;

    // Determine, if wehicle is deployed on the road
    private boolean isDeployed = false;

    // Current street where the vehicle is located
    private Street currentStreet;

    // after or on which point of the street the vehicle is located
    private int partOfStreetCounter;

    // point on the current route
    private int currentRoutePointer = 0;

    // Transport Line under which this vehicle operate
    private Line activeLine;

    // determine position in percent on current road
    private float percentOfLineCompleted;

    // Last time, that position was calculated with. Time is in format of seconds
    // after 0:00.
    private int lastUpdateTime;

    // How many seconds remain until vehicle can leave the stop
    private int reaminingSecodnsOnStop = 0;

    public Vehicle(String id) {
        assert id != null;
        this.Id = id;
    }

    public Vehicle(String id, Line line) {
        assert id != null;
        assert line != null;
        this.Id = id;
        this.activeLine = line;
    }

    public Coordinate getSimulatedPosition(int time) {

        if (!isDeployed) {
            System.err.println("Vehicle " + this.Id + " isn't deployed");
            return null;
        }

        return coord;
    }

    // public boolean deploy(int time) {
    // if (isDeployed) {
    // return false;
    // }
    // if (activeLine == null) {
    // return false;
    // }
    // if (time < 0) {
    // System.err.println("time is lesser than zero");
    // return false;
    // }

    // this.isDeployed = true;
    // this.lastUpdateTime = time;
    // this.coord = activeLine.getStartPosition();
    // this.currentStreet = activeLine.getRoute().get(0).getKey();
    // // this.partOfStreetCounter =

    // if (this.coord == null || this.currentStreet == null) {
    // System.err.println("Vehicle " + this.Id + " cannot be deployed: some value in
    // Line is null");
    // return false;
    // }

    // return true;
    // }

    /**
     * @param activeLine the activeLine to set
     */
    public void setActiveLine(Line activeLine) {
        isDeployed = false;
        this.activeLine = activeLine;
    }

}
