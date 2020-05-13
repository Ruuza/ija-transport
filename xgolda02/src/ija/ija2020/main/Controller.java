package ija.ija2020.main;
/**
 * Contains all the gui event handlers
 *
 * @author Vojtěch Golda
 */
import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.guiMaps.GuiVehicle;
import ija.ija2020.maps.Line;
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
    private final int maxFramerateIndex = 2;
    private final int framerate = 20;
    private Timer timer;
    private List<GuiStreet> mapObjects;
    private int time = 0;
    private int lowerFrameTime =0;
    private List<Vehicle> vehicles = null;
    private List<GuiVehicle> movable = new ArrayList<GuiVehicle>();
    private List<Line> lines = null;
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
        speed /=2;
        speedText.setText((speed*1)+"x");


        timer.cancel();
        go();
    }

    @FXML
    private void faster(){
        if(speed>=maxFramerateIndex*64*framerate) return;
        speed *=2;
        speedText.setText((speed*1)+"x");

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
        map.getChildren().clear();
        for(MapObject mapObject : mapObjects ){
            map.getChildren().addAll(mapObject.getEl(scale));
        }


        for(GuiVehicle g: movable){
            map.getChildren().addAll(g.getEl(scale));
        }
    }

    public void go(){
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(speed>maxFramerateIndex){
                    time += 1000/framerate * (speed/maxFramerateIndex);
                }else{
                    time+=1000/framerate;
                }
                if(time>24*60*60*1000-1) time -= 24*60*60*1000;


                timeText.setText(getTimeString(time));
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        notifyLines(time);
                        if(time>=lowerFrameTime+1000 || time < lowerFrameTime){
                            lowerFrameTime = time;
                            movable = new ArrayList<GuiVehicle>();
                            for(Vehicle v: vehicles){
                                if(v.isDeployed()){
                                    movable.add(new GuiVehicle(v.getSimulatedPosition(lowerFrameTime)));
                                }

                            }
                        }
                        refreshMapObjects();
                    }
                });
            }
        }, 0, (long) (1000/ framerate /evalSpeed()));
    }


    public void setVehicles(List<Vehicle> vehicles){
        this.vehicles = vehicles;
    }
    public void setLines(List<Line> lines){
        this.lines = lines;
    }
    private void notifyLines(int time){

        for(Line line: lines){
            line.checkDeploy(time);
        }
    }

    private float evalSpeed(){
        if(speed<=maxFramerateIndex){
            return speed;
        }else{

        }

        return (float) maxFramerateIndex;
    }

    private String getTimeString(int time){

        int timeWHours = time % (60 * 60 * 1000);
        int hours = (time - timeWHours) / (60 * 60 * 1000);
        String hoursLead = "";
        if(hours<10) hoursLead = "0";


        int timeWMinutes = timeWHours % (60*1000);
        int minutes = (timeWHours-timeWMinutes) / (60*1000);
        String minutesLead = "";
        if(minutes<10) minutesLead = "0";


        int miliseconds = timeWMinutes % 1000;
        int seconds = (timeWMinutes-miliseconds) / 1000;
        String secondsLead = "";
        if(seconds<10) secondsLead = "0";

        String sMiliseconds = "";
        if(miliseconds>0) sMiliseconds = "." + miliseconds;





        return hoursLead + hours + ":" + minutesLead + minutes + ":" + secondsLead + seconds +  sMiliseconds;
    }


}
