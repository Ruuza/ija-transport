package ija.ija2020.main;
/**
 * Contains all the gui event handlers
 *
 * @author Vojtěch Golda
 */
import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Controller {
    List<MapObject> mapObjects;

    @FXML
    private AnchorPane map;

    @FXML
    private void zoom(ScrollEvent e){
        e.consume();
        double z = e.getDeltaY();
        if(z>0){
            z=1.05;
        }else{
            z=0.95;
        }
        map.setScaleX(map.getScaleX()*z);
        map.setMinHeight(map.getHeight()*z);
        map.setScaleY(map.getScaleY()*z);
        map.setMinWidth(map.getWidth()*z);
    }

    public void setMapObjects(List<MapObject> mapObjects){
        this.mapObjects = mapObjects;
        for(MapObject mapObject : mapObjects ){
            map.getChildren().addAll(mapObject.getEl());
        }
    }
}
