package calculator.model.observer;

import calculator.model.stats.CalculatorMode;
import calculator.model.stats.ErrorState;
import calculator.view.localization.Language;

import java.util.List;

public interface CalculatorObserver {

    void updateDigitButtons(List<String> buttonsText);

    void updateCalculatorMode(CalculatorMode calculatorMode);

    void updateLanguage(Language language);

    void setBackSpaceEnabled(boolean value);

    void setResult(String result);

    void setHistoryText(String text);

    void clearResultAfterEnteringDigit();

    void copyValueToClipboard();

    void pasteValueFromClipboard();

    void setErrorState(ErrorState errorState);

    void disableMemoryButtons(boolean value);
}
