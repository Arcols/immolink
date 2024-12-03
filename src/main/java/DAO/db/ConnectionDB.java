package DAO.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private static Connection cn;

    private ConnectionDB() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/immolink", // DB URL
                    "root", // Username
                    "" // Password
            );
            if (cn == null) {
                throw new SQLException("Failed to establish a connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    public static ConnectionDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (this.cn == null || this.cn.isClosed()) {
                // Re-establish the connection if it is closed
                this.cn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/immolink", // DB URL
                        "root", // Username
                        "" // Password
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.cn;
    }

    public void closeConnection() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            this.cn.close();
        }
    }

    public void setAutoCommit(boolean b) {
        try {
            this.cn.setAutoCommit(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            this.cn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}