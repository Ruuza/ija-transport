package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;

public class Line {

    public static final int msInDay = 86400000;

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

    // When do deploy Vehicle on Line. In miliseconds from 0:00
    private List<Integer> deployTimes = new ArrayList<>();

    // Last time deploy was checked
    private int lastDeployTime = 0;

    // List of stops to stop at
    private List<Stop> stops = new ArrayList<>();

    // Last point used to start street with
    private int lastPoint;

    // Determine which street does a point below to.
    // Example: point[5] belwo to street whichStret[5]
    private List<Street> whichStreet = new ArrayList<>();

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

        for (Street street : streets) {
            if (street == null) {
                throw new IllegalArgumentException("null street");
            }
        }

        this.inputStreets = streets;

        generateRoute();
    }

    private void generateRoute() {

        this.route = new ArrayList<>();
        this.whichStreet = new ArrayList<>();

        this.route.add(new SimpleImmutableEntry<Coordinate, Stop>(inputStreets[0].getCoordinates().get(0), null));
        this.whichStreet.add(inputStreets[0]);
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

        if (route.size() != whichStreet.size()) {
            throw new InternalError("whichStreet size and route size are not equal");
        }

        if (inputStreets[0].isPoint(getLastStreetPoint()) != -1) {
            if (getStartCoordinateOfRoute().equals(route.get(route.size() - 1).getKey())) {
                return;
            } else {
                this.route.add(new SimpleImmutableEntry<Coordinate, Stop>(getStartCoordinateOfRoute(), null));
                this.whichStreet.add(inputStreets[0]);

            }
        } else {
            throw new IllegalArgumentException(
                    "the route doesn't end where start's. First and last street in argument has to be the same");
        }
    }

    private void addStreet​(Street actualStreet, Street nextStreet) {

        boolean debug = false;

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

        // System.out.println("Name: " + actualStreet.getId());

        if(actualStreet.getId() == "sth3"){
            System.out.println("Current Street: sth3 , cross: " + streetCross[0]);
            System.out.println("Next Street: " + nextStreet.getId() + " , corss: " + streetCross[1]);
            System.out.println("Last point: " + lastPoint);
            debug = true;
        }

        // Fill the coordinates for actualStreet
        if (lastPoint > streetCross[0]) {
            for (int i = lastPoint - 1; i >= 0; i--) {
                for (Stop stop : stopsOnActualStreet) {
                    if (stop.getAfterWhichPointOfStreet() == i) {
                        route.add(new SimpleImmutableEntry<Coordinate, Stop>(stop.getCoordinate(), stop));
                        whichStreet.add(stop.getStreet());
                    }
                }
                route.add(new SimpleImmutableEntry<Coordinate, Stop>(actualStreet.getCoordinates().get(i), null));
                whichStreet.add(actualStreet);
            }
        } else {
            for (int i = lastPoint + 1; i <= streetCross[0]; i++) {
                for (Stop stop : stopsOnActualStreet) {
                    if (stop.getAfterWhichPointOfStreet() == i - 1) {
                        route.add(new SimpleImmutableEntry<Coordinate, Stop>(stop.getCoordinate(), stop));
                        whichStreet.add(stop.getStreet());
                    }
                }
                route.add(new SimpleImmutableEntry<Coordinate, Stop>(actualStreet.getCoordinates().get(i), null));
                whichStreet.add(actualStreet);
            }
        }

        lastPoint = streetCross[1];

    }

    public void updateStreets(Street... streets){
        Street[] oldStreets = inputStreets;
        this.inputStreets = streets;
        try {
            generateRoute();
        } catch (Exception e) {
            this.inputStreets = oldStreets;
            generateRoute();
            throw new IllegalArgumentException("Entered streets are in wrong format");
        }
    }

    public boolean addDeployTime(int time) {

        if (time >= msInDay) {
            System.err.println("time has to be lower than miliseconds in day");
            return false;
        }

        if (time < 0) {
            System.err.println("time has to be postivie number");
            return false;
        }

        if (deployTimes.contains(time)) {
            return false;
        }
        deployTimes.add(time);
        return true;
    }

    public boolean removeTime(int time) {
        if (deployTimes.contains(time)) {
            deployTimes.remove(time);
        }
        return false;
    }

    public boolean addStop(Stop stop) {
        if (stop == null) {
            throw new IllegalArgumentException("stop cannot be null");
        }
        if (stops.contains(stop)) {

            return false;
        } else {
            stops.add(stop);
            generateRoute();
            return true;
        }

    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : this.vehicles) {
            if (!vehicle.isDeployed()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public List<Vehicle> getunAvailableVehicles() {
        List<Vehicle> unavailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.isDeployed()) {
                unavailableVehicles.add(vehicle);
            }
        }
        return unavailableVehicles;
    }

    public void updateVehicles(int time) {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.isDeployed()) {
                vehicle.getSimulatedPosition(time);
            }
        }
    }

    public boolean checkDeploy(int time) {
        if (time >= msInDay) {
            System.err.println("time has to be lower than miliseconds in day");
            return false;
        }

        if (time < 0) {
            System.err.println("time has to be postivie number");
            return false;
        }
        if (getRoute().isEmpty()) {
            System.out.println("The route is empty!");
        }
        if (getDeployTimes().isEmpty()) {
            System.out.println("The are no deploy times!");
        }

        if (this.lastDeployTime == time) {
            return false;
        }

        boolean deploy = false;

        for (Integer deployTime : this.deployTimes) {
            if (deployTime <= time && deployTime > lastDeployTime) {
                deploy = true;
            }
        }

        lastDeployTime = time;
        if (!deploy) {
            return false;
        }

        updateVehicles(time);
        List<Vehicle> availableVehicles = getAvailableVehicles();

        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicle is available");
            return false;
        }

        return deploy(availableVehicles.get(0), time);

    }

    private boolean deploy(Vehicle vehicle, int time) {
        return vehicle.deploy(time);
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

    public boolean addVehicle(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            return false;
        }
        this.vehicles.add(vehicle);
        vehicle.setActiveLine(this);
        return false;
    }

    public boolean removeVehicle(Vehicle vehicle) {
        if (!this.vehicles.contains(vehicle)) {
            return false;
        }
        this.vehicles.remove(vehicle);
        vehicle.setActiveLine(null);
        return false;
    }

    public Coordinate getStartCoordinateOfRoute() {
        return route.get(0).getKey();
    }

    public List<SimpleImmutableEntry<Coordinate, Stop>> getRoute() {
        return Collections.unmodifiableList(route);
    }

    public void printRoute() {

        System.out.println("########## PRINTING ROUTE: ############");
        String printString = "";
        for (Street street : inputStreets) {
            printString += street.getId();
            printString += ", ";
        }
        System.out.println(printString);
        for (SimpleImmutableEntry<Coordinate, Stop> coordAndStop : route) {
            if (coordAndStop.getValue() == null) {
                System.out.println(
                        "COORDINATE at X: " + coordAndStop.getKey().getX() + " ,Y:" + coordAndStop.getKey().getY());
            } else {
                System.out.println("STOP: " + coordAndStop.getValue().getId() + " at X: " + coordAndStop.getKey().getX()
                        + " ,Y: " + coordAndStop.getKey().getY());

            }
        }
    }

    /**
     * @return the whichStreet
     */
    public Street getWhichStreet(int i) {
        if (i < 0 || i > whichStreet.size() - 1) {
            throw new IllegalArgumentException("index is lower than zero ore greater than number of streets");
        }
        return whichStreet.get(i);
    }

    /**
     * @return the deployTimes
     */
    public List<Integer> getDeployTimes() {
        return deployTimes;
    }

    /**
     * @return the lastDeployTime
     */
    public int getLastDeployTime() {
        return lastDeployTime;
    }

    /**
     * @return the iD
     */
    public String getID() {
        return ID;
    }

    /**
     * @return the inputStreets
     */
    public Street[] getInputStreets() {
        return inputStreets;
    }

    /**
     * @return the stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * @return the lastPoint
     */
    public int getLastPoint() {
        return lastPoint;
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