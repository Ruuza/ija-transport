package ija.ija2020.main;
/**
 * Main
 *
 * @author Vojtěch Golda
 */

import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.maps.Coordinate;
import ija.ija2020.maps.Stop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
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

        List<MapObject> mapObjects = new ArrayList<MapObject>();
        GuiStreet s = new GuiStreet("First", new Coordinate(200,200), new Coordinate(300,300), new Coordinate(200,300));
        s.addStop(new Stop("First", new Coordinate(200, 200)));
        s.addStop(new Stop("Second", new Coordinate(250, 300)));
        mapObjects.add(s);

        Controller controller = loader.getController();
        controller.setMapObjects(mapObjects);
    }
    public static void main(String args[]){
        launch(args);
    }
}
