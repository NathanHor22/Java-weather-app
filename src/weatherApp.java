import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class weatherApp {
    //need longitude and latitude to get the weather data
    //get longitude and latitude data from the OpenWeatherMap API
    //input that into the weather API

    public static JSONObject getWeatherData(String cityName) {
        // Create a new JSONObject to hold the weather data
        JSONArray locationData = getLocationData(cityName);

        return null;
    }

    public static JSONArray getLocationData(String cityName) {
        cityName = cityName.replaceAll(" ", "+");

    //Build API URL with location parameters
    String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + 
    cityName + "&count=10&language=en&format=json";
        try {
            // call Api and get the response
            HttpURLConnection connection = fetchApiResponse(urlString);

            //check response code
            //if 200 means successful connection
            if(connection.getResponseCode() !=200) {
                System.out.println("Error: Unable to connect to the API. Response code: " + connection.getResponseCode());
                return null;
            } else {
                //store the API results
                StringBuilder resultJSON = new StringBuilder();
                //Uses scanner class to read the response from the API
                Scanner scanner = new Scanner(connection.getInputStream());

                //read and store the resulting jsone data into our StringBuilder
                while(scanner.hasNext()) {
                    resultJSON.append(scanner.nextLine());
                }

                //close the scanner
                scanner.close();

                //close url connection
                connection.disconnect();

                //parse JSON string into JSON obj
                JSONParser parser = new JSONParser();
                JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJSON));

                //get the list of the location data the API generated
                JSONArray locationData = (JSONArray) resultJsonObj.get("results");
                return locationData;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        //could not get location data
        return null;    
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
}
