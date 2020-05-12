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


        Controller controller = loader.getController();
        controller.setMapObjects(streetList);

        controller.setVehicles(vehicles);
        controller.go();
    }
    public static void main(String args[]){
        launch(args);
    }
}
