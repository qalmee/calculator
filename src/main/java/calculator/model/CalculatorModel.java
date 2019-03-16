package calculator.model;

import calculator.model.observer.CalculatorObserver;

public class CalculatorModel {

    private CalculatorObserver calculatorObserver;

    public void setCalculatorObserver(CalculatorObserver calculatorObserver) {
        this.calculatorObserver = calculatorObserver;
    }
}
