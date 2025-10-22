import javax.swing.*;
import com.hrms.ui.LoginFrame;

/**
 * HRMS Application Launcher
 * This is the main entry point for the enhanced HRMS application
 */
public class HRMSLauncher {
    
    public static void main(String[] args) {
        // Set system look and feel - using default for compatibility
        
        // Set application properties
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Start the application with login screen
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error starting HRMS application: " + e.getMessage(), 
                    "Startup Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
