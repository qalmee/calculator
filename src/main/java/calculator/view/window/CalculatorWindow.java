package calculator.view.window;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import static calculator.view.localization.LanguageProperties.getProperty;

public class CalculatorWindow extends Stage {

    private Window window;

    public CalculatorWindow() {
        window = this;
        setupCalculatorWindow();
    }

    private void setupCalculatorWindow() {
        this.setTitle(getProperty("calculator_window.title"));
        this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/window_icon.png")));
        this.setResizable(false);
        this.sizeToScene();

        window.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
        });
    }
}
