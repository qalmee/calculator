package calculator.model.calculatorStats;

public enum CalculatorOperation {

    ADD("ADD", false, "+"),
    SUBTRACT("SUBTRACT", false, "-"),
    MULTIPLY("MULTIPLY", false, "*"),
    DIVIDE("DIVIDE", false, "/"),
    REVERSE("REVERSE", true, "1/"),
    NEGATE("NEGATE", true, "-"),
    SQUARE("SQUARE", true, "sqr"),
    BACKSPACE("BACKSPACE", true, "OMEGA_LUL"),
    //only for complex:
    ABS("ABS", true, "abs"),
    RAD("RAD", true, "rad"),
    DEG("DEG", true, "deg"),
    POW("POW", false, "^");


    //todo: continue list


    private String calculatorOperation;
    private boolean unary;
    private String mathSign;

    CalculatorOperation(String operation, boolean unaryFlag, String mS) {
        calculatorOperation = operation;
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
