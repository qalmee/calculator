package calculator.view.window;

import javafx.stage.Stage;

import static calculator.view.LanguageProperties.getProperty;

public class CalculatorWindow extends Stage {

    public CalculatorWindow() {
        setupCalculatorWindow();
    }

    private void setupCalculatorWindow() {
        this.setTitle(getProperty("calculator_window.title"));
    }
}
