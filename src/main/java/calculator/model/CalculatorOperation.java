package calculator.model;

public enum CalculatorOperation {

    ADD("ADD", false),
    SUBSTRACT("SUBSTRACT", false),
    MULTIPLY("MULTIPLY", false),
    DIVIDE("DIVIDE", false),
    REVERSE("REVERSE", true),
    NEGATE("NEGATE", true),
    SQUARE("SQUARE", true),
    //only for complex:
    ABS("ABS", true),
    RAD("RAD", true),
    DEG("DEG", true),
    POW("POW", false);


    //todo: continue list


    private String calculatorOperation;
    private boolean unary;

    CalculatorOperation(String operation, boolean unaryFlag) {
        calculatorOperation = operation;
        unary = unaryFlag;
    }

    public boolean isUnary() {
        return unary;
    }
}
