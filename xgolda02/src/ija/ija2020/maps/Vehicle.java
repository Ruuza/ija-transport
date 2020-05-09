package ija.ija2020.maps;

public class Vehicle {
    Coordinate c = new Coordinate(200, 200);
    boolean deployed = true;


    public Coordinate getSimulatedPosition(int time){
        Coordinate newC = new Coordinate(c.getX()+1, c.getY()+1);
        c = newC;
        return c;
    }
}
