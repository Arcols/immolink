package DAO.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection cn;

    public static Connection getInstance() {
        try {
            if (cn == null || cn.isClosed())
                create();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cn;
    }

    public static Connection create() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/immolink",
                    "root",
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cn;
    }

    public static void destroy() {
        try {
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cn = null;
    }

    public static void setAutoCommit(boolean b) {
        try {
            cn.setAutoCommit(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        try {
            cn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}