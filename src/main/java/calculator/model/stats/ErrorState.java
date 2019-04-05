package calculator.model.stats;

import static calculator.view.localization.LanguageProperties.getProperty;

public enum ErrorState {

    DIVISION_BY_ZERO(getProperty("calculator_scene.error_division_by_zero")),
    WRONG_DATA(getProperty("calculator_scene.error_wrong_data"));

    String errorStateText;

    ErrorState(String errorStateText) {
        this.errorStateText = errorStateText;
    }

    public String getErrorStateText() {
        return errorStateText;
    }
}
