package com.company.gorest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                System.exit(1);
            }
            // Load the properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getAuthToken() {
        return properties.getProperty("auth.token");
    }
}