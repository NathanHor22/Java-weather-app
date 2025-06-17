import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        JSONObject city = (JSONObject) locationData.get(0);
        double latitude = (double) city.get("latitude");
        double longitude = (double) city.get("longitude");

        //build API request URL with the location data
        String urlString = "https://my-server.tld/v1/forecast?" + 
        "latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m&timezone=Europe%2FLondon";
        
        try{
            
            //call API and get response
            HttpURLConnection connection = fetchApiResponse(urlString);

            //check response code
            //if 200 means successful connection

            if(connection.getResponseCode() != 200) {
                System.out.println("Error: Unable to connect to the API. Response code: " + connection.getResponseCode());
                return null;
            }

                //store the API results
                StringBuilder resultJSON = new StringBuilder();
                //Uses scanner class to read the response from the API
                Scanner scanner = new Scanner(connection.getInputStream());

                //read and store the resulting json data into our StringBuilder
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


                //retrieve hourly data from the JSON object
                JSONArray hourly = (JSONArray) resultJsonObj.get("hourly");

                //retrieve the time data from the JSON object
                JSONObject timeData = (JSONObject) resultJsonObj.get("time");
                int index = findIndexOfCurrentTime(timeData);

                //get temperature data from the hourly data
                JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
                double temperature = (double) temperatureData.get(index);

                //get weather code data from the hourly data
                JSONArray weatherCodeData = (JSONArray) hourly.get("weather_code");
                String weatherCondition = convertWeatherCode((long) weatherCodeData.get(index));

                //get humidity data from the hourly data
                JSONArray humidityData = (JSONArray) hourly.get("relative_humidity_2m");
                long humidity = (long) humidityData.get(index);

                //get wind speed data from the hourly data
                JSONArray windSpeedData = (JSONArray) hourly.get("windspeed_10m");
                double windSpeed = (double) windSpeedData.get(index);

                //create a new JSON object to hold the weather data for our frontend
                JSONObject weatherData = new JSONObject();
                weatherData.put("temperature", temperature);
                weatherData.put("weatherCondition", weatherCondition);
                weatherData.put("humidity", humidity);
                weatherData.put("windSpeed", windSpeed);

                return weatherData;

        } catch(Exception e) {
            e.printStackTrace();
        }
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

    private static int findIndexOfCurrentTime(JSONObject timeData) {
        String currentTime = getCurrentTime();

        //iterate through the time data to find the index of the current time
        for(int i = 0; i < timeData.size(); i++) {
            String time = (String) timeData.get(i);
            if(time.equalsIgnoreCase(currentTime)) {
                return i;
            }
        }
        return 0;
    }


    public static String getCurrentTime() {
        // Get the current time in the format "yyyy-MM-dd'T'HH:mm:ss"
        LocalDateTime currentDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");

        // format and print current date and time
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    private static String convertWeatherCode(long weatherCode) {
        // Convert the weather code to something more readable
        //documentation to show the weather codes are available at 
        //https://www.nodc.noaa.gov/archive/arc0021/0002199/1.1/data/0-data/HTML/WMO-CODE/WMO4677.HTM
        String weatherCondition = "";
            if(weatherCode == 0L) {
                //clear
                weatherCondition = "Clear";
            } else if(weatherCode > 0L && weatherCode <= 3L) {
                //cloudy
                weatherCondition = "Cloudy";
            } else if(weatherCode >= 51L && weatherCode <= 67L || (weatherCode >= 80L && weatherCode <= 99L)) {
                //rainy
                weatherCondition = "Rainy";
            } else if(weatherCode > 71L && weatherCode <= 77L) {
                //snowy
                weatherCondition = "Snowy";
            }
            return weatherCondition;
        }



}
