package calculator.model.stats;

public enum CalculatorState {
    ERROR(0),
    FIRST_OPERAND_INPUT(1),
    OPERATOR_SET(2),
    SECOND_OPERAND_INPUT(3),
    EQUALS_PRESSED(4);

    private int calculatorStateNumber;

    CalculatorState(int number) {
        this.calculatorStateNumber = number;
    }

    public int getCalculatorStateNumber() {
        return calculatorStateNumber;
    }
}
