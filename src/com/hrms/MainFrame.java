import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("HRMS – Frontend");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuManage = new JMenu("Manage");
        JMenuItem miEmployees = new JMenuItem("Employees");
        JMenuItem miDepartments = new JMenuItem("Departments");
        menuManage.add(miEmployees);
        menuManage.add(miDepartments);
        menuBar.add(menuManage);
        setJMenuBar(menuBar);

        // Content panel with card layout
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        EmployeesPanel employeesPanel = new EmployeesPanel();
        DepartmentsPanel departmentsPanel = new DepartmentsPanel();

        contentPanel.add(employeesPanel, "Employees");
        contentPanel.add(departmentsPanel, "Departments");

        // Default view
        cardLayout.show(contentPanel, "Employees");

        // Menu item actions
        miEmployees.addActionListener(e -> cardLayout.show(contentPanel, "Employees"));
        miDepartments.addActionListener(e -> cardLayout.show(contentPanel, "Departments"));

        add(contentPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
