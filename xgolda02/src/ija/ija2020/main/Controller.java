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

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private GuiStreet selStreet = null;
    @FXML
    private AnchorPane map;
    @FXML
    private Text timeText;
    @FXML
    private Text speedText;
    @FXML
    private Text status;
    @FXML
    private Text streetSel;
    @FXML
    private TextField jumpTime;
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

    @FXML
    private void jump(){
        String[] sTime = jumpTime.getCharacters().toString().split(":");
        if(sTime.length!=3){
            status.setText("Invalid input!");
            return;
        }

        int h;
        int m;
        int s;
        try {
            h = Integer.parseInt(sTime[0]);
            m = Integer.parseInt(sTime[1]);
            s = Integer.parseInt(sTime[2]);
        }catch (NumberFormatException e) {
            status.setText("Invalid input!");
            return;
        }

        if(h<0||h>23||m<0||m>59||s<0||s>59){
            status.setText("Invalid input!");
            return;
        }

        //all the checks passed succesfully, time to do some simulating
        status.setText("Simulating...");
        timer.cancel();
        map.getChildren().clear();

        boolean loopThrough= false;
        int target = h*60*60*1000 + m*60*1000 + s*1000;
        if(time > target){
            loopThrough = true;
        }

        while(time< target || loopThrough){
            if(time>24*60*60*1000-1){
                loopThrough = false;
                time -= 24*60*60*1000;
            }
            notifyLines(time);
            for(Vehicle v: vehicles){
                if(v.isDeployed()){
                    v.getSimulatedPosition(time);
                }

            }
            time+=1000 ;
        }
        status.setText("Everything ok");
        go();
    }


    @FXML
    private void handleMapClick(MouseEvent event) {
        System.out.println(event.getSceneX());
        System.out.println(event.getSceneY());
        for(GuiStreet s: mapObjects){
            if(s.wasIClickedOn(event.getSceneX(),event.getSceneY(), scale)){
                if(selStreet!=null) selStreet.unselect();
                streetSel.setText("Selected " + s.getId());
                s.select();
                selStreet = s;
                return;
            }
        }
    }

    @FXML
    private void strslow1(){

    }
    @FXML
    private void strslow2(){

    }
    @FXML
    private void strslow3(){

    }
    @FXML
    private void strslow4(){

    }
    @FXML
    private void close(){

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
