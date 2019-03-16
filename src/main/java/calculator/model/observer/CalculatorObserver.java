package calculator.model.observer;

import java.util.List;

public interface CalculatorObserver {

    void updateDigitButtons(List<String> buttonsText);
}
