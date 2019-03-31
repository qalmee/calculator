package calculator.model.observer;

import calculator.model.calculatorStats.CalculatorMode;
import calculator.view.ErrorState;
import calculator.view.localization.Language;

import java.util.List;

public interface CalculatorObserver {

    void updateDigitButtons(List<String> buttonsText);

    void updateCalculatorMode(CalculatorMode calculatorMode);

    void updateLanguage(Language language);

    void setBackSpaceEnabled(boolean value);

    void setResult(String result);

    void setPreviousOperationText(String text);

    void clearResultAfterEnteringDigit();

    void copyValueToClipboard();

    void pasteValueFromClipboard();

    void setErrorState(ErrorState errorState);
}
