package calculator.view.scene;

import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

public enum AbstractCalculatorSceneButtons {

    BUTTON_DIGIT0(4, 3, new Button(getProperty("abstract_calculator_scene.button0"))),
    BUTTON_DIGIT1(3, 2, new Button(getProperty("abstract_calculator_scene.button1"))),
    BUTTON_DIGIT2(3, 3, new Button(getProperty("abstract_calculator_scene.button2"))),
    BUTTON_DIGIT3(3, 4, new Button(getProperty("abstract_calculator_scene.button3"))),
    BUTTON_DIGIT4(2, 2, new Button(getProperty("abstract_calculator_scene.button4"))),
    BUTTON_DIGIT5(2, 3, new Button(getProperty("abstract_calculator_scene.button5"))),
    BUTTON_DIGIT6(2, 4, new Button(getProperty("abstract_calculator_scene.button6"))),
    BUTTON_DIGIT7(1, 2, new Button(getProperty("abstract_calculator_scene.button7"))),
    BUTTON_DIGIT8(1, 3, new Button(getProperty("abstract_calculator_scene.button8"))),
    BUTTON_DIGIT9(1, 4, new Button(getProperty("abstract_calculator_scene.button9"))),

    BUTTON_CLEAR_ENTRY(0, 2, new Button(getProperty("abstract_calculator_scene.button_clear_entry"))),
    BUTTON_GLOBAL_CLEAR(0, 3, new Button(getProperty("abstract_calculator_scene.button_global_clear"))),
    BUTTON_BACKSPACE(0, 4, new Button(getProperty("abstract_calculator_scene.button_backspace"))),

    BUTTON_DIVIDE(0, 5, new Button(getProperty("abstract_calculator_scene.button_divide"))),
    BUTTON_MULTIPLY(1, 5, new Button(getProperty("abstract_calculator_scene.button_multiply"))),
    BUTTON_MINUS(2, 5, new Button(getProperty("abstract_calculator_scene.button_minus"))),
    BUTTON_PLUS(3, 5, new Button(getProperty("abstract_calculator_scene.button_plus"))),

    BUTTON_SQUARE(3, 1, new Button(getProperty("abstract_calculator_scene.button_square"))),
    BUTTON_REVERSE(4, 1, new Button(getProperty("abstract_calculator_scene.button_reverse"))),

    BUTTON_DELIMITER(4, 4, new Button(getProperty("abstract_calculator_scene.button_delimiter"))),
    BUTTON_ENTER(4, 5, new Button(getProperty("abstract_calculator_scene.button_enter"))),
    BUTTON_PLUS_MINUS(4, 2, new Button(getProperty("abstract_calculator_scene.button_plus_minus"))),

    BUTTON_MEMORY_CLEAR(1, 0, new Button(getProperty("abstract_calculator_scene.button_memory_clear"))),
    BUTTON_MEMORY_SAVE(2, 0, new Button(getProperty("abstract_calculator_scene.button_memory_save"))),
    BUTTON_MEMORY_COPY(3, 0, new Button(getProperty("abstract_calculator_scene.button_memory_copy"))),
    BUTTON_MEMORY_ADD(4, 0, new Button(getProperty("abstract_calculator_scene.button_memory_add")));

    private int rowInGridPane;
    private int columnInGridPane;
    private Button button;

    @SuppressWarnings("unusePrivateMethod")
    AbstractCalculatorSceneButtons(int rowInGridPane, int columnInGridPane, Button button) {
        this.rowInGridPane = rowInGridPane;
        this.columnInGridPane = columnInGridPane;
        this.button = button;
    }

    public static List<AbstractCalculatorSceneButtons> getDigitButtons() {
        return Arrays.asList(BUTTON_DIGIT0, BUTTON_DIGIT1, BUTTON_DIGIT2, BUTTON_DIGIT3, BUTTON_DIGIT4,
                BUTTON_DIGIT5, BUTTON_DIGIT6, BUTTON_DIGIT7, BUTTON_DIGIT8, BUTTON_DIGIT9);
    }

    public int getRowInGridPane() {
        return rowInGridPane;
    }

    public int getColumnInGridPane() {
        return columnInGridPane;
    }

    public Button getButton() {
        return button;
    }
}
