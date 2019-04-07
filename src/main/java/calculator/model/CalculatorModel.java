package calculator.model;

import calculator.controller.ControllerListener;
import calculator.model.configuration.Config;
import calculator.model.memory.MemoryOperation;
import calculator.model.numbers.Number;
import calculator.model.observer.CalculatorObserver;
import calculator.model.observer.ComplexCalculatorObserver;
import calculator.model.observer.FractionCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.model.utils.ConverterPToP;
import calculator.model.utils.NumberConverter;
import calculator.model.utils.exceptions.DivisionByZeroException;
import calculator.model.stats.ErrorState;
import calculator.view.localization.Language;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel implements ControllerListener {

    private static final int MAX_BASE = 16;
    private int currentBase = 10;

    private CalculatorObserver calculatorObserver;
    private FractionCalculatorObserver fractionCalculatorObserver;
    private ComplexCalculatorObserver complexCalculatorObserver;

    public void readConfigInformation() {
        calculatorObserver.updateCalculatorMode(Config.getCalculatorMode());
    }

    public void readLanguageFromConfig() {
        calculatorObserver.updateLanguage(Config.getLanguage());
    }

    @Override
    public void setNewObserver(CalculatorObserver calculatorObserver) {
        this.calculatorObserver = calculatorObserver;
    }

    @Override
    public void setFractionCalculatorObserver(FractionCalculatorObserver fractionCalculatorObserver) {
        this.fractionCalculatorObserver = fractionCalculatorObserver;
    }

    @Override
    public void setComplexCalculatorObserver(ComplexCalculatorObserver complexCalculatorObserver) {
        this.complexCalculatorObserver = complexCalculatorObserver;
    }

    @Override
    public void setNewBase(int newBase) {
        currentBase = newBase;
    }

    @Override
    public void updateDigitButtons(int base) {
        List<String> buttonsToUpdate = new ArrayList<>(MAX_BASE - base);
        for (int i = base; i < MAX_BASE; ++i) {
            buttonsToUpdate.add(Integer.toString(i, 16).toUpperCase());
        }
        calculatorObserver.updateDigitButtons(buttonsToUpdate);
    }

    @Override
    public void checkPastedValue(String value, CalculatorMode calculatorMode) {
        Number number;
        try {
            number = NumberConverter.stringToNumber(value, calculatorMode);
        } catch (RuntimeException e) {
            calculatorObserver.setErrorState(ErrorState.WRONG_DATA);
            calculatorObserver.clearResultAfterEnteringDigit();
            ControlUnit.INSTANCE.resetCalculator();
            return;
        }
        buttonDigitClicked();
        calculatorObserver.setResult(number.toString());
    }

    @Override
    public void convertValue(String value, int currentBase, int newBase) {
        value = commasToDots(value);
        String result = ConverterPToP.convertPTo10Adaptive(value, currentBase);
        result = ConverterPToP.convert10ToPAdaptive(result, newBase);
        calculatorObserver.setResult(dotsToCommas(result));
        calculatorObserver.setPreviousOperationText(dotsToCommas(LocalHistory.INSTANCE.toString(newBase)));
    }

    @Override
    public void updateLanguage(Language language) {
        Config.setLanguage(language);
    }

    @Override
    public void updateCalculatorMode(CalculatorMode calculatorMode) {
        ControlUnit.INSTANCE.resetCalculator();
        resetModel();
        Config.setCalculatorMode(calculatorMode);
        calculatorObserver.updateCalculatorMode(calculatorMode);
        calculatorObserver.setBackSpaceEnabled(true);
    }

    @Override
    public void actionButtonClicked(String value, CalculatorOperation operation, CalculatorMode mode) {
        value = commasToDots(value);
        if (mode.equals(CalculatorMode.P_NUMBER)) {
            value = ConverterPToP.convertPTo10Adaptive(value, currentBase);
        }
        Number number = NumberConverter.stringToNumber(value, mode);

        try {
            ControlUnit.INSTANCE.operatorPressed(number, operation);
        } catch (DivisionByZeroException e) {
            calculatorObserver.setErrorState(ErrorState.DIVISION_BY_ZERO);
            calculatorObserver.clearResultAfterEnteringDigit();
            setHistoryOnDisplay(mode);
            ControlUnit.INSTANCE.resetCalculator();
            return;
        }

        calculatorObserver.clearResultAfterEnteringDigit();
        if (ControlUnit.INSTANCE.needToSetResult()) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (mode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.resultIsSet();
        }
        setHistoryOnDisplay(mode);
        calculatorObserver.setBackSpaceEnabled(false);
    }

    @Override
    public void memoryButtonClicked(String value, MemoryOperation memoryOperation, CalculatorMode mode) {
        value = commasToDots(value);
        if (mode.equals(CalculatorMode.P_NUMBER)) {
            value = ConverterPToP.convertPTo10Adaptive(value, currentBase);
        }
        Number number = NumberConverter.stringToNumber(value, mode);
        ControlUnit.INSTANCE.memoryOperationPressed(number, memoryOperation);
        if (memoryOperation.equals(MemoryOperation.MEMORY_READ)) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (mode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.enteringNewValue();
        }
        calculatorObserver.clearResultAfterEnteringDigit();
    }

    @Override
    public void buttonEnterClicked(String value, CalculatorMode mode) {
        value = commasToDots(value);
        if (mode.equals(CalculatorMode.P_NUMBER)) {
            value = ConverterPToP.convertPTo10Adaptive(value, currentBase);
        }
        Number number = NumberConverter.stringToNumber(value, mode);
        try {
            ControlUnit.INSTANCE.equalsPressed(number);
        } catch (DivisionByZeroException e) {
            calculatorObserver.setErrorState(ErrorState.DIVISION_BY_ZERO);
            calculatorObserver.clearResultAfterEnteringDigit();
            setHistoryOnDisplay(mode);
            ControlUnit.INSTANCE.resetCalculator();
            return;
        }

        calculatorObserver.clearResultAfterEnteringDigit();
        if (ControlUnit.INSTANCE.needToSetResult()) {
            String result = ControlUnit.INSTANCE.getResultValue().toString();
            if (mode.equals(CalculatorMode.P_NUMBER)) {
                result = ConverterPToP.convert10ToPAdaptive(result, currentBase);
            }
            calculatorObserver.setResult(dotsToCommas(result));
            ControlUnit.INSTANCE.resultIsSet();
        }
        calculatorObserver.setPreviousOperationText("");
        calculatorObserver.setBackSpaceEnabled(true);
    }

    @Override
    public void buttonDigitClicked() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    @Override
    public void buttonClearEntryClicked() {
        ControlUnit.INSTANCE.enteringNewValue();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    @Override
    public void buttonGlobalClearClicked() {
        ControlUnit.INSTANCE.resetCalculator();
        calculatorObserver.setBackSpaceEnabled(true);
    }

    @Override
    public void buttonCopyClicked() {
        calculatorObserver.copyValueToClipboard();
    }

    @Override
    public void buttonPasteClicked() {
        calculatorObserver.pasteValueFromClipboard();
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

    private void resetModel() {
        currentBase = 10;
    }
}
