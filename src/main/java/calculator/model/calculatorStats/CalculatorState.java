package calculator.model.calculatorStats;

public enum CalculatorState {
    ERROR(0),
    START(1),
    FIRST_OPERAND_INPUT(2),
    OPERATOR_SET(3),
    SECOND_OPERAND_INPUT(4),
    EQUALS_PRESSED(5);

    private int calculatorStateNumber;

    CalculatorState(int number) {
        this.calculatorStateNumber = number;
    }

    public int getCalculatorStateNumber() {
        return calculatorStateNumber;
    }
}
