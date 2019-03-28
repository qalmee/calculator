package calculator.controller;

import calculator.model.CalculatorMode;
import calculator.model.CalculatorOperation;
import calculator.view.localization.Language;

public interface ControllerListener {

    void updateDigitButtons(int base);

    void updateLanguage(Language language);

    void updateCalculatorMode(CalculatorMode calculatorMode);

    void actionButtonClicked(String number, CalculatorOperation operation, CalculatorMode mode);

    void buttonEnterClicked(String number, CalculatorMode mode);
}
