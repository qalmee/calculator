package calculator.model;

import calculator.model.calculatorStats.CalculatorMode;
import calculator.model.calculatorStats.CalculatorOperation;
import calculator.model.configuration.Config;
import calculator.model.memory.MemoryOperation;
import calculator.model.numbers.Number;
import calculator.model.observer.CalculatorObserver;
import calculator.model.utils.ConverterPToP;
import calculator.model.utils.Exceptions.DivisionByZeroException;
import calculator.model.utils.NumberConverter;
import calculator.view.localization.Language;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel {

    private static final int MAX_BASE = 16;
    private int currentBase = 10;

    private CalculatorObserver calculatorObserver;

    public void setCalculatorObserver(CalculatorObserver calculatorObserver) {
        this.calculatorObserver = calculatorObserver;
    }

    private void resetModel() {
        currentBase = 10;
    }

    public void updateDigitButtons(int base) {
        List<String> buttonsToUpdate = new ArrayList<>(MAX_BASE - base);
        for (int i = base; i < MAX_BASE; ++i) {
            buttonsToUpdate.add(Integer.toString(i, 16).toUpperCase());
        }
        calculatorObserver.updateDigitButtons(buttonsToUpdate);
    }

    public void setLanguageToConfig(Language language) {
        Config.setLanguage(language);
    }

    public void setCalculatorModeToConfig(CalculatorMode calculatorMode) {
        ControlUnit.INSTANCE.resetCalculator();
        resetModel();
        Config.setCalculatorMode(calculatorMode);
        calculatorObserver.updateCalculatorMode(calculatorMode);
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void readConfigInformation() {
        calculatorObserver.updateCalculatorMode(Config.getCalculatorMode());
    }

    public void readLanguageFromConfig() {
        calculatorObserver.updateLanguage(Config.getLanguage());
    }

    public void copyValueToClipboard() {
        calculatorObserver.copyValueToClipboard();
    }

    public void pasteValueFromClipboard() {
        calculatorObserver.pasteValueFromClipboard();
    }

    @SuppressWarnings("Duplicates")
    public void operationPressed(String valueOnDisplay, CalculatorOperation operation, CalculatorMode calculatorMode) {
        valueOnDisplay = commasToDots(valueOnDisplay);
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            valueOnDisplay = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, currentBase);
        }
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode);

        try {
            ControlUnit.INSTANCE.operatorPressed(number, operation);
        } catch (DivisionByZeroException e) {
            calculatorObserver.setErrorState();
            calculatorObserver.clearResultAfterEnteringDigit();
            calculatorObserver.setResult(e.getMessage());
            setHistoryOnDisplay(calculatorMode);
            ControlUnit.INSTANCE.resetCalculator();
            return;
        }

        calculatorObserver.clearResultAfterEnteringDigit();
        if (ControlUnit.INSTANCE.needToSetResult()) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.resultIsSet();
        }
        setHistoryOnDisplay(calculatorMode);
        calculatorObserver.setBackSpaceEnabled(false);
    }

    @SuppressWarnings("Duplicates")
    public void equalsPressed(String valueOnDisplay, CalculatorMode calculatorMode) {
        valueOnDisplay = commasToDots(valueOnDisplay);
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            valueOnDisplay = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, currentBase);
        }
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode);
        try {
            ControlUnit.INSTANCE.equalsPressed(number);
        } catch (DivisionByZeroException e) {
            calculatorObserver.setErrorState();
            calculatorObserver.clearResultAfterEnteringDigit();
            calculatorObserver.setResult(e.getMessage());
            setHistoryOnDisplay(calculatorMode);
            ControlUnit.INSTANCE.resetCalculator();
            return;
        }

        calculatorObserver.clearResultAfterEnteringDigit();
        if (ControlUnit.INSTANCE.needToSetResult()) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.resultIsSet();
        }
        calculatorObserver.setPreviousOperationText("");
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void memoryOperationPressed(String valueOnDisplay, MemoryOperation operation, CalculatorMode calculatorMode) {
        valueOnDisplay = commasToDots(valueOnDisplay);
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            valueOnDisplay = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, currentBase);
        }
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode);
        ControlUnit.INSTANCE.memoryOperationPressed(number, operation);
        if (operation.equals(MemoryOperation.MEMORY_READ)) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.enteringNewValue();
        }
        calculatorObserver.clearResultAfterEnteringDigit();

    }

    public void digitButtonPressed() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clear() {
        ControlUnit.INSTANCE.resetCalculator();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clearEntry() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void convertAll(String valueOnDisplay, int oldBase, int newBase) {
        valueOnDisplay = commasToDots(valueOnDisplay);
        String result = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, oldBase); //todo: precision ?
        result = ConverterPToP.convert10ToPAdaptive(result, newBase);
        calculatorObserver.setResult(dotsToCommas(result));
        calculatorObserver.setPreviousOperationText(dotsToCommas(LocalHistory.INSTANCE.toString(newBase)));
        currentBase = newBase;
    }

    private String dotsToCommas(String s) {
        return s.replaceAll("\\.", ",");
    }

    private String commasToDots(String s) {
        return s.replaceAll(",", ".");
    }

    private void setHistoryOnDisplay(CalculatorMode calculatorMode) {
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            calculatorObserver.setPreviousOperationText(dotsToCommas(LocalHistory.INSTANCE.toString(currentBase)));
        } else {
            calculatorObserver.setPreviousOperationText(dotsToCommas(LocalHistory.INSTANCE.toString()));
        }
    }
}
