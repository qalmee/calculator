package calculator.model;

import calculator.model.configuration.Config;
import calculator.model.memory.Memory;
import calculator.model.memory.MemoryOperation;
import calculator.model.numbers.Complex;
import calculator.model.numbers.Number;
import calculator.model.observer.CalculatorObserver;
import calculator.model.observer.ComplexCalculatorObserver;
import calculator.model.observer.FractionCalculatorObserver;
import calculator.model.observer.PNumberCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.model.stats.CalculatorPrecision;
import calculator.model.stats.ErrorState;
import calculator.model.utils.ConverterPToP;
import calculator.model.utils.NumberConverter;
import calculator.model.utils.exceptions.DivisionByZeroException;
import calculator.model.utils.exceptions.OverflowException;
import calculator.view.localization.Language;

import java.util.ArrayList;
import java.util.List;

import static calculator.model.utils.NumberConverter.commasToDots;
import static calculator.model.utils.NumberConverter.dotsToCommas;

public class CalculatorModel {

    private static final int MAX_BASE = 16;
    private static final int MAX_SCIENTIFIC_DIGITS_REAL = CalculatorPrecision.SCIENTIFIC_DIGITS_REAL.getPrecision();
    private static final int MAX_SCIENTIFIC_DIGITS_FRACTION = CalculatorPrecision.SCIENTIFIC_DIGITS_FRACTION.getPrecision();
    private int currentBase = 10;

    private CalculatorObserver calculatorObserver;
    private FractionCalculatorObserver fractionCalculatorObserver;
    private ComplexCalculatorObserver complexCalculatorObserver;
    private PNumberCalculatorObserver pNumberCalculatorObserver;

    public void setCalculatorObserver(CalculatorObserver calculatorObserver) {
        this.calculatorObserver = calculatorObserver;
        Memory.INSTANCE.memoryClear();
        resetModel();
    }

    public void setFractionCalculatorObserver(FractionCalculatorObserver fractionCalculatorObserver) {
        this.fractionCalculatorObserver = fractionCalculatorObserver;
    }

    public void setComplexCalculatorObserver(ComplexCalculatorObserver complexCalculatorObserver) {
        this.complexCalculatorObserver = complexCalculatorObserver;
    }

    public void setPNumberCalculatorObserver(PNumberCalculatorObserver pNumberCalculatorObserver) {
        this.pNumberCalculatorObserver = pNumberCalculatorObserver;
        currentBase = 10;
        if (pNumberCalculatorObserver != null) {
            pNumberCalculatorObserver.setBase(10);
        }
    }

    private void resetModel() {
        calculatorObserver.setBackSpaceEnabled(true);
        ControlUnit.INSTANCE.resetCalculator();
        toggleCaretIfComplexOrFraction();
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
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode, currentBase);

        if (operation.equals(CalculatorOperation.IM_NEGATE)) {
            changeImSignOperation(number);
            return;
        }

        if (!performOperator(number, calculatorMode, operation)) {
            return;
        }

        if (ControlUnit.INSTANCE.needToSetResult()) {
            try {
                setResult(calculatorMode);
            } catch (OverflowException e) {
                setErrorState(ErrorState.OVERFLOW, calculatorMode);
                return;
            }
        }
        calculatorObserver.setBackSpaceEnabled(false);
        calculatorObserver.clearResultAfterEnteringDigit();
        setHistoryOnDisplay(calculatorMode);
        toggleCaretIfComplexOrFraction();
    }

    public void equalsPressed(String valueOnDisplay, CalculatorMode calculatorMode) {
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode, currentBase);

        if (!performEquals(number, calculatorMode)) {
            return;
        }
        if (ControlUnit.INSTANCE.needToSetResult()) {
            try {
                setResult(calculatorMode);
            } catch (OverflowException e) {
                setErrorState(ErrorState.OVERFLOW, calculatorMode);
                return;
            }
        }
        calculatorObserver.clearResultAfterEnteringDigit();
        setHistoryOnDisplay(calculatorMode);
        toggleCaretIfComplexOrFraction();
    }

    public void memoryOperationPressed(String valueOnDisplay, MemoryOperation memoryOperation, CalculatorMode calculatorMode) {
        Number number = NumberConverter.stringToNumber(valueOnDisplay, calculatorMode, currentBase);

        ControlUnit.INSTANCE.memoryOperationPressed(number, memoryOperation);
        toggleMemoryButtons(memoryOperation);
        if (memoryOperation.equals(MemoryOperation.MEMORY_READ) && ControlUnit.INSTANCE.getResultValue() != null) {
            try {
                setResult(calculatorMode);
            } catch (OverflowException e) {
                setErrorState(ErrorState.OVERFLOW, calculatorMode);
                return;
            }
        }
        calculatorObserver.clearResultAfterEnteringDigit();
        toggleCaretIfComplexOrFraction();
    }

    public void displayTextActionHappened() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clear() {
        resetModel();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    public void clearEntry(CalculatorMode calculatorMode) {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
        LocalHistory.INSTANCE.popOperand();
        setHistoryOnDisplay(calculatorMode);
        toggleCaretIfComplexOrFraction();
    }

    public void convertAll(String valueOnDisplay, int oldBase, int newBase) {
        currentBase = oldBase;
        try {
            valueOnDisplay = parseStringToNumber(valueOnDisplay, CalculatorMode.P_NUMBER, oldBase).toString();
            valueOnDisplay = ConverterPToP.convert10ToPAdaptive(valueOnDisplay, newBase);
            valueOnDisplay = NumberConverter.toScientificIfNeeded(valueOnDisplay, CalculatorMode.P_NUMBER, MAX_SCIENTIFIC_DIGITS_REAL, MAX_SCIENTIFIC_DIGITS_FRACTION);
        } catch (OverflowException e) {
            setErrorState(ErrorState.OVERFLOW, CalculatorMode.P_NUMBER);
            return;
        } catch (RuntimeException e) {
            setErrorState(ErrorState.WRONG_DATA, CalculatorMode.P_NUMBER);
            return;
        }
        try {
            calculatorObserver.setResult(dotsToCommas(valueOnDisplay));
        } catch (OverflowException e) {
            setErrorState(ErrorState.OVERFLOW, CalculatorMode.P_NUMBER);
            return;
        }
        currentBase = newBase;
        setHistoryOnDisplay(CalculatorMode.P_NUMBER);
    }

    private void setHistoryOnDisplay(CalculatorMode calculatorMode) {
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            calculatorObserver.setHistoryText(dotsToCommas(LocalHistory.INSTANCE.toString(currentBase)));
        } else {
            calculatorObserver.setHistoryText(dotsToCommas(LocalHistory.INSTANCE.toString()));
        }
    }

    public void pasteFromClipboard(String data, CalculatorMode calculatorMode) {
        try {
            data = parseClipboardString(data, calculatorMode);
        } catch (OverflowException e) {
            setErrorState(ErrorState.OVERFLOW, calculatorMode);
            return;
        } catch (RuntimeException e) {
            setErrorState(ErrorState.WRONG_DATA, calculatorMode);
            return;
        }
        displayTextActionHappened();
        calculatorObserver.setResult(data);
    }

    private String parseClipboardString(String data, CalculatorMode calculatorMode) {
        data = parseStringToNumber(data, calculatorMode, 10).toString();

        data = NumberConverter.toScientificIfNeeded(data, calculatorMode, MAX_SCIENTIFIC_DIGITS_REAL, MAX_SCIENTIFIC_DIGITS_FRACTION);
        return dotsToCommas(data);
    }

    private Number parseStringToNumber(String data, CalculatorMode calculatorMode, int baseOfData) {
        data = commasToDots(data);
        if (data.isEmpty()) {
            throw new IllegalArgumentException("data is empty");
        }
        if (!checkStringBeforeParse(data, calculatorMode)) {
            throw new IllegalArgumentException("data is wrong");
        }
        return NumberConverter.stringToNumber(data, calculatorMode, baseOfData);
    }

    public void setBase(int base) {
        currentBase = base;
    }

    private boolean checkStringBeforeParse(String data, CalculatorMode calculatorMode) {
        switch (calculatorMode) {
            case P_NUMBER:
                char digits = (char) ('0' + (currentBase > 10 ? 9 : currentBase - 1));
                char letters = (char) ('A' + currentBase - 11);
                if (data.replaceFirst("exp", "").contains("exp")) {
                    return false;
                }
                return data.replaceFirst("exp.*", "").chars().allMatch(
                        ch -> (ch >= '0' && ch <= '9' && ch <= digits)
                                || (ch >= 'A' && ch <= 'F' && ch <= letters)
                                || ch == '.'
                                || ch == '-');
            case FRACTION:
                return data.chars().filter(ch -> ch == '/').count() == 1;
            case COMPLEX:
                return true;
        }
        return true;
    }

    private void setResult(CalculatorMode calculatorMode) {
        String result = ControlUnit.INSTANCE.getResultValue().toString();
        if (calculatorMode.equals(CalculatorMode.P_NUMBER)) {
            result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
        }
        result = NumberConverter.toScientificIfNeeded(result, calculatorMode, MAX_SCIENTIFIC_DIGITS_REAL, MAX_SCIENTIFIC_DIGITS_FRACTION);
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

    private void setErrorState(ErrorState state, CalculatorMode calculatorMode) {
        resetModel();
        calculatorObserver.setErrorState(state);
        calculatorObserver.clearResultAfterEnteringDigit();
        toggleCaretIfComplexOrFraction();
        setHistoryOnDisplay(calculatorMode);
        Memory.INSTANCE.memoryClear();
        calculatorObserver.disableMemoryButtons(true);

        currentBase = 10;
        if (pNumberCalculatorObserver != null) {
            pNumberCalculatorObserver.setBase(10);
        }
    }

    private void toggleMemoryButtons(MemoryOperation memoryOperation) {
        switch (memoryOperation) {
            case MEMORY_ADD:
            case MEMORY_SAVE:
                calculatorObserver.disableMemoryButtons(false);
                break;
            case MEMORY_CLEAR:
                calculatorObserver.disableMemoryButtons(true);
                break;
        }
    }

    private void toggleCaretIfComplexOrFraction() {
        if (complexCalculatorObserver != null) {
            complexCalculatorObserver.setCaretToRealPart();
        }
        if (fractionCalculatorObserver != null) {
            fractionCalculatorObserver.setCaretToNumerator();
        }
    }

    private void changeImSignOperation(Number number) {
        Complex complex = (Complex) number;
        complex = complex.negateIm();
        calculatorObserver.setResult(dotsToCommas(complex.toString()));
    }
}
