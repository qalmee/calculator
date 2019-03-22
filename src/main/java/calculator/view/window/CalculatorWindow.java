package calculator.view.window;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import static calculator.view.localization.LanguageProperties.getProperty;

public class CalculatorWindow extends Stage {

    public CalculatorWindow() {
        setupCalculatorWindow();
    }

    private void setupCalculatorWindow() {
        this.setTitle(getProperty("calculator_window.title"));
        this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/window_icon.png")));
        this.setResizable(false);
    }
}
