package ija.ija2020.guiMaps;

import ija.ija2020.main.MapObject;
import ija.ija2020.maps.Coordinate;
import ija.ija2020.maps.Stop;
import ija.ija2020.maps.Street;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

public class GuiStreet extends Street implements MapObject {
    private static final int offsetX = 200;
    private static final int offsetY = 0;
    private static final int clickRange = 5;
    Color color = new Color(0, 0, 0, 1.0);
    public GuiStreet(String Id, Coordinate... points) {
        super(Id, points);
    }
    public GuiStreet(String Id, List<Coordinate> points) {
        super(Id, points);
        Random random = new Random();
        color = new Color(random.nextFloat()*0.7,random.nextFloat() * 0.3 , random.nextFloat(), 1.0);
    }

    @Override
    public List<Shape> getEl(float scale) {
        List<Shape> lines = new ArrayList<Shape>();
        Coordinate last = null;
        for (Coordinate c : this.getCoordinates()) {
            if (last != null) {
                Line line = new Line(last.getX() * scale, last.getY() * scale, c.getX() * scale, c.getY() * scale);
                line.setStrokeWidth(2*scale);
                line.setStroke(color);
                lines.add(line);
            }
            last = c;
        }

        for (Stop s : this.getStops()) {
            GuiStop gS = new GuiStop(s);
            for (Shape shape : gS.getEl(scale)) {
                lines.add(shape);
            }
        }
        return lines;
    }

    public boolean wasIClickedOn(double cX, double cY, float scale){
        double x = cX /scale - offsetX/scale;
        double y = cY /scale - offsetY/scale;

        //System.out.println("map click: " + x + "x" + y);
        Coordinate last = null;
        //go through all the lines
        for(Coordinate c: this.getCoordinates()){

            if(last != null){
                if(lineClick(x,y, last, c)){
                    return true;
                }
            }
            last = c;
        }
        return false;
    }

    private boolean lineClick(double x, double y, Coordinate start, Coordinate end){
        //make a circle of radius clickRange every clickRange pixels along the line c. If the click is within any of these, we have a click. The click area is a bit squiggly but its about the simplest solution there is so its well worth it
        int a = abs(start.getX()-end.getX());
        int b = abs(start.getY()-end.getY());

        //only left-to-right lines are supported
        if(start.getY()>end.getY()) b*=-1;

        double c = distance(start.getX(), start.getY(), end.getX(), end.getY());

        //this is way faster than calculating the next point with sin() or whatever
        double steps = abs(c/clickRange);
        double aStep = a/steps;
        double bStep = b/steps;

        double xBuf = start.getX();
        double yBuf = start.getY();

        for(int i = 0; i<steps+1; i++){
            //if its in the circle, line was clicked on
            if(distance(x, y, xBuf, yBuf)<clickRange){
                return true;
            }

            xBuf+= aStep;
            yBuf+= bStep;
            if(this.getId().equals("std1")){
              //  System.out.println("circle at " + xBuf + "X" + yBuf);
            }

        }


        return false;
    }

    private double distance(double x1, double y1, double x2, double y2){

        //simple pythagoras theorem
        double a = x1 - x2;
        double b = y1 - y2;
        double c = sqrt(a*a + b*b);
        if(c>0) return c;
        return c* (-1);
    }
}
