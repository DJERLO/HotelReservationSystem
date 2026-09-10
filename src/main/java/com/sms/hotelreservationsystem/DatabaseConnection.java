package com.sms.hotelreservationsystem;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:test.db";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Babasahin ang schema.sql mula sa resources folder at ii-execute
     * ang mga CREATE TABLE IF NOT EXISTS statements.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            if (conn == null) return;

            // Kukunin ang schema.sql mula sa src/main/resources
            InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("schema.sql");
            if (inputStream == null) {
                System.err.println("Could not find schema.sql in resources folder!");
                return;
            }

            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            
            // I-execute ang SQL statements (hiwalay per semicolon)
            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement.trim());
                }
            }

            System.out.println("Database tables initialized successfully!");

        } catch (Exception e) {
            System.err.println("Failed to initialize database schema: " + e.getMessage());
        }
    }

    /**
     * Sample Method gamit ang Bind Parameters (?) para mag-add ng bagong kwarto
     */
    public static boolean addRoom(String roomNumber, String roomType, double pricePerNight) {
        String query = "INSERT INTO rooms (room_number, room_type, price_per_night) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Bind Parameters
            pstmt.setString(1, roomNumber);
            pstmt.setString(2, roomType);
            pstmt.setDouble(3, pricePerNight);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }
}