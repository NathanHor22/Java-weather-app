import javax.swing.*;

public class appLauncher {
    public static void main(String[] args) {
        //make sure GUI components are thread-safe to prevent bugs and issues
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            // Create and set up the GUI;
            //shortcut instead of creating a new instance of the GUI class
            // Make the GUI visible
            new weatherAppGUI().setVisible(true);
            }
        });

    }

}
