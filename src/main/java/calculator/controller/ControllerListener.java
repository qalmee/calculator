package calculator.controller;

import calculator.model.calculatorStats.CalculatorMode;
import calculator.model.calculatorStats.CalculatorOperation;
import calculator.model.memory.MemoryOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.view.localization.Language;

public interface ControllerListener {

    void setNewObserver(CalculatorObserver calculatorObserver);

    void setNewBase(int newBase);

    void updateDigitButtons(int base);

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
