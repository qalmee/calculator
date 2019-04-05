package calculator.model.stats;

import static calculator.view.localization.LanguageProperties.getProperty;

public enum CalculatorOperation {

    ADD("ADD", false, getProperty("calculator_scene.operation_plus")),
    SUBTRACT("SUBTRACT", false, getProperty("calculator_scene.operation_minus")),
    MULTIPLY("MULTIPLY", false, getProperty("calculator_scene.operation_multiply")),
    DIVIDE("DIVIDE", false, getProperty("calculator_scene.operation_divide")),
    REVERSE("REVERSE", true, getProperty("calculator_scene.operation_reverse")),
    NEGATE("NEGATE", true, getProperty("calculator_scene.operation_negate")),
    SQUARE("SQUARE", true, getProperty("calculator_scene.operation_square")),
    BACKSPACE("BACKSPACE", true, "OMEGA_LUL"),
    //only for complex:
    ABS("ABS", true, getProperty("complex_calculator_scene.operation_abs")),
    RAD("RAD", true, getProperty("complex_calculator_scene.operation_rad")),
    DEG("DEG", true, getProperty("complex_calculator_scene.operation_deg")),
    POW("POW", false, getProperty("complex_calculator_scene.operation_pow"));


    private String name;
    private boolean unary;
    private String mathSign;

    CalculatorOperation(String operation, boolean unaryFlag, String mS) {
        name = operation;
        unary = unaryFlag;
        mathSign = mS;
    }

    public boolean isUnary() {
        return unary;
    }

    public String getMathSign() {
        return mathSign;
    }
}
