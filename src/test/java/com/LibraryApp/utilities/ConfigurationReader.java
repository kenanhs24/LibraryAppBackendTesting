package com.LibraryApp.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        try {
            // 1) Open the configuration file
            FileInputStream file = new FileInputStream("configuration.properties");

            // 2) Create a Properties object and load it with the file data
            properties = new Properties();
            properties.load(file);

            // 3) Close the file
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            // If there's an error loading the file, the properties object might remain null
            // or incomplete, so handle that appropriately.
        }
    }

    /**
     * Returns the property value associated with the given key.
     * @param keyName The key whose property value you want
     * @return The value of the property, or null if not found
     */
    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }

    /**
     * A second method that does the same thing, returning the value for the given key.
     * Some frameworks prefer the name getProperty(...) so it's included here for compatibility.
     */
    public static String getProperty(String keyName) {
        return properties.getProperty(keyName);
    }
}
