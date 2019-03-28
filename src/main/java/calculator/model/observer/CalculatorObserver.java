package calculator.model.observer;

import calculator.model.CalculatorMode;
import calculator.view.localization.Language;

import java.util.List;

public interface CalculatorObserver {

    void updateDigitButtons(List<String> buttonsText);

    void updateCalculatorMode(CalculatorMode calculatorMode);

    void updateLanguage(Language language);
}
