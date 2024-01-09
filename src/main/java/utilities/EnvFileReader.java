package utilities;

import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for reading environment-specific properties from a properties file.
 * The properties file to load is determined by the "env" system property, with "prod" as the default.
 * If the properties file cannot be loaded, a runtime exception is thrown.
 */
public class EnvFileReader {
    private static Properties properties = new Properties();

    /**
     * Static initialization block to load environment-specific properties from a properties file.
     * The properties file to load is determined by the "env" system property, with "prod" as the default.
     * If the properties file cannot be loaded, a runtime exception is thrown.
     */
    static {
        String envFile = System.getProperty("env");
        if (envFile == null) {
            envFile = "prod";
        }
        String filePath = envFile.concat(".properties");

        try {
            properties.load(EnvFileReader.class.getClassLoader().getResourceAsStream(filePath));
        } catch (IOException e) {
            System.out.println("Failed to read properties file!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the value associated with a specified key from the loaded properties.
     *
     * @param key The key for which the corresponding value is to be retrieved.
     * @return The value associated with the specified key as a String, or null if the key is not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
