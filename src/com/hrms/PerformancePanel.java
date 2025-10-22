package com.hrms.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.hrms.dao.PerformanceDAO;
import com.hrms.model.PerformanceReview;

public class PerformancePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public PerformancePanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Employee", "Score", "Remarks"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadPerformance());
        btnPanel.add(refreshBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadPerformance();
    }

    private void loadPerformance() {
        model.setRowCount(0);
        try {
            PerformanceDAO dao = new PerformanceDAO();
            List<PerformanceReview> reviews = dao.getAllReviews();
            for (PerformanceReview r : reviews) {
                model.addRow(new Object[]{r.getId(), r.getEmployeeName(), r.getScore(), r.getRemarks()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load performance data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
