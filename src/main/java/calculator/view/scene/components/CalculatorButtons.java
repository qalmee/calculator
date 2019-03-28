package calculator.view.scene.components;

import calculator.model.CalculatorMode;
import calculator.model.CalculatorOperation;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static calculator.view.localization.LanguageProperties.getProperty;

public enum CalculatorButtons {

    BUTTON_DIGIT0(4, 3, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button0")), KeyCode.DIGIT0),
    BUTTON_DIGIT1(3, 2, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button1")), KeyCode.DIGIT1),
    BUTTON_DIGIT2(3, 3, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button2")), KeyCode.DIGIT2),
    BUTTON_DIGIT3(3, 4, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button3")), KeyCode.DIGIT3),
    BUTTON_DIGIT4(2, 2, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button4")), KeyCode.DIGIT4),
    BUTTON_DIGIT5(2, 3, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button5")), KeyCode.DIGIT5),
    BUTTON_DIGIT6(2, 4, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button6")), KeyCode.DIGIT6),
    BUTTON_DIGIT7(1, 2, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button7")), KeyCode.DIGIT7),
    BUTTON_DIGIT8(1, 3, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button8")), KeyCode.DIGIT8),
    BUTTON_DIGIT9(1, 4, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button9")), KeyCode.DIGIT9),

    BUTTON_CLEAR_ENTRY(0, 2, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_clear_entry")), null),
    BUTTON_GLOBAL_CLEAR(0, 3, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_global_clear")), KeyCode.ESCAPE),
    BUTTON_BACKSPACE(0, 4, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_backspace")), KeyCode.BACK_SPACE),

    BUTTON_DIVIDE(0, 5, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_divide")), KeyCode.DIVIDE),
    BUTTON_MULTIPLY(1, 5, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_multiply")), KeyCode.MULTIPLY),
    BUTTON_MINUS(2, 5, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_minus")), KeyCode.MINUS),
    BUTTON_PLUS(3, 5, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_plus")), KeyCode.PLUS),

    BUTTON_SQUARE(3, 1, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_square")), null),
    BUTTON_REVERSE(4, 1, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_reverse")), null),

    BUTTON_DOT(4, 4, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_dot")), KeyCode.COMMA),
    BUTTON_ENTER(4, 5, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_enter")), KeyCode.ENTER),
    BUTTON_PLUS_MINUS(4, 2, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_plus_minus")), null),

    BUTTON_MEMORY_CLEAR(1, 0, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_memory_clear")), null),
    BUTTON_MEMORY_SAVE(2, 0, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_memory_save")), null),
    BUTTON_MEMORY_COPY(3, 0, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_memory_copy")), null),
    BUTTON_MEMORY_ADD(4, 0, CalculatorMode.BASIC,
            new Button(getProperty("calculator_scene.button_memory_add")), null),

    BUTTON_DIGIT_A(5, 0, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonA")), KeyCode.A),
    BUTTON_DIGIT_B(5, 1, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonB")), KeyCode.B),
    BUTTON_DIGIT_C(5, 2, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonC")), KeyCode.C),
    BUTTON_DIGIT_D(5, 3, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonD")), KeyCode.D),
    BUTTON_DIGIT_E(5, 4, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonE")), KeyCode.E),
    BUTTON_DIGIT_F(5, 5, CalculatorMode.P_NUMBER,
            new Button(getProperty("p-number_calculator_scene.buttonF")), KeyCode.F),

    BUTTON_DELIMITER(2, 1, CalculatorMode.FRACTION,
            new Button(getProperty("fraction_calculator_scene.button_delimiter")), null),

    BUTTON_MODULE(5, 0, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.button_module")), null),
    BUTTON_ARGUMENT_RAD(5, 1, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.button_argument_rad")), null),
    BUTTON_ARGUMENT_DEG(5, 2, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.button_argument_deg")), null),
    BUTTON_POW(5, 3, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.button_pow")), null),
    BUTTON_SQR(5, 4, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.button_sqr")), null),
    BUTTON_I(5, 5, CalculatorMode.COMPLEX,
            new Button(getProperty("complex_calculator_scene.buttonI")), KeyCode.I);


    private static Map<Button, CalculatorOperation> actionButtons;

    static {
        actionButtons = new HashMap<>();
        actionButtons.put(BUTTON_PLUS.getButton(), CalculatorOperation.ADD);
        actionButtons.put(BUTTON_MINUS.getButton(), CalculatorOperation.SUBSTRACT);
        actionButtons.put(BUTTON_MULTIPLY.getButton(), CalculatorOperation.MULTIPLY);
        actionButtons.put(BUTTON_DIVIDE.getButton(), CalculatorOperation.DIVIDE);
        actionButtons.put(BUTTON_REVERSE.getButton(), CalculatorOperation.REVERSE);
        actionButtons.put(BUTTON_SQUARE.getButton(), CalculatorOperation.SQUARE);
        actionButtons.put(BUTTON_PLUS_MINUS.getButton(), CalculatorOperation.NEGATE);
    }

    private int rowInGridPane;
    private int columnInGridPane;
    private Button button;
    private CalculatorMode calculatorMode;
    private KeyCode keyCode;

    @SuppressWarnings("unusePrivateMethod")
    CalculatorButtons(int rowInGridPane, int columnInGridPane, CalculatorMode calculatorMode,
                      Button button, KeyCode keyCode) {
        this.rowInGridPane = rowInGridPane;
        this.columnInGridPane = columnInGridPane;
        this.calculatorMode = calculatorMode;
        this.button = button;
        this.keyCode = keyCode;
    }

    public static List<CalculatorButtons> getDigitButtons() {
        return Arrays.asList(BUTTON_DIGIT0, BUTTON_DIGIT1, BUTTON_DIGIT2, BUTTON_DIGIT3, BUTTON_DIGIT4,
                BUTTON_DIGIT5, BUTTON_DIGIT6, BUTTON_DIGIT7, BUTTON_DIGIT8, BUTTON_DIGIT9);
    }

    public static List<CalculatorButtons> getPNumberDigitButtons() {
        return Arrays.asList(BUTTON_DIGIT_A, BUTTON_DIGIT_B, BUTTON_DIGIT_C,
                BUTTON_DIGIT_D, BUTTON_DIGIT_E, BUTTON_DIGIT_F);
    }

    public static List<CalculatorButtons> getActionButtons() {
        return Arrays.asList(BUTTON_PLUS, BUTTON_MINUS, BUTTON_MULTIPLY,
                BUTTON_DIVIDE, BUTTON_PLUS_MINUS, BUTTON_REVERSE, BUTTON_SQUARE);
    }

    public static CalculatorOperation getCalculatorOperationFromButton(Button button) {
        return actionButtons.get(button);
    }

    public int getRowInGridPane() {
        return rowInGridPane;
    }

    public int getColumnInGridPane() {
        return columnInGridPane;
    }

    public CalculatorMode getCalculatorMode() {
        return calculatorMode;
    }

    public Button getButton() {
        return button;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
}
