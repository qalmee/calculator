package calculator.view.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LanguageProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageProperties.class);
    private static final String LANGUAGE_PROPERTIES_FILE = "/lang/languages.properties";
    private static final Charset LANGUAGE_PROPERTIES_CHARSET = StandardCharsets.UTF_8;

    private static Properties defaultProperties;
    private static Properties properties;
    private static Properties languages;
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

    static synchronized String getLanguageName(String key) {
        if (languages == null) {
            initLanguagesPropertiesFile();
        }
        return languages.getProperty(key);
    }

    private static void initProperties() {
        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }
        properties = new Properties();
        defaultProperties = new Properties();
        loadPropertiesFile(properties, language);
        loadPropertiesFile(defaultProperties, Language.DEFAULT_LANGUAGE);
    }

    private static void initLanguagesPropertiesFile() {
        languages = new Properties();
        try (InputStream inputStream = LanguageProperties.class.getResourceAsStream(LANGUAGE_PROPERTIES_FILE);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, LANGUAGE_PROPERTIES_CHARSET)) {
            languages.load(inputStreamReader);
        } catch (IOException e) {
            LOGGER.error("Error loading language properties file " + LANGUAGE_PROPERTIES_FILE, e);
        }
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
