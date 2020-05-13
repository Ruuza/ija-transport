package ija.ija2020.main;
/**
 * Main
 *
 * @author Vojtěch Golda
 */

import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.maps.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static ija.ija2020.main.Loader.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        List<GuiStreet> streetList = getStreets("streets.json");
        if(streetList.isEmpty()){
            System.exit(1);
        }
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        List<Line> lines = getLines("lines.json", streetList, vehicles);
       FXMLLoader loader = new FXMLLoader(Main.class.getResource("/layout2.fxml"));
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //Displaying the contents of a scene
        stage.show();

        ////////////////////////////////////////////TEMPORARY HACK BEGIN
        Coordinate c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        Stop stop1, stop2, stop3;

        c1 = new Coordinate(124, 360);
        c2 = new Coordinate(115, 300);
        c3 = new Coordinate(107, 203);
        c4 = new Coordinate(320, 150);
        c5 = new Coordinate(403, 122);
        c6 = new Coordinate(124, 57);
        c7 = new Coordinate(528, 365);
        c8 = new Coordinate(625, 124);

        streetList = new ArrayList<GuiStreet>();
        GuiStreet s0 = new GuiStreet("street0", c1, c2, c3);
        GuiStreet s1 = new GuiStreet("street1", c3, c4, c5);
        GuiStreet s2 = new GuiStreet("street2", c5, c6);
        GuiStreet s3 = new GuiStreet("street3", c5, c7);
        GuiStreet s4 = new GuiStreet("street4", c8, c7);
        GuiStreet s5 = new GuiStreet("street5", c4, c8);
        GuiStreet s6 = new GuiStreet("street6", c1, c4);
        streetList.add(s0);streetList.add(s1); streetList.add(s2); streetList.add(s3); streetList.add(s4); streetList.add(s5); streetList.add(s6);

        c9 = new Coordinate(201, 278);
        c10 = new Coordinate(111, 252);
        c11 = new Coordinate(580, 236);
        stop1 = new Stop("Stop1", c9);
        stop2 = new Stop("Stop2", c10);
        stop3 = new Stop("Stop3", c11);
        s6.addStop(stop1);
        s0.addStop(stop2);
        s4.addStop(stop3);




        Line l1 = new Line("line1", s0, s1, s3, s4, s5, s6, s0);
        l1.addStop(stop1);
        l1.addStop(stop2);
        l1.addStop(stop3);

        lines = new ArrayList<Line>();
        lines.add(l1);

        vehicles = new ArrayList<Vehicle>();
        Vehicle v1, v2, v3;
        v1 = new Vehicle("vehicle1");
        v2 = new Vehicle("vehicle2");
        v3 = new Vehicle("vehicle3");

        vehicles.add(v1);vehicles.add(v2);vehicles.add(v3);
        l1.addVehicle(v1);
        l1.addVehicle(v2);
        l1.addVehicle(v3);

        l1.addDeployTime(6000);
        l1.addDeployTime(120000);
        l1.addDeployTime(10000);
        l1.addDeployTime(140000);


        ///////////////////////////////////////TEMPORARY HACK END





        Controller controller = loader.getController();
        controller.setMapObjects(streetList);
        controller.setVehicles(vehicles);
        controller.setLines(lines);
        controller.go();

        List<AbstractMap.SimpleImmutableEntry<Coordinate, Stop>> cs = lines.get(0).getRoute();
        for(AbstractMap.SimpleImmutableEntry<Coordinate, Stop> c: cs){
            System.out.println("X: " + c.getKey().getX() + "/Y:" + c.getKey().getY());;
        }

    }
    public static void main(String args[]){
        launch(args);
    }
}
