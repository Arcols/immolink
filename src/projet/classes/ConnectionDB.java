package projet.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private Connection cn = null;

    public ConnectionDB() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            cn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/imolink", // Database URL
                "root", // Username
                "" // Password
            );
            if (cn == null) {
                throw new SQLException("Failed to establish a connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to inform the caller
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    // Method to get the connection
    public Connection getConnection() {
        return this.cn;
    }

    // Method to close the connection
    public void closeConnection() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            cn.close();
        }
    }
}
