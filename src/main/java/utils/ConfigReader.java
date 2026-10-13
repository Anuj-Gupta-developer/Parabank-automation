package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Reads settings (URL, browser, headless, timeout) from config.properties on the classpath.
public class ConfigReader {

    private static final Properties properties = new Properties();

    // Loads config.properties once, when the class is first used.
    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in src/test/resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    // Returns the value for a key. A -Dkey=value passed to Maven overrides the file value.
    public static String get(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) {
            throw new RuntimeException("Missing key in config.properties: " + key);
        }
        return value.trim();
    }

    // Returns the value for a key as an int (used for the timeout).
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    // Returns the value for a key as a boolean (used for headless).
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
