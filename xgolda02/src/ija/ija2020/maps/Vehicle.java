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

    // Last time, that position was calculated with. Time is in format of seconds
    // after 0:00.
    private int lastUpdateTime;

    // How many seconds remain until vehicle can leave the stop
    private int reaminingMillisecondsOnStop = 0;

    // Speed in meters per second
    private float speed = 13.4f;

    // Save route, so changes in Line won't affect this ride
    private List<SimpleImmutableEntry<Coordinate, Stop>> activeRoute = new ArrayList<>();

    private float xRemain = 0f;
    private float yRemain = 0f;

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
        if (coord.equals(activeRoute.get(currentRoutePointer).getKey())
                && activeRoute.get(currentRoutePointer).getValue() != null) {
            int waitTime;
            if (reaminingMillisecondsOnStop > 0) {
                waitTime = reaminingMillisecondsOnStop;
            } else {
                waitTime = activeRoute.get(currentRoutePointer).getValue().getWaitTime();
            }

            if (waitTime > deltaTime) {
                waitTime -= deltaTime;
                reaminingMillisecondsOnStop = waitTime;
                lastUpdateTime = time;
                return true;
            } else {
                this.reaminingMillisecondsOnStop = 0;
                deltaTime -= waitTime;
                if (deltaTime < 1000) {
                    deltaTime = 1000;
                }
            }

        }

        // move to next point
        int remainTime = toNextPoint(deltaTime);

        int originalTimeWithoutRemain = time - remainTime;

        if (originalTimeWithoutRemain < lastUpdateTime) {
            System.err.println("Unexpected vehicle time behaviour. Please report to administrator");
            originalTimeWithoutRemain = lastUpdateTime;
        }

        if (remainTime <= 0) {
            lastUpdateTime = time;
            return true;
        } else {
            currentRoutePointer += 1;
            updateSimulatedPosition(originalTimeWithoutRemain);
        }

        return true;

    }

    private int toNextPoint(int deltaTime) {
        Coordinate nextCoordinate = activeRoute.get(currentRoutePointer + 1).getKey();

        double distance = coord.distance(nextCoordinate);

        int timeDistance = distanceToTime(distance);

        timeDistance = (int) (timeDistance / activeLine.getWhichStreet(currentRoutePointer + 1).getSpeedModifier());

        // When the vehicle is already in next point
        if (deltaTime >= timeDistance) {
            this.coord = nextCoordinate;
            return deltaTime - timeDistance;
        }

        // else caclulate the position
        double coefficient = ((double) deltaTime) / timeDistance;

        double deltaX = nextCoordinate.getX() - coord.getX();
        double deltaY = nextCoordinate.getY() - coord.getY();

        double newX = coord.getX() + (coefficient * deltaX * activeLine.getWhichStreet(currentRoutePointer + 1).getSpeedModifier());
        double newY = coord.getY() + (coefficient * deltaY * activeLine.getWhichStreet(currentRoutePointer + 1).getSpeedModifier());
        
        newX += xRemain;
        newY += yRemain;

        int newXwhole = (int) newX;
        int newYwhole = (int) newY;

        xRemain = (float) (newX - newXwhole);
        yRemain = (float) (newY - newYwhole);

        this.coord = new Coordinate(newXwhole, newYwhole);

        return 0;

    }

    private int distanceToTime(double distance) {
        return (int) ((distance * 1000) / speed);
    }

    public boolean deploy(int time) {
        if (isDeployed) {
            System.err.println("Vehicle: " + Id + " is already deployed! Cannot be deployed");
            return false;
        }
        if (activeLine == null) {
            System.err.println("Vehicle: " + Id + " has no active line! Cannot be deployed");
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
        this.reaminingMillisecondsOnStop = 0;
        this.xRemain = 0;
        this.yRemain = 0;

        System.out.println("deploying vehicle " + Id + " on the route. Time is: " + time);
        System.out.println("And the route is: ");
        this.activeLine.printRoute();

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
