package ija.ija2020.guiMaps;

import ija.ija2020.main.MapObject;
import ija.ija2020.maps.Coordinate;
import ija.ija2020.maps.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GuiStop extends Stop implements MapObject {
    public GuiStop(String Id, Coordinate coordinate) {
        super(Id, coordinate);
    }

    public GuiStop(Stop s) {
        super(s.getId(), s.getCoordinate());

    }

    @Override
    public List<Shape> getEl(float scale) {
        Coordinate c = this.getCoordinate();
        List<Shape> shapeList = new ArrayList<Shape>();
        shapeList.add(new Rectangle((c.getX() - 5) * scale, (c.getY() - 5) * scale, 10 * scale, 10 * scale));
        shapeList.add(new Text((c.getX() + 10) * scale, c.getY() * scale, this.getId()));
        return shapeList;
    }
}
