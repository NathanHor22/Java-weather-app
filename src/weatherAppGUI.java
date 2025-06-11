import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class weatherAppGUI extends JFrame {
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

        //Search button
        JButton searchButton = new JButton(loadImage("C:\\Users\\damna\\Documents\\GitHub\\Java-weather-app\\assets\\search.png"));

        //change cursor to hand when hovering over the button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(335, 13, 47, 45);
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
