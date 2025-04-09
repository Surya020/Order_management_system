package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnUtil {
    public static Connection getConnection(String connectionString) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionString, "root", "surya@02");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 