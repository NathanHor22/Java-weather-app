import javax.swing.JFrame;

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
    }

}
