package ija.ija2020.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;

public class Vehicle {

    // How many milliseconds is in one day
    public static final int msInDay = 86400000;

    // Unique id of the vehicle
    private String Id;

    // Current location of vehicle
    private Coordinate coord;

    // Determine, if wehicle is deployed on the road
    private boolean isDeployed = false;

    // Transport Line under which this vehicle operate
    private Line activeLine;

    // point on the current route
    private int currentRoutePointer = 0;

    // determine position in percent on current road
    private float percentOfLineCompleted;

    // Last time, that position was calculated with. Time is in format of seconds
    // after 0:00.
    private int lastUpdateTime;

    // How many seconds remain until vehicle can leave the stop
    private int reaminingMillisecondsOnStop = 0;

    // Speed in meters per second
    private float speed = 13.4f;

    // Save route, so changes in Line won't affect this ride
    private List<SimpleImmutableEntry<Coordinate, Stop>> activeRoute = new ArrayList<>();

    public Vehicle(String id) {
        if (id == null) {
            throw new IllegalArgumentException("null parrameters not supported");
        }
        this.Id = id;
    }

    /**
     * @return the isDeployed
     */
    public boolean isDeployed() {
        return isDeployed;
    }

    public Coordinate getSimulatedPosition(int time) {

        if (!isDeployed) {
            System.err.println("Vehicle " + this.Id + " isn't deployed");
            return null;
        }

        if (time >= msInDay) {
            System.err.println("time has to be lower than miliseconds in day");
            return null;
        }

        if (time < 0) {
            System.err.println("time has to be postivie number");
            return null;
        }

        updateSimulatedPosition(time);
        return coord;
    }

    private boolean updateSimulatedPosition(int time) {

        if (!isDeployed) {
            return true;
        }

        if (time >= msInDay) {
            System.err.println("time has to be lower than miliseconds in day");
            return false;
        }

        if (time < 0) {
            System.err.println("time has to be postivie number");
            return false;
        }

        if (time == lastUpdateTime) {
            return true;
        }

        if (currentRoutePointer == activeRoute.size() - 1) {
            isDeployed = false;
            return true;
        }

        int deltaTime;

        if (time > lastUpdateTime) {
            deltaTime = time - lastUpdateTime;
        } else {
            deltaTime = (msInDay - lastUpdateTime) + time;
        }

        // if vehicle is on the stop, try complete wait time.
        if (percentOfLineCompleted == 0 && activeRoute.get(currentRoutePointer).getValue() != null) {
            int waitTime;
            if (reaminingMillisecondsOnStop > 0) {
                waitTime = reaminingMillisecondsOnStop;
            } else {
                waitTime = activeRoute.get(currentRoutePointer).getValue().getWaitTime();
            }

            if (waitTime > deltaTime) {
                waitTime -= deltaTime;
                reaminingMillisecondsOnStop = waitTime;
                return true;
            } else {
                deltaTime -= waitTime;
                if (deltaTime == 0) {
                    deltaTime = 1;
                }
            }

        }

        // move to next point
        int remainTime = toNextPoint(deltaTime);

        if (remainTime <= 0) {
            return true;
        } else {
            updateSimulatedPosition(remainTime);
        }

        return true;

    }

    private int toNextPoint(int deltaTime) {
        Coordinate nextCoordinate = activeRoute.get(currentRoutePointer + 1).getKey();

        double distance = coord.distance(nextCoordinate);

        int timeDistance = distanceToTime(distance);

        // When the vehicle is already in next point
        if (deltaTime >= timeDistance) {
            this.coord = nextCoordinate;
            return deltaTime - timeDistance;
        }

        // else caclulate the position
        double coefficient = ((double) deltaTime) / timeDistance;

        this.coord = new Coordinate((int) Math.round(coord.getX() + (coefficient * coord.diffX(nextCoordinate))),
                (int) Math.round(coord.getY() + (coefficient * coord.diffY(nextCoordinate))));

        return 0;

    }

    private int distanceToTime(double distance) {
        return (int) ((distance * 1000) / speed);
    }

    public boolean deploy(int time) {
        if (isDeployed) {
            return false;
        }
        if (activeLine == null) {
            return false;
        }
        if (time < 0) {
            System.err.println("time is lesser than zero");
            return false;
        }

        this.isDeployed = true;
        this.lastUpdateTime = time;
        this.activeRoute = activeLine.getRoute();
        this.coord = activeRoute.get(0).getKey();
        this.currentRoutePointer = 0;
        this.percentOfLineCompleted = 0;
        this.reaminingMillisecondsOnStop = 0;

        return true;
    }

    /**
     * @param activeLine the activeLine to set
     */
    public void setActiveLine(Line activeLine) {
        isDeployed = false;
        if (this.activeLine != null && activeLine != null) {
            throw new IllegalAccessError("Vehicle already assigned");
        }
        this.activeLine = activeLine;

    }

}
