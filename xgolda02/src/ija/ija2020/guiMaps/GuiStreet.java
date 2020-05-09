package ija.ija2020.guiMaps;

import ija.ija2020.main.MapObject;
import ija.ija2020.maps.Coordinate;
import ija.ija2020.maps.Stop;
import ija.ija2020.maps.Street;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class GuiStreet extends Street implements MapObject {

    public GuiStreet(String Id, Coordinate... points) {
        super(Id, points);
    }

    @Override
    public List<Shape> getEl(float scale) {
        List<Shape> lines = new ArrayList<Shape>();
        Coordinate last = null;
        for(Coordinate c: this.getCoordinates()){
            if(last!=null){
                lines.add(new Line(last.getX()*scale, last.getY()*scale, c.getX()*scale, c.getY()*scale));
            }
            last = c;
        }

        for(Stop s: this.getStops()){
            GuiStop gS = new GuiStop(s);
            for(Shape shape: gS.getEl(scale)){
                lines.add(shape);
            }
        }
        return lines;
    }
}
