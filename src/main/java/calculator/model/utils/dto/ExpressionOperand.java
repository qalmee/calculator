package calculator.model.utils.dto;

import calculator.model.numbers.Number;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.model.utils.ConverterPToP;
import calculator.model.utils.NumberConverter;

import java.util.LinkedList;
import java.util.List;

public class ExpressionOperand implements ExpressionNode {
    private Number number;
    private LinkedList<ExpressionOperation> unaryOperations;
    private static final int MAX_DIGITS_IN_HISTORY = 30;

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

    public List<ExpressionOperation> getUnaryOperations() {
        return unaryOperations;
    }

    public void addUnaryOperation(CalculatorOperation operation) {
        if (!operation.isUnary()) {
            throw new IllegalArgumentException("Operation must be unary");
        }
        unaryOperations.addFirst(new ExpressionOperation(operation));
    }

    @Override
    public String toString() {
        return toString(10);
    }

    public String toString(int base) {
        StringBuilder sb = new StringBuilder();
        for (ExpressionOperation operation : unaryOperations) {
            sb.append(operation.getOperation().getMathSign());
            sb.append("(");
        }
        sb.append(NumberConverter.toScientific(ConverterPToP.convert10ToPAdaptive(number.toString(), base), MAX_DIGITS_IN_HISTORY, CalculatorMode.P_NUMBER));
        for (int i = 0; i < unaryOperations.size(); i++) {
            sb.append(")");
        }
        return sb.toString();
    }
}
