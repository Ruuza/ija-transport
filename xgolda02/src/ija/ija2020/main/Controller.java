package ija.ija2020.main;
/**
 * Contains all the gui event handlers
 *
 * @author Vojtěch Golda
 */
import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.guiMaps.GuiVehicle;
import ija.ija2020.maps.Vehicle;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    Timer timer;
    List<GuiStreet> mapObjects;
    int time = 0;
    List<Vehicle> vehicles = null;
    List<Shape> movable = null;
    float scale = 1;
    float speed = 1;
    @FXML
    private AnchorPane map;
    @FXML
    private Text timeText;
    @FXML
    private Text speedText;
    @FXML

    private void zoom(ScrollEvent e){
        e.consume();
        double z = e.getDeltaY();
        if(z>0){
            z=1.05;
        }else{
            z=0.95;
        }
        scale*=z;
        map.setMinHeight(map.getHeight()*z);
        map.setMinWidth(map.getWidth()*z);
    }
    @FXML
    private void slower(){
        if(speed<0.1) return;
        speed -=0.1;
        if(speed<0.1) speed = 0;
        speedText.setText((int) (speed*10)+"");
        timer.cancel();
        go();
    }

    @FXML
    private void faster(){
        speed +=0.1;
        speedText.setText((int) (speed*10)+"");
        timer.cancel();
        go();
    }

    public void setMapObjects(List<GuiStreet> mapObjects){
        this.mapObjects = mapObjects;
        for(MapObject mapObject : mapObjects ){
            map.getChildren().addAll(mapObject.getEl(scale));
        }

    }
    public void refreshMapObjects(){
        for(MapObject mapObject : mapObjects ){
            map.getChildren().addAll(mapObject.getEl(scale));
        }

    }

    public void go(){
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time+=10;
                timeText.setText(Integer.toString(time));
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        map.getChildren().clear();
                        refreshMapObjects();
                        movable = new ArrayList<Shape>();
                        for(Vehicle v: vehicles){
                            GuiVehicle gV = new GuiVehicle(v.getSimulatedPosition(time));
                            for(Shape s: gV.getEl(scale)){
                                movable.add(s);
                                map.getChildren().add(s);

                            }
                        }
                    }
                });
            }
        }, 0, (long) (100/speed));
    }


    public void setVehicles(List<Vehicle> vehicles){
        this.vehicles = vehicles;
    }
}
