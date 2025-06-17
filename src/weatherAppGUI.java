import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

public class weatherAppGUI extends JFrame {

    private JSONObject weatherData;
    
    public weatherAppGUI() {

        //setting up the GUI and adding title
        super("Weather Application");

        //size of the GUI
        setSize(400, 600);

        //configure GUI to close the program's process when closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //load gui at the centre of the screen
        setLocationRelativeTo(null);

        //allows components to be manually positioned within the frame
        //instead of using a layout manager
        setLayout(null);

        //prevent resizing of the GUI
        setResizable(false);

        addGUIComponents();
    }

    private void addGUIComponents() {
        // Add components to the GUI here
        // For example, you can add buttons, labels, text fields, etc.
        // Example: JButton button = new JButton("Click Me");
        // add(button);

        JTextField searchField = new JTextField();

        //set the location and size of the text field
        searchField.setBounds(25, 25, 300, 30);

        //font style and size
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));

        //add the text field to the GUI
        add(searchField);

   

        //add weather icon
        JLabel weatherIcon = new JLabel(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\cloudy.png"));
        //centre the icon in the GUI
        weatherIcon.setBounds(50, 100, 300, 300);
        add(weatherIcon);

        //add temperature text
        JLabel temperatureLabel = new JLabel("20°C");
        //set font style and size
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 20));
        temperatureLabel.setBounds(50, 360, 300, 30);

        //centre the temperature label in the GUI
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureLabel);

        //add weather description text
        JLabel weatherDescriptionLabel = new JLabel("Partly Cloudy");
        weatherDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        weatherDescriptionLabel.setBounds(50, 400, 300, 30);
        add(weatherDescriptionLabel);
        //centre the weather description label in the GUI
        weatherDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //add humidity icon
        JLabel humidityIcon = new JLabel(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\humidity.png"));
        humidityIcon.setBounds(10, 450, 130, 130);
        add(humidityIcon);
        //place the humidity icon to the left
        humidityIcon.setHorizontalAlignment(SwingConstants.LEFT);

        //add humidity text, setting it bold font
        JLabel humidityLabel = new JLabel("<html><b>Humidity:</b> 60% </html>");
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        humidityLabel.setBounds(90, 490, 75, 40);
        add(humidityLabel);

        //add windspeed icon
        JLabel windSpeedIcon = new JLabel(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\windspeed.png"));
        windSpeedIcon.setBounds(130, 450, 130, 130);
        add(windSpeedIcon);
        //place the windspeed icon to the right
        windSpeedIcon.setHorizontalAlignment(SwingConstants.RIGHT);

        //add windspeed text, setting it bold font
        JLabel windSpeedLabel = new JLabel("<html><b>Wind Speed:</b> 15 km/h </html>");
        windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        windSpeedLabel.setBounds(270, 490, 95, 40);
        add(windSpeedLabel);


        //Search button
        JButton searchButton = new JButton(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\search.png"));

        //change cursor to hand when hovering over the button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(335, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the city name from the search field
                String userInput = searchTextField.getText();

                //validate user input and remove white space to prevent non-empty text
                if(userInput.replaceAll("\\s","").length() <= 0) {
                    return;
                }

                //retrive weather data for the specified city
                weatherData = weatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weatherCondition");

                //weather image updates according to the weather condition
                switch(weatherCondition) {
                    case "Clear":
                        weatherIcon.setIcon(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\sunny.png"));
                        break;
                    case "Clouds":
                        weatherIcon.setIcon(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\cloudy.png"));
                        break;
                    case "Rain":
                        weatherIcon.setIcon(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\rainy.png"));
                        break;
                    case "Snow":
                        weatherIcon.setIcon(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\snowy.png"));
                        break;
                }

                //update temperature
                double temperature = (double) weatherData.get("temperature");
                temperatureLabel.setText(temperature + "C°");

                //update humidity
                long humidity = (long) weatherData.get("humidity");
                humidityLabel.setText("<html><b>Humidity:</b> " + humidity + "%</html>");


                //update wind speed
                double windSpeed = (double) weatherData.get("windSpeed");
                windSpeedLabel.setText("<html><b>Windpeed:</b> " + windSpeed + " km/h</html>");
            }
        });


        add(searchButton);
    }

    // create images in the gui components
    private ImageIcon loadImage(String resourcePath) {
        try{
            //Read the image from the specified path
            BufferedImage image = ImageIO.read(new File(resourcePath));  
            
            //return image icon so the gui component can render it
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not find resources at " + resourcePath);
        return null;
    }  

}
