package calculator.controller;

import calculator.model.CalculatorMode;
import calculator.model.CalculatorModel;
import calculator.model.CalculatorOperation;
import calculator.model.MemoryOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.view.localization.Language;

public class Controller implements ControllerListener {

    private CalculatorModel calculatorModel;

    public Controller(CalculatorModel calculatorModel) {
        this.calculatorModel = calculatorModel;
    }

    @Override
    public void setNewObserver(CalculatorObserver calculatorObserver) {
        calculatorModel.setCalculatorObserver(calculatorObserver);
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

    @Override
    public void actionButtonClicked(String number, CalculatorOperation operation, CalculatorMode mode) {
        calculatorModel.operationPressed(number, operation, mode);
    }

    @Override
    public void memoryButtonClicked(String number, MemoryOperation memoryOperation, CalculatorMode mode) {

    }

    @Override
    public void buttonEnterClicked(String number, CalculatorMode mode) {
        calculatorModel.equalsPressed(number, mode);
    }

    @Override
    public void buttonCopyClicked() {
        calculatorModel.copyValueToClipboard();
    }

    @Override
    public void buttonPasteClicked() {
        calculatorModel.pasteValueFromClipboard();
    }
}
