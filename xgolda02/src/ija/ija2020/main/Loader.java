package ija.ija2020.main;

import ija.ija2020.guiMaps.GuiStreet;
import ija.ija2020.maps.*;
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
    //no need to comment this, the code is pretty self explanatory. Refer to the other function or the JSON file in case of doubts
    public static List<Line> getLines(String filename, List<GuiStreet> gStreets, List<Vehicle> vehicles){

        JSONObject obj = init(filename);
        List<Line> lines = new ArrayList<Line>();
        JSONArray jLines = obj.getJSONArray("lines");
        for(int i = 0; i<jLines.length(); i++){
            JSONObject jLine = jLines.getJSONObject(i);
            System.out.println("Parsing line " + jLine.getString("id"));

            List<Street> streets = new ArrayList<Street>();
            List<Stop> stops = new ArrayList<Stop>();
            int stArrLen = 0;
            JSONArray jStreets = jLine.getJSONArray("streets");
            for(int p = 0; p<jStreets.length(); p++){
                stArrLen++;
                JSONObject jStreet = jStreets.getJSONObject(p);

                //find the street
                Street street = findStreetById(gStreets, jStreet.getString("id"));
                streets.add(street);
                System.out.println("    parsing street " + jStreet.getString("id"));
                JSONArray jStops = jStreet.getJSONArray("stops");
                for(int o =0; o<jStops.length(); o++){
                    JSONObject jStop = jStops.getJSONObject(o);
                    stops.add(findStopById(street.getStops(), jStop.getString("id") ));
                    System.out.println("        Parsing stop " + jStop.getString("id"));
                }
            }



            //convert the list to an array because reasons
            Street[] stAr = new Street[stArrLen];
            int o = 0;
            for(Street street: streets){
                stAr[o] = street;
                System.out.println("adding " + street.getId());
                o++;

            }
            System.out.println("equals " + stAr[0].equals(stAr[stArrLen-1]));
            Line line = new Line(jLine.getString("id"), stAr);
            for(Stop stop: stops){
                line.addStop(stop);
            }
            JSONArray jVehicles = jLine.getJSONArray("vehicles");
            for(int p = 0; p<jVehicles.length(); p++){
                JSONObject vehicle = jVehicles.getJSONObject(p);
                Vehicle v = new Vehicle(vehicle.getString("id"));
                line.addVehicle(v);
                vehicles.add(v);
                System.out.println("    Parsing veh "+vehicle.getString("id"));
            }


            //add a bunch of deploytimes. Right now all the lines are deploying at the same time - perhaps i can add it as a feature later
            for(int f = 0; f< 24*60*60*1000; f+=5000){
                line.addDeployTime(f);
            }




            lines.add(line);
        }


        return lines;
    }

    private static Street findStreetById(List<GuiStreet> streets, String key){
        for(Street street: streets){
            if(street.getId().equals(key)){
                return street;
            }
        }
        return null;
    }

    private static Stop findStopById(List<Stop> stops, String key){
        for(Stop stop: stops){
            if(stop.getId().equals(key)){
                return stop;
            }
        }
        return null;
    }
}
