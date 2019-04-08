package calculator.view.scene.components;

import calculator.model.memory.MemoryOperation;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static calculator.view.localization.LanguageProperties.getProperty;

public enum CalculatorButtons {

    BUTTON_DIGIT0(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT0),
            4, 3, new Button(getProperty("calculator_scene.button0"))),
    BUTTON_DIGIT1(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT1),
            3, 2, new Button(getProperty("calculator_scene.button1"))),
    BUTTON_DIGIT2(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT2),
            3, 3, new Button(getProperty("calculator_scene.button2"))),
    BUTTON_DIGIT3(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT3),
            3, 4, new Button(getProperty("calculator_scene.button3"))),
    BUTTON_DIGIT4(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT4),
            2, 2, new Button(getProperty("calculator_scene.button4"))),
    BUTTON_DIGIT5(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT5),
            2, 3, new Button(getProperty("calculator_scene.button5"))),
    BUTTON_DIGIT6(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT6),
            2, 4, new Button(getProperty("calculator_scene.button6"))),
    BUTTON_DIGIT7(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT7),
            1, 2, new Button(getProperty("calculator_scene.button7"))),
    BUTTON_DIGIT8(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT8),
            1, 3, new Button(getProperty("calculator_scene.button8"))),
    BUTTON_DIGIT9(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIGIT9),
            1, 4, new Button(getProperty("calculator_scene.button9"))),

    BUTTON_CLEAR_ENTRY(CalculatorMode.BASIC, null,
            0, 2, new Button(getProperty("calculator_scene.button_clear_entry"))),
    BUTTON_GLOBAL_CLEAR(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.ESCAPE),
            0, 3, new Button(getProperty("calculator_scene.button_global_clear"))),
    BUTTON_BACKSPACE(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.BACK_SPACE),
            0, 4, new Button(getProperty("calculator_scene.button_backspace"))),

    BUTTON_DIVIDE(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.DIVIDE),
            0, 5, new Button(getProperty("calculator_scene.button_divide"))),
    BUTTON_MULTIPLY(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.MULTIPLY),
            1, 5, new Button(getProperty("calculator_scene.button_multiply"))),
    BUTTON_SUBTRACT(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.SUBTRACT),
            2, 5, new Button(getProperty("calculator_scene.button_subtract"))),
    BUTTON_ADD(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.ADD),
            3, 5, new Button(getProperty("calculator_scene.button_add"))),

    BUTTON_SQUARE(CalculatorMode.BASIC, null,
            3, 1, new Button(getProperty("calculator_scene.button_square"))),
    BUTTON_REVERSE(CalculatorMode.BASIC, null,
            4, 1, new Button(getProperty("calculator_scene.button_reverse"))),

    BUTTON_COMMA(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.COMMA),
            4, 4, new Button(getProperty("calculator_scene.button_comma"))),
    BUTTON_ENTER(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.ENTER),
            4, 5, new Button(getProperty("calculator_scene.button_enter"))),
    BUTTON_NEGATE(CalculatorMode.BASIC, null,
            4, 2, new Button(getProperty("calculator_scene.button_negate"))),

    BUTTON_MEMORY_CLEAR(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN),
            1, 0, new Button(getProperty("calculator_scene.button_memory_clear"))),
    BUTTON_MEMORY_SAVE(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN),
            2, 0, new Button(getProperty("calculator_scene.button_memory_save"))),
    BUTTON_MEMORY_READ(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
            3, 0, new Button(getProperty("calculator_scene.button_memory_read"))),
    BUTTON_MEMORY_ADD(CalculatorMode.BASIC, new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN),
            4, 0, new Button(getProperty("calculator_scene.button_memory_add"))),

    BUTTON_DIGIT_A(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.A),
            5, 0, new Button(getProperty("p-number_calculator_scene.buttonA"))),
    BUTTON_DIGIT_B(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.B),
            5, 1, new Button(getProperty("p-number_calculator_scene.buttonB"))),
    BUTTON_DIGIT_C(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.C),
            5, 2, new Button(getProperty("p-number_calculator_scene.buttonC"))),
    BUTTON_DIGIT_D(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.D),
            5, 3, new Button(getProperty("p-number_calculator_scene.buttonD"))),
    BUTTON_DIGIT_E(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.E),
            5, 4, new Button(getProperty("p-number_calculator_scene.buttonE"))),
    BUTTON_DIGIT_F(CalculatorMode.P_NUMBER, new KeyCodeCombination(KeyCode.F),
            5, 5, new Button(getProperty("p-number_calculator_scene.buttonF"))),

    BUTTON_DELIMITER(CalculatorMode.FRACTION, new KeyCodeCombination(KeyCode.BACK_SLASH, KeyCombination.SHIFT_DOWN),
            2, 1, new Button(getProperty("fraction_calculator_scene.button_delimiter"))),

    BUTTON_IM_NEGATE(CalculatorMode.COMPLEX, null,
            2, 1, new Button(getProperty("complex_calculator_scene.button_im_negate"))),
    BUTTON_MODULE(CalculatorMode.COMPLEX, null,
            5, 0, new Button(getProperty("complex_calculator_scene.button_module"))),
    BUTTON_ARGUMENT_RAD(CalculatorMode.COMPLEX, null,
            5, 1, new Button(getProperty("complex_calculator_scene.button_argument_rad"))),
    BUTTON_ARGUMENT_DEG(CalculatorMode.COMPLEX, null,
            5, 2, new Button(getProperty("complex_calculator_scene.button_argument_deg"))),
    BUTTON_POW(CalculatorMode.COMPLEX, null,
            5, 3, new Button(getProperty("complex_calculator_scene.button_pow"))),
    BUTTON_SQR(CalculatorMode.COMPLEX, null,
            5, 4, new Button(getProperty("complex_calculator_scene.button_sqr"))),
    BUTTON_I(CalculatorMode.COMPLEX, new KeyCodeCombination(KeyCode.I),
            5, 5, new Button(getProperty("complex_calculator_scene.buttonI")));

    private static Map<Button, CalculatorOperation> actionButtons;
    private static Map<Button, CalculatorOperation> complexActionButtons;
    private static Map<Button, MemoryOperation> memoryButtons;

    static {
        actionButtons = new HashMap<>();
        actionButtons.put(BUTTON_ADD.getButton(), CalculatorOperation.ADD);
        actionButtons.put(BUTTON_SUBTRACT.getButton(), CalculatorOperation.SUBTRACT);
        actionButtons.put(BUTTON_MULTIPLY.getButton(), CalculatorOperation.MULTIPLY);
        actionButtons.put(BUTTON_DIVIDE.getButton(), CalculatorOperation.DIVIDE);
        actionButtons.put(BUTTON_REVERSE.getButton(), CalculatorOperation.REVERSE);
        actionButtons.put(BUTTON_SQUARE.getButton(), CalculatorOperation.SQUARE);
        actionButtons.put(BUTTON_NEGATE.getButton(), CalculatorOperation.NEGATE);

        complexActionButtons = new HashMap<>();
        complexActionButtons.put(BUTTON_IM_NEGATE.getButton(), CalculatorOperation.IM_NEGATE);
        complexActionButtons.put(BUTTON_MODULE.getButton(), CalculatorOperation.ABS);
        complexActionButtons.put(BUTTON_ARGUMENT_RAD.getButton(), CalculatorOperation.RAD);
        complexActionButtons.put(BUTTON_ARGUMENT_DEG.getButton(), CalculatorOperation.DEG);
        complexActionButtons.put(BUTTON_POW.getButton(), CalculatorOperation.POW);
        complexActionButtons.put(BUTTON_SQR.getButton(), CalculatorOperation.SQRT);

        memoryButtons = new HashMap<>();
        memoryButtons.put(BUTTON_MEMORY_ADD.getButton(), MemoryOperation.MEMORY_ADD);
        memoryButtons.put(BUTTON_MEMORY_SAVE.getButton(), MemoryOperation.MEMORY_SAVE);
        memoryButtons.put(BUTTON_MEMORY_READ.getButton(), MemoryOperation.MEMORY_READ);
        memoryButtons.put(BUTTON_MEMORY_CLEAR.getButton(), MemoryOperation.MEMORY_CLEAR);
    }

    private CalculatorMode calculatorMode;
    private KeyCodeCombination keyCodeCombination;
    private int rowInGridPane;
    private int columnInGridPane;
    private Button button;

    @SuppressWarnings("unusePrivateMethod")
    CalculatorButtons(CalculatorMode calculatorMode, KeyCodeCombination keyCodeCombination,
                      int rowInGridPane, int columnInGridPane, Button button) {
        this.calculatorMode = calculatorMode;
        this.keyCodeCombination = keyCodeCombination;
        this.rowInGridPane = rowInGridPane;
        this.columnInGridPane = columnInGridPane;
        this.button = button;
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
        return Arrays.asList(BUTTON_ADD, BUTTON_SUBTRACT, BUTTON_MULTIPLY,
                BUTTON_DIVIDE, BUTTON_NEGATE, BUTTON_REVERSE, BUTTON_SQUARE);
    }

    public static List<CalculatorButtons> getComplexActionButtons() {
        return Arrays.asList(BUTTON_MODULE, BUTTON_ARGUMENT_RAD, BUTTON_ARGUMENT_DEG,
                BUTTON_POW, BUTTON_SQR, BUTTON_IM_NEGATE);
    }

    public static List<CalculatorButtons> getMemoryButtons() {
        return Arrays.asList(BUTTON_MEMORY_ADD, BUTTON_MEMORY_CLEAR, BUTTON_MEMORY_READ, BUTTON_MEMORY_SAVE);
    }

    public static List<CalculatorButtons> getAllClearButtons() {
        return Arrays.asList(BUTTON_CLEAR_ENTRY, BUTTON_GLOBAL_CLEAR, BUTTON_BACKSPACE);
    }

    public static CalculatorOperation getCalculatorOperationFromButton(Button button) {
        return actionButtons.get(button);
    }

    public static CalculatorOperation getCalculatorOperationFromComplexButton(Button button) {
        return complexActionButtons.get(button);
    }

    public static MemoryOperation getMemoryOperationFromButton(Button button) {
        return memoryButtons.get(button);
    }

    public CalculatorMode getCalculatorMode() {
        return calculatorMode;
    }

    public KeyCodeCombination getKeyCodeCombination() {
        return keyCodeCombination;
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
