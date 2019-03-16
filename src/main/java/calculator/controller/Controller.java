package calculator.controller;

import calculator.model.CalculatorModel;

public class Controller implements ControllerListener {

    private CalculatorModel calculatorModel;

    public Controller(CalculatorModel calculatorModel) {
        this.calculatorModel = calculatorModel;
    }
}
