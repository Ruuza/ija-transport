package ija.ija2020.loaders;

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
import java.util.List;

public class streetLoader {
    public static List<Street> getStreets(String filename){
        String streetsJsonRaw = "{}";
        try{
            streetsJsonRaw = new String(Files.readAllBytes(Paths.get(filename)));
        }catch (IOException e){
            System.out.println("Error reading file: "  + e);
            return;
        }

        String jsonString = streetsJsonRaw ; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        String s = obj.getString("s");
        System.out.println(s);
    }
}
