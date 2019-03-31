package calculator.model.utils.DTO;

import calculator.model.calculatorStats.CalculatorOperation;

public class ExpressionOperation implements ExpressionNode {

    private CalculatorOperation operation;

    public ExpressionOperation(CalculatorOperation op) {
        operation = op;
    }

    public CalculatorOperation getOperation() {
        return operation;
    }

    public void setOperation(CalculatorOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation.getMathSign();
    }
}
