package calculator.view.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class LanguageProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageProperties.class);
    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;

    private static Properties defaultProperties;
    private static Properties properties;
    private static Language language;

    private LanguageProperties() {

    }

    public static void setLanguage(Language language) {
        LanguageProperties.language = language;
    }

    public static synchronized String getProperty(String key) {
        if (properties == null) {
            initProperties();
        }
        String value = properties.getProperty(key);
        return value != null ? value : defaultProperties.getProperty(key);
    }

    private static void initProperties() {
        if (language == null) {
            language = DEFAULT_LANGUAGE;
        }
        properties = new Properties();
        defaultProperties = new Properties();
        loadPropertiesFile(properties, language);
        loadPropertiesFile(defaultProperties, DEFAULT_LANGUAGE);
    }

    private static void loadPropertiesFile(Properties properties, Language language) {
        try (InputStream inputStream = LanguageProperties.class.getResourceAsStream(language.getLocalizationFile());
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, language.getCharset())) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            LOGGER.error("Error loading properties file " + language.getLocalizationFile(), e);
        }
    }
}
