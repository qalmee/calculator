package calculator.controller;

import calculator.model.CalculatorModel;

public class Controller implements ControllerListener {

    private CalculatorModel calculatorModel;

    public Controller(CalculatorModel calculatorModel) {
        this.calculatorModel = calculatorModel;
    }

    @Override
    public void updateDigitButtons(int base) {
        calculatorModel.updateDigitButtons(base);
    }
}
