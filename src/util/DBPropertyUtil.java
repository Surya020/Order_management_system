package util;

import java.io.FileInputStream;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString(String propertyFileName) {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(propertyFileName);
            properties.load(input);
            String url = properties.getProperty("db.url");
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 