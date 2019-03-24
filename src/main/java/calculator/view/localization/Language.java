package calculator.view.localization;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum Language {

    ENGLISH("/lang/en_US.properties", StandardCharsets.UTF_8,
            LanguageProperties.getLanguageName("language.english")),
    RUSSIAN("/lang/ru_RU.properties", StandardCharsets.UTF_8,
            LanguageProperties.getLanguageName("language.russian"));

    static final Language DEFAULT_LANGUAGE = Language.ENGLISH;

    private String localizationFile;
    private Charset charset;
    private String languageName;

    Language(String localizationFile, Charset charset, String languageName) {
        this.localizationFile = localizationFile;
        this.charset = charset;
        this.languageName = languageName;
    }

    public String getLocalizationFile() {
        return localizationFile;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getLanguageName() {
        return languageName;
    }
}
