import java.awt.Font;
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
    }

}
