package calculator.model;

import calculator.model.configuration.Config;
import calculator.model.memory.MemoryOperation;
import calculator.model.numbers.Number;
import calculator.model.observer.CalculatorObserver;
import calculator.model.observer.ComplexCalculatorObserver;
import calculator.model.observer.FractionCalculatorObserver;
import calculator.model.observer.PNumberCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.model.stats.ErrorState;
import calculator.model.utils.ConverterPToP;
import calculator.model.utils.NumberConverter;
import calculator.model.utils.exceptions.DivisionByZeroException;
import calculator.view.localization.Language;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel {

    private static final int MAX_BASE = 16;
    private static final int MAX_SCIENTIFIC_DIGITS = 30;
    private int currentBase = 10;

    private CalculatorObserver calculatorObserver;
    private FractionCalculatorObserver fractionCalculatorObserver;
    private ComplexCalculatorObserver complexCalculatorObserver;
    private PNumberCalculatorObserver pNumberCalculatorObserver;

    public void setCalculatorObserver(CalculatorObserver calculatorObserver) {
        this.calculatorObserver = calculatorObserver;
    }

    public void setFractionCalculatorObserver(FractionCalculatorObserver fractionCalculatorObserver) {
        this.fractionCalculatorObserver = fractionCalculatorObserver;
    }

    public void setComplexCalculatorObserver(ComplexCalculatorObserver complexCalculatorObserver) {
        this.complexCalculatorObserver = complexCalculatorObserver;
    }

    public void setPNumberCalculatorObserver(PNumberCalculatorObserver pNumberCalculatorObserver) {
        this.pNumberCalculatorObserver = pNumberCalculatorObserver;
    }

    private void resetModel() {
        currentBase = 10;
        calculatorObserver.disableMemoryButtons(true);
        calculatorObserver.setBackSpaceEnabled(true);
        ControlUnit.INSTANCE.resetCalculator();
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
        resetModel();
        Config.setCalculatorMode(calculatorMode);
        calculatorObserver.updateCalculatorMode(calculatorMode);
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

    public void operationPressed(String valueOnDisplay, CalculatorOperation operation, CalculatorMode calculatorMode) {
        Number number = prepareStringBeforeCalc(valueOnDisplay, calculatorMode);

        if (!performOperator(number, calculatorMode, operation)) {
            return;
        }

        if (ControlUnit.INSTANCE.needToSetResult()) {
            setResult(calculatorMode);
        }
        calculatorObserver.setBackSpaceEnabled(false);
        calculatorObserver.clearResultAfterEnteringDigit();
        setHistoryOnDisplay(calculatorMode);
    }

    public void equalsPressed(String valueOnDisplay, CalculatorMode calculatorMode) {
        Number number = prepareStringBeforeCalc(valueOnDisplay, calculatorMode);

        if (!performEquals(number, calculatorMode)) {
            return;
        }
        if (ControlUnit.INSTANCE.needToSetResult()) {
            setResult(calculatorMode);
        }
        calculatorObserver.clearResultAfterEnteringDigit();
        calculatorObserver.setPreviousOperationText("");
    }

    public void memoryOperationPressed(String valueOnDisplay, MemoryOperation operation, CalculatorMode calculatorMode) {
        Number number = prepareStringBeforeCalc(valueOnDisplay, calculatorMode);

        ControlUnit.INSTANCE.memoryOperationPressed(number, operation);

        if (operation.equals(MemoryOperation.MEMORY_READ) && ControlUnit.INSTANCE.getResultValue() != null) {
            setResult(calculatorMode);
        }
        calculatorObserver.clearResultAfterEnteringDigit();
    }

    public void displayTextActionHappened() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clear() {
        resetModel();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clearEntry() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
        LocalHistory.INSTANCE.popOperand();
        calculatorObserver.setPreviousOperationText(LocalHistory.INSTANCE.toString(currentBase));
    }

    public void convertAll(String valueOnDisplay, int oldBase, int newBase) {
        valueOnDisplay = NumberConverter.fromScientific(commasToDots(valueOnDisplay));
        valueOnDisplay = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, oldBase);
        valueOnDisplay = ConverterPToP.convert10ToPAdaptive(valueOnDisplay, newBase);
        calculatorObserver.setResult(dotsToCommas(NumberConverter.toScientific(valueOnDisplay, MAX_SCIENTIFIC_DIGITS, CalculatorMode.P_NUMBER)));
        calculatorObserver.setPreviousOperationText(dotsToCommas(LocalHistory.INSTANCE.toString(newBase)));
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

    public void parseClipboardString(String data, CalculatorMode calculatorMode) {
        if (data.isEmpty()) {
            return;
        }
        data = data.toUpperCase();
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            if (!checkStringBeforeParse(data)) {
                setErrorState(ErrorState.WRONG_DATA, calculatorMode);
                return;
            }
        } else {
            Number number;
            try {
                number = NumberConverter.stringToNumber(data, calculatorMode);
            } catch (RuntimeException e) {
                setErrorState(ErrorState.WRONG_DATA, calculatorMode);
                return;
            }
            data = number.toString();
        }
        if (data.length() > MAX_SCIENTIFIC_DIGITS) {
            data = NumberConverter.toScientific(data, MAX_SCIENTIFIC_DIGITS, calculatorMode);
        }
        displayTextActionHappened();
        calculatorObserver.setResult(data);
    }

    public void setBase(int base) {
        currentBase = base;
    }

    private boolean checkStringBeforeParse(String data) {
        char digits = (char) ('0' + (currentBase > 10 ? 9 : currentBase - 1));
        char letters = (char) ('A' + currentBase - 11);
        return data.chars().allMatch(
                ch -> (ch >= '0' && ch <= '9' && ch <= digits)
                        || (ch >= 'A' && ch <= 'F' && ch <= letters));
    }

    private void setResult(CalculatorMode calculatorMode) {
        String result = ControlUnit.INSTANCE.getResultValue().toString();
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
        }
        if (result.length() > MAX_SCIENTIFIC_DIGITS) {
            result = NumberConverter.toScientific(result, MAX_SCIENTIFIC_DIGITS, calculatorMode);
        }

        calculatorObserver.setResult(dotsToCommas(result));
        ControlUnit.INSTANCE.resultIsSet();

        calculatorObserver.setBackSpaceEnabled(false);
    }

    private boolean performEquals(Number number, CalculatorMode calculatorMode) {
        try {
            ControlUnit.INSTANCE.equalsPressed(number);
        } catch (DivisionByZeroException e) {
            setErrorState(ErrorState.DIVISION_BY_ZERO, calculatorMode);
            return false;
        }
        return true;
    }

    private boolean performOperator(Number number, CalculatorMode calculatorMode, CalculatorOperation calculatorOperation) {
        try {
            ControlUnit.INSTANCE.operatorPressed(number, calculatorOperation);
        } catch (DivisionByZeroException e) {
            setErrorState(ErrorState.DIVISION_BY_ZERO, calculatorMode);
            return false;
        }
        return true;
    }

    private Number prepareStringBeforeCalc(String valueOnDisplay, CalculatorMode calculatorMode) {
        valueOnDisplay = NumberConverter.fromScientific(commasToDots(valueOnDisplay));
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            valueOnDisplay = ConverterPToP.convertPTo10Adaptive(valueOnDisplay, currentBase);
        }
        return NumberConverter.stringToNumber(valueOnDisplay, calculatorMode);
    }

    private void setErrorState(ErrorState state, CalculatorMode calculatorMode) {
        calculatorObserver.setErrorState(state);
        calculatorObserver.clearResultAfterEnteringDigit();
        setHistoryOnDisplay(calculatorMode);
        resetModel();
    }

}
