package calculator.controller;

import calculator.model.CalculatorMode;
import calculator.view.localization.Language;

public interface ControllerListener {

    void updateDigitButtons(int base);

    void updateLanguage(Language language);

    void updateCalculatorMode(CalculatorMode calculatorMode);
}
