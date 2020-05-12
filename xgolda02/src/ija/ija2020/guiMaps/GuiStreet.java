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

public class GuiStreet extends Street implements MapObject {
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
}
