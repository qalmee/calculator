package calculator.model.configuration;

import calculator.model.CalculatorMode;
import calculator.view.localization.Language;

import java.util.prefs.Preferences;

public class Config {

    private static final String APPLICATION_NODE_NAME = "calculator";
    private static final String LANGUAGE_KEY = "language";
    private static final String CALCULATOR_MODE_KEY = "calculator_mode";
    private static final String DEFAULT_LANGUAGE_KEY = "ENGLISH";
    private static final String DEFAULT_CALCULATOR_MODE_KEY = "FRACTION";

    private static Preferences preferences;

    static {
        Preferences root = Preferences.userRoot();
        preferences = root.node(APPLICATION_NODE_NAME);
    }

    private Config() {

    }

    public static Language getLanguage() {
        String language = preferences.get(LANGUAGE_KEY, DEFAULT_LANGUAGE_KEY);
        return Language.valueOf(language);
    }

    public static void setLanguage(Language language) {
        preferences.put(LANGUAGE_KEY, language.toString());
    }

    public static CalculatorMode getCalculatorMode() {
        String calculatorMode = preferences.get(CALCULATOR_MODE_KEY, DEFAULT_CALCULATOR_MODE_KEY);
        return CalculatorMode.valueOf(calculatorMode);
    }

    public static void setCalculatorMode(CalculatorMode calculatorMode) {
        preferences.put(CALCULATOR_MODE_KEY, calculatorMode.toString());
    }
}
