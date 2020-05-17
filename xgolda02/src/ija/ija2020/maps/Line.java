package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Define the transport line. Creates point to point path through both, streets and stops, so Vehicles can just move from one point to another.
 * Also cares of deploying Vehicles at right deploy time.
 * @author Petr Ruzansky
 */
public class Line {

    // how many milliseconds are in day
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


    /**
     * 
     * @param id unique name of street
     * @param streets streets in order to create path with
     */
    public Line(String id, Street... streets) {
        if (id == null)
            throw new IllegalArgumentException("null argument");
        this.ID = id;

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

    /**
     * Generate point to point route (path) with both streets and stops
     */
    private void generateRoute() {

        this.route = new ArrayList<>();
        this.whichStreet = new ArrayList<>();

        // add first route
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

        // We are back on street now, we have to check if we are on right point. If not, get there.
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

    /**
     * Add required point of streets and stops to the line so vehicle can just follow them point to point.
     * @param actualStreet actual street that has to be generated
     * @param nextStreet next street
     */
    private void addStreet​(Street actualStreet, Street nextStreet) {

        //we get crossing of two streets to know from which to which point we have to generate path
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
            for (int i = lastPoint - 1; i >= streetCross[0]; i--) {
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

    /**
     * set Streets
     * @param streets streets to replace default streets from constructor
     */
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

    /**
     * Set new part of street insted of old one. The first and last element has to be already set in line.
     * Find the first result and replace everything between first and last element with the Steeets between first and last new elements.
     * @param streets List of the new part of the street.
     */
    public void updatePartOfStreets(List<Street> streets){
        if(streets == null){
            throw new IllegalArgumentException("Streets are null");
        }
        Street[] oldStreets = inputStreets;
        List<Street> newStreets = new ArrayList<>();

        boolean wasFirstStreet = false;
        boolean wasLastStreet = false;

        int indexStart = -1;
        int indexEnd = -1;

        indexStart = Arrays.asList(inputStreets).indexOf(streets.get(0));
        indexEnd = Arrays.asList(inputStreets).indexOf(streets.get(streets.size()-1));

        if(indexStart == -1 || indexEnd == -1){
            throw new IllegalArgumentException("First and last element are not in streets");
        }

        if(indexStart >= indexEnd){
            throw new IllegalArgumentException("First element is after the last element");
        }

        int streetsCounter = 0;
        for(Street street: inputStreets){
            if(wasFirstStreet){
                if(wasLastStreet){
                    newStreets.add(street);
                }else{
                    if(streetsCounter == indexEnd){
                        wasLastStreet = true;
                    }
                }
            }else{
                if(street.equals(streets.get(0))){
                    wasFirstStreet = true;
                    for (Street s: streets){
                        newStreets.add(s);
                    }
                }else{
                    newStreets.add(street);
                }
            } 
            streetsCounter++;
        }

        if(!wasFirstStreet || !wasLastStreet){
            throw new IllegalArgumentException("First element is after the last element");
        }
        
        try {
            inputStreets = newStreets.toArray(new Street[0]);
            generateRoute();
        } catch (Exception e) {
            
            inputStreets = oldStreets;
            throw new IllegalArgumentException("won't be able to generate route");
        }

    }

    /**
     * Add time (in format: how many seconds in this day from 0:00) when the Vehicles should be deployed.
     * @param time in format: how many seconds in this day from 0:00. Determine when the Vehicles should be deployed.
     * @return true when success.
     */
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

    /**
     * Remove time from deploy times.
     * @param time time to be removed. in format: how many seconds in this day from 0:00.
     * @return true if time was in deploy times.
     */
    public boolean removeTime(int time) {
        if (deployTimes.contains(time)) {
            deployTimes.remove(time);
            return true;
        }
        return false;
    }

    /**
     * Add stop to stop at, if Line would go through that stop.
     * @param stop stop to stop at, if Line would go through that stop.
     * @return true when succes (for example stop not already in Line).
     */
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

    /**
     * return Vehicles, that can be deployed (the are not deployed yet).
     * @return undeployed vehicles
     */
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : this.vehicles) {
            if (!vehicle.isDeployed()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    /**
     * return deployed vehicles.
     * @return deployed vehicles
     */
    public List<Vehicle> getunAvailableVehicles() {
        List<Vehicle> unavailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.isDeployed()) {
                unavailableVehicles.add(vehicle);
            }
        }
        return unavailableVehicles;
    }

    /**
     * update position and status of all avaible vehicles.
     * @param time time in format: how many seconds in this day from 0:00
     */
    public void updateVehicles(int time) {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.isDeployed()) {
                vehicle.getSimulatedPosition(time);
            }
        }
    }

    /**
     * Check if time should deploy a vehicle between last and given time. If so, it would automaticly try to deploy one of avaible vehicles.
     * @param time actual time in ms from 0:00
     * @return true if vehicle was succesfully deployed.
     */
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

    /**
     * Deploy a vehicle
     * @param vehicle vehicle to deploy
     * @param time in format: how many seconds in this day from 0:00
     * @return true when successfully deployed
     */
    private boolean deploy(Vehicle vehicle, int time) {
        return vehicle.deploy(time);
    }

    /**
     * remove the stop from Stops
     * @param stop the stop to be removed
     * @return true if the stop was in Stops
     */
    public boolean removeStop(Stop stop) {
        if (stops.contains(stop)) {
            stops.remove(stop);
            generateRoute();
        }
        return false;
    }

    /**
     * return last point of street in route.
     * @return last point of street in route.
     */
    private Coordinate getLastStreetPoint() {
        for (int i = route.size() - 1; i >= 0; i--) {
            if (route.get(i).getValue() == null) {
                return route.get(i).getKey();
            }
        }
        throw new IllegalArgumentException("no street point's in route");
    }

    /**
     * add vehicle
     * @param vehicle vehicle to add
     * @return true if success
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            return false;
        }
        this.vehicles.add(vehicle);
        vehicle.setActiveLine(this);
        return false;
    }

    /**
     * remove vehicle
     * @param vehicle vehicle to remove
     * @return true if success
     */
    public boolean removeVehicle(Vehicle vehicle) {
        if (!this.vehicles.contains(vehicle)) {
            return false;
        }
        this.vehicles.remove(vehicle);
        vehicle.setActiveLine(null);
        return false;
    }

    /**
     * Get start coordinate of the route
     * @return start coordinate of the route
     */
    public Coordinate getStartCoordinateOfRoute() {
        return route.get(0).getKey();
    }

    /**
     * return the route
     * @return the route
     */
    public List<SimpleImmutableEntry<Coordinate, Stop>> getRoute() {
        return Collections.unmodifiableList(route);
    }

    /**
     * print current route on stdout
     */
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