package ija.ija2020.main;

import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.maps.Coordinate;
import ija.ija2020.maps.Stop;
import ija.ija2020.maps.Street;
import org.json.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static JSONObject init(String filename){
        String streetsJsonRaw = "{}";
        try{
            streetsJsonRaw = new String(Files.readAllBytes(Paths.get(filename)));
        }catch (IOException e){
            System.out.println("Error reading file: "  + e);
            return null;
        }

        return new JSONObject(streetsJsonRaw);
    }
    public static List<GuiStreet> getStreets(String filename){
        List<GuiStreet> l = new ArrayList<GuiStreet>();

        JSONObject obj = init(filename);
        if(obj == null){
            return l;
        }

        JSONArray streets = obj.getJSONArray("streets");
        for (int i = 0; i < streets.length(); i++){
            List<Coordinate> c = new ArrayList<Coordinate>();
            JSONObject jStreet = streets.getJSONObject(i); //Json object representing the street
            JSONArray jC = jStreet.getJSONArray("coordinates"); //array of coordinates
            for (int o = 0; o < jC.length(); o++){
                JSONObject jCi = jC.getJSONObject(o); //one coordinate
                c.add(new Coordinate(jCi.getInt("x"),jCi.getInt("y"))); //add it to the list
            }
            GuiStreet street = new GuiStreet(jStreet.getString("id"), c); //making the street object is now possible


            JSONArray jStops = jStreet.getJSONArray("stops"); //array of stops
            for(int o = 0; o<jStops.length(); o++){
                JSONObject jStop = jStops.getJSONObject(o); //one stop
                Coordinate stopC = new Coordinate(jStop.getInt("x"),jStop.getInt("y")); //coordinate for the stop
                Stop stop = new Stop(jStop.getString("id"), stopC);//create the stop object
                street.addStop(stop);//add it
            }

            l.add(street);

        }
        return l;
    }
}
