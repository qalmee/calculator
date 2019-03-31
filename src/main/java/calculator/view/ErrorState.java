package calculator.view;

import calculator.view.localization.LanguageProperties;

public enum ErrorState {

    DIVISION_BY_ZERO(LanguageProperties.getProperty("calculator_scene.error_division_by_zero")),
    WRONG_DATA(LanguageProperties.getProperty("calculator_scene.error_wrong_data"));

    String errorStateText;

    ErrorState(String errorStateText) {
        this.errorStateText = errorStateText;
    }

    public String getErrorStateText() {
        return errorStateText;
    }
}
