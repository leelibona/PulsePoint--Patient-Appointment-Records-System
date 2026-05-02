package com.pulsepoint.creational_patterns.singleton;

/**
 * Singleton Pattern — Database Connection
 *
 * Purpose: Ensures only one database connection instance exists
 * throughout the entire PulsePoint application lifecycle.
 *
 * Use Case in PulsePoint: The system must maintain a single shared
 * connection to the PostgreSQL database. Multiple connections would
 * waste resources and risk data inconsistency. The Singleton pattern
 * guarantees exactly one connection is created and reused everywhere.
 *
 * Thread Safety: Uses double-checked locking to ensure thread safety
 * when multiple threads try to access the instance simultaneously.
 */
public class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private String connectionUrl;
    private String username;
    private boolean isConnected;
    private int queryCount;

    // Private constructor prevents direct instantiation
    private DatabaseConnection() {
        this.connectionUrl = "jdbc:postgresql://localhost:5432/pulsepoint";
        this.username = "pulsepoint_admin";
        this.isConnected = false;
        this.queryCount = 0;
        connect();
    }

    /**
     * Returns the single instance of DatabaseConnection.
     * Uses double-checked locking for thread safety.
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                    System.out.println("New DatabaseConnection instance created.");
                }
            }
        }
        return instance;
    }

    private void connect() {
        // Simulate connecting to PostgreSQL
        this.isConnected = true;
        System.out.println("Connected to database: " + connectionUrl);
    }

    public void disconnect() {
        this.isConnected = false;
        System.out.println("Disconnected from database.");
    }

    public String executeQuery(String sql) {
        if (!isConnected) {
            throw new IllegalStateException("Database is not connected.");
        }
        queryCount++;
        System.out.println("Executing query #" + queryCount + ": " + sql);
        return "Query result for: " + sql;
    }

    public boolean isConnected() { return isConnected; }
    public String getConnectionUrl() { return connectionUrl; }
    public int getQueryCount() { return queryCount; }

    @Override
    public String toString() {
        return "DatabaseConnection{url='" + connectionUrl +
               "', connected=" + isConnected +
               ", queryCount=" + queryCount + "}";
    }
}