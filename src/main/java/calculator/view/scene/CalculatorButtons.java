package calculator.view.scene;

import calculator.model.CalculatorMode;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

public enum CalculatorButtons {

    BUTTON_DIGIT0(4, 3,
            new Button(getProperty("abstract_calculator_scene.button0")), CalculatorMode.BASIC),
    BUTTON_DIGIT1(3, 2,
            new Button(getProperty("abstract_calculator_scene.button1")), CalculatorMode.BASIC),
    BUTTON_DIGIT2(3, 3,
            new Button(getProperty("abstract_calculator_scene.button2")), CalculatorMode.BASIC),
    BUTTON_DIGIT3(3, 4,
            new Button(getProperty("abstract_calculator_scene.button3")), CalculatorMode.BASIC),
    BUTTON_DIGIT4(2, 2,
            new Button(getProperty("abstract_calculator_scene.button4")), CalculatorMode.BASIC),
    BUTTON_DIGIT5(2, 3,
            new Button(getProperty("abstract_calculator_scene.button5")), CalculatorMode.BASIC),
    BUTTON_DIGIT6(2, 4,
            new Button(getProperty("abstract_calculator_scene.button6")), CalculatorMode.BASIC),
    BUTTON_DIGIT7(1, 2,
            new Button(getProperty("abstract_calculator_scene.button7")), CalculatorMode.BASIC),
    BUTTON_DIGIT8(1, 3,
            new Button(getProperty("abstract_calculator_scene.button8")), CalculatorMode.BASIC),
    BUTTON_DIGIT9(1, 4,
            new Button(getProperty("abstract_calculator_scene.button9")), CalculatorMode.BASIC),

    BUTTON_CLEAR_ENTRY(0, 2,
            new Button(getProperty("abstract_calculator_scene.button_clear_entry")), CalculatorMode.BASIC),
    BUTTON_GLOBAL_CLEAR(0, 3,
            new Button(getProperty("abstract_calculator_scene.button_global_clear")), CalculatorMode.BASIC),
    BUTTON_BACKSPACE(0, 4,
            new Button(getProperty("abstract_calculator_scene.button_backspace")), CalculatorMode.BASIC),

    BUTTON_DIVIDE(0, 5,
            new Button(getProperty("abstract_calculator_scene.button_divide")), CalculatorMode.BASIC),
    BUTTON_MULTIPLY(1, 5,
            new Button(getProperty("abstract_calculator_scene.button_multiply")), CalculatorMode.BASIC),
    BUTTON_MINUS(2, 5,
            new Button(getProperty("abstract_calculator_scene.button_minus")), CalculatorMode.BASIC),
    BUTTON_PLUS(3, 5,
            new Button(getProperty("abstract_calculator_scene.button_plus")), CalculatorMode.BASIC),

    BUTTON_SQUARE(3, 1,
            new Button(getProperty("abstract_calculator_scene.button_square")), CalculatorMode.BASIC),
    BUTTON_REVERSE(4, 1,
            new Button(getProperty("abstract_calculator_scene.button_reverse")), CalculatorMode.BASIC),

    BUTTON_DOT(4, 4,
            new Button(getProperty("abstract_calculator_scene.button_dot")), CalculatorMode.BASIC),
    BUTTON_ENTER(4, 5,
            new Button(getProperty("abstract_calculator_scene.button_enter")), CalculatorMode.BASIC),
    BUTTON_PLUS_MINUS(4, 2,
            new Button(getProperty("abstract_calculator_scene.button_plus_minus")), CalculatorMode.BASIC),

    BUTTON_MEMORY_CLEAR(1, 0,
            new Button(getProperty("abstract_calculator_scene.button_memory_clear")), CalculatorMode.BASIC),
    BUTTON_MEMORY_SAVE(2, 0,
            new Button(getProperty("abstract_calculator_scene.button_memory_save")), CalculatorMode.BASIC),
    BUTTON_MEMORY_COPY(3, 0,
            new Button(getProperty("abstract_calculator_scene.button_memory_copy")), CalculatorMode.BASIC),
    BUTTON_MEMORY_ADD(4, 0,
            new Button(getProperty("abstract_calculator_scene.button_memory_add")), CalculatorMode.BASIC),

    BUTTON_DIGIT_A(5, 0,
            new Button(getProperty("p-number_calculator_scene.buttonA")), CalculatorMode.P_NUMBER),
    BUTTON_DIGIT_B(5, 1,
            new Button(getProperty("p-number_calculator_scene.buttonB")), CalculatorMode.P_NUMBER),
    BUTTON_DIGIT_C(5, 2,
            new Button(getProperty("p-number_calculator_scene.buttonC")), CalculatorMode.P_NUMBER),
    BUTTON_DIGIT_D(5, 3,
            new Button(getProperty("p-number_calculator_scene.buttonD")), CalculatorMode.P_NUMBER),
    BUTTON_DIGIT_E(5, 4,
            new Button(getProperty("p-number_calculator_scene.buttonE")), CalculatorMode.P_NUMBER),
    BUTTON_DIGIT_F(5, 5,
            new Button(getProperty("p-number_calculator_scene.buttonF")), CalculatorMode.P_NUMBER),

    BUTTON_DELIMITER(2, 1,
            new Button(getProperty("fraction_calculator_scene.button_delimiter")), CalculatorMode.FRACTION),

    BUTTON_MODULE(5, 0,
            new Button(getProperty("complex_calculator_scene.button_module")), CalculatorMode.COMPLEX),
    BUTTON_ARGUMENT_RAD(5, 1,
            new Button(getProperty("complex_calculator_scene.button_argument_rad")), CalculatorMode.COMPLEX),
    BUTTON_ARGUMENT_DEG(5, 2,
            new Button(getProperty("complex_calculator_scene.button_argument_deg")), CalculatorMode.COMPLEX),
    BUTTON_POW(5, 3,
            new Button(getProperty("complex_calculator_scene.button_pow")), CalculatorMode.COMPLEX),
    BUTTON_ROOT(5, 4,
            new Button(getProperty("complex_calculator_scene.button_root")), CalculatorMode.COMPLEX),
    BUTTON_I(5, 5,
            new Button(getProperty("complex_calculator_scene.buttonI")), CalculatorMode.COMPLEX);


    private int rowInGridPane;
    private int columnInGridPane;
    private Button button;
    private CalculatorMode calculatorMode;

    @SuppressWarnings("unusePrivateMethod")
    CalculatorButtons(int rowInGridPane, int columnInGridPane, Button button, CalculatorMode calculatorMode) {
        this.rowInGridPane = rowInGridPane;
        this.columnInGridPane = columnInGridPane;
        this.button = button;
        this.calculatorMode = calculatorMode;
    }

    public static List<CalculatorButtons> getDigitButtons() {
        return Arrays.asList(BUTTON_DIGIT0, BUTTON_DIGIT1, BUTTON_DIGIT2, BUTTON_DIGIT3, BUTTON_DIGIT4,
                BUTTON_DIGIT5, BUTTON_DIGIT6, BUTTON_DIGIT7, BUTTON_DIGIT8, BUTTON_DIGIT9);
    }

    public static List<CalculatorButtons> getPNumberDigitButtons() {
        return Arrays.asList(BUTTON_DIGIT_A, BUTTON_DIGIT_B, BUTTON_DIGIT_C,
                BUTTON_DIGIT_D, BUTTON_DIGIT_E, BUTTON_DIGIT_F);
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

    public CalculatorMode getCalculatorMode() {
        return calculatorMode;
    }
}
