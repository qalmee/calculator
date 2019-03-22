package calculator.view.localization;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum Language {

    ENGLISH("/lang/en_US.properties", StandardCharsets.UTF_8),
    RUSSIAN("/lang/ru_RU.properties", StandardCharsets.UTF_8);

    private String localizationFile;
    private Charset charset;

    Language(String localizationFile, Charset charset) {
        this.localizationFile = localizationFile;
        this.charset = charset;
    }

    public String getLocalizationFile() {
        return localizationFile;
    }

    public Charset getCharset() {
        return charset;
    }
}
