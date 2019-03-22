package calculator.model;

public enum CalculatorOperation {

    ADD("ADD"),
    SUBSTRACT("SUBSTRACT"),
    MULTIPLY("MULTIPLY"),
    DIVIDE("DIVIDE");
    //todo: continue list


    private String calculatorOperation;

    CalculatorOperation(String operation) {
        calculatorOperation = operation;
    }
}
