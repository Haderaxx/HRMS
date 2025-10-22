package com.hrms.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DashboardFrame() {
        setTitle("HRMS - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel sidebar = new JPanel(new GridLayout(4, 1, 10, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JButton empBtn = new JButton("Employees");
        JButton perfBtn = new JButton("Performance");
        JButton logoutBtn = new JButton("Logout");

        sidebar.add(empBtn);
        sidebar.add(perfBtn);
        sidebar.add(logoutBtn);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new EmployeePanel(), "Employee");
        cardPanel.add(new PerformancePanel(), "Performance");

        empBtn.addActionListener(e -> cardLayout.show(cardPanel, "Employee"));
        perfBtn.addActionListener(e -> cardLayout.show(cardPanel, "Performance"));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }
}
