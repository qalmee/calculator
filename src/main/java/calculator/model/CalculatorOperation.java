package calculator.model;

public enum CalculatorOperation {

    ADD("ADD"),
    SUBSTRACT("SUBSTRACT"),
    MULTIPLY("MULTIPLY"),
    DIVIDE("DIVIDE"),
    REVERSE("REVERSE"),
    NEGATE("NEGATE"),
    SQUARE("SQUARE");

    //todo: continue list


    private String calculatorOperation;

    CalculatorOperation(String operation) {
        calculatorOperation = operation;
    }
}
