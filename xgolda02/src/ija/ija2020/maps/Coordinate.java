package ija.ija2020.maps;

import java.util.Objects;

/**
 * Represents coordinate of 2D space
 * 
 * @author Petr Růžanský
 */
public class Coordinate {

    private int X;
    private int Y;

    /**
     * Create coordinate (X, Y)
     * 
     * @param X X value of coordinate
     * @param Y Y value of coordinate
     */
    public Coordinate(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * @return the X coordinate
     */
    public int getX() {
        return X;
    }

    /**
     * @return the Y coordinate
     */
    public int getY() {
        return Y;
    }

    /**
     * Calculate the distance between X value of this Coordinate and another
     * 
     * @param c The second Coordinate
     * @return distance between X value of this Coordinate and another
     */
    public int diffX(Coordinate c) {
        return Math.abs(this.X - c.X);
    }

    /**
     * Calculate the distance between Y value of this Coordinate and another
     * 
     * @param c The second Coordinate
     * @return distance between Y value of this Coordinate and another
     */
    public int diffY(Coordinate c) {
        return Math.abs(this.Y - c.Y);
    }

    /**
     * Calculate the distance between this Coordinate and another
     * 
     * @param c The second Coordinate
     * @return distance betweem two coordinates
     */
    public double distance(Coordinate c) {
        return Math.sqrt(Math.pow(this.diffX(c), 2) + Math.pow(this.diffY(c), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return X == coordinate.X && Y == coordinate.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }
}