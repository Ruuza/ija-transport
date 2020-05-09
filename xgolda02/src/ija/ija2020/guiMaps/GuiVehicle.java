package ija.ija2020.guiMaps;

import ija.ija2020.main.MapObject;
import ija.ija2020.maps.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class GuiVehicle implements MapObject {
    Coordinate c;

    public GuiVehicle(Coordinate c) {
        this.c = c;
    }

    @Override
    public List<Shape> getEl(float scale) {
        List<Shape> list = new ArrayList<Shape>();
        list.add(new Circle(c.getX() * scale, c.getY() * scale, 4 * scale, new Color(1, 0, 0, 1.0)));
        return list;
    }
}
