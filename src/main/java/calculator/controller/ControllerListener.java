package calculator.controller;

import calculator.model.memory.MemoryOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.model.observer.ComplexCalculatorObserver;
import calculator.model.observer.FractionCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.view.localization.Language;

public interface ControllerListener {

    void setNewObserver(CalculatorObserver calculatorObserver);

    void setFractionCalculatorObserver(FractionCalculatorObserver fractionCalculatorObserver);

    void setComplexCalculatorObserver(ComplexCalculatorObserver complexCalculatorObserver);

    void setNewBase(int newBase);

    void updateDigitButtons(int base);

    void checkPastedValue(String value, CalculatorMode calculatorMode);

    void convertValue(String value, int currentBase, int newBase);

    void updateLanguage(Language language);

    void updateCalculatorMode(CalculatorMode calculatorMode);

    void actionButtonClicked(String number, CalculatorOperation operation, CalculatorMode mode);

    void memoryButtonClicked(String number, MemoryOperation memoryOperation, CalculatorMode mode);

    void buttonEnterClicked(String number, CalculatorMode mode);

    void buttonDigitClicked();

    void buttonClearEntryClicked();

    void buttonGlobalClearClicked();

    void buttonCopyClicked();

    void buttonPasteClicked();
}
