package calculator.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LanguageProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageProperties.class);
    private static final String PROPERTIES_FILE = "/lang/en_US.properties";

    private static Properties properties;

    private LanguageProperties() {

    }

    public static synchronized String getProperty(String key) {
        if (properties == null) {
            initProperties();
        }
        return properties.getProperty(key);
    }

    private static void initProperties() {
        properties = new Properties();
        try (InputStream inputStream = LanguageProperties.class.getResourceAsStream(PROPERTIES_FILE);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            LOGGER.error("Error loading properties file " + PROPERTIES_FILE, e);
        }
    }
}