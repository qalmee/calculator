package calculator.model.utils.DTO;

import calculator.model.calculatorStats.CalculatorOperation;
import calculator.model.numbers.Number;
import calculator.model.utils.ConverterPToP;

import java.util.LinkedList;

public class ExpressionOperand implements ExpressionNode {
    private Number number;
    private LinkedList<ExpressionOperation> unaryOperations;

    public ExpressionOperand(Number n) {
        number = n;
        unaryOperations = new LinkedList<>();
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public LinkedList<ExpressionOperation> getUnaryOperations() {
        return unaryOperations;
    }

    public void addUnaryOperation(CalculatorOperation operation) {
        if (!operation.isUnary()) {
            throw new IllegalArgumentException("Operation must be unary");
        }
        unaryOperations.addFirst(new ExpressionOperation(operation));
    }

    @Override
    @SuppressWarnings("Duplicates")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ExpressionOperation operation : unaryOperations) {
            sb.append(operation.getOperation().getMathSign());
            sb.append("(");
        }
        sb.append(number.toString());
        for (int i = 0; i < unaryOperations.size(); i++) {
            sb.append(")");
        }
        return sb.toString();
    }

    @SuppressWarnings("Duplicates")
    public String toString(int base) {
        StringBuilder sb = new StringBuilder();
        for (ExpressionOperation operation : unaryOperations) {
            sb.append(operation.getOperation().getMathSign());
            sb.append("(");
        }
        sb.append(ConverterPToP.convert10ToPAdaptive(number.toString(), base));
        for (int i = 0; i < unaryOperations.size(); i++) {
            sb.append(")");
        }
        return sb.toString();
    }
}
