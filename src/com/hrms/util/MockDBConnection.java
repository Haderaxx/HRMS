package com.hrms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Mock database connection for testing when MySQL is not available
 * This creates an in-memory H2 database for testing purposes
 */
public class MockDBConnection {
    private static final String URL = "jdbc:h2:mem:hrms_test;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load H2 driver
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Create tables
            createTables(conn);
            
        } catch (ClassNotFoundException e) {
            System.out.println("H2 Driver not found! Please add H2 jar to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
    
    private static void createTables(Connection conn) throws SQLException {
        // Create employees table
        conn.createStatement().execute(
            "CREATE TABLE IF NOT EXISTS employees (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(100) NOT NULL, " +
            "department VARCHAR(50) NOT NULL, " +
            "salary DECIMAL(10, 2) NOT NULL" +
            ")"
        );
        
        // Create attendance table
        conn.createStatement().execute(
            "CREATE TABLE IF NOT EXISTS attendance (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "employee_id INT NOT NULL, " +
            "date DATE NOT NULL, " +
            "status VARCHAR(20) NOT NULL" +
            ")"
        );
        
        // Insert sample data
        conn.createStatement().execute(
            "INSERT INTO employees (name, department, salary) VALUES " +
            "('John Doe', 'IT', 75000.00), " +
            "('Jane Smith', 'HR', 65000.00), " +
            "('Alice Johnson', 'Finance', 70000.00)"
        );
        
        conn.createStatement().execute(
            "INSERT INTO attendance (employee_id, date, status) VALUES " +
            "(1, '2024-01-15', 'Present'), " +
            "(1, '2024-01-16', 'Present'), " +
            "(2, '2024-01-15', 'Present'), " +
            "(2, '2024-01-16', 'Absent'), " +
            "(3, '2024-01-15', 'Present')"
        );
    }
}

