package calculator.controller;

import calculator.model.CalculatorMode;
import calculator.model.CalculatorModel;
import calculator.view.localization.Language;

public class Controller implements ControllerListener {

    private CalculatorModel calculatorModel;

    public Controller(CalculatorModel calculatorModel) {
        this.calculatorModel = calculatorModel;
    }

    @Override
    public void updateDigitButtons(int base) {
        calculatorModel.updateDigitButtons(base);
    }

    @Override
    public void updateLanguage(Language language) {
        calculatorModel.setLanguageToConfig(language);
    }

    @Override
    public void updateCalculatorMode(CalculatorMode calculatorMode) {
        calculatorModel.setCalculatorModeToConfig(calculatorMode);
    }
}
