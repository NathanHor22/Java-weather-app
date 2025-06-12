import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class weatherApp {
    //need longitude and latitude to get the weather data
    //get longitude and latitude data from the OpenWeatherMap API
    //input that into the weather API

    public static JSONObject getWeatherData(String cityName) {
        // Create a new JSONObject to hold the weather data
        JSONArray locationData = getLocationData(cityName);
        
    }

    public static JSONArray getLocationData(String cityName) {
        cityName = cityName.replaceAll(" ", "+");

    //Build API URL with location parameters
    String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + 
    cityName + "&count=10&language=en&format=json";
        try {
            // call Api and get the response
            HttpURLConnection connection = fetchApiResponse(urlString);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


    private static HttpURLConnection fetchApiResponse(String urlString) {
    //Attempt to connect to the API
        try {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
        // Set the request method to GET
        //we are trying to GET the data from the API
        connection.setRequestMethod("GET");
        //connect to the API
        connection.connect();
        return connection;
        } catch(IOException e) {
            e.printStackTrace();
        }


    //could not connect to the API
    return null;
    }