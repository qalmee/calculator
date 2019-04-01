package calculator.model;

import calculator.model.numbers.Number;
import calculator.model.stats.CalculatorOperation;
import calculator.model.utils.dto.ExpressionNode;
import calculator.model.utils.dto.ExpressionOperand;
import calculator.model.utils.dto.ExpressionOperation;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LocalHistory {

    public static final LocalHistory INSTANCE = new LocalHistory();

    private LinkedList<ExpressionNode> historyList;

    private LocalHistory() {
        historyList = new LinkedList<>();
    }

    public void reset() {
        historyList.clear();
    }

    public void addNumber(Number number) {
        historyList.addLast(new ExpressionOperand(number));
    }

    public void addOperation(CalculatorOperation operation) {
        if (operation.isUnary()) {
            addUnaryOperation(operation);
        } else {
            addBinaryOperation(operation);
        }
    }

    public void addBinaryOperation(CalculatorOperation operation) {
        if (operation.isUnary()) {
            throw new IllegalArgumentException("Operation must be binary");
        }
        historyList.addLast(new ExpressionOperation(operation));
    }

    public void addUnaryOperation(CalculatorOperation operation) {
        if (!operation.isUnary()) {
            throw new IllegalArgumentException("Operation must be unary");
        }
        ExpressionNode operand = historyList.removeLast();
        if (!(operand instanceof ExpressionOperand)) {
            throw new NoSuchElementException("Last element of History must be operand");
        }
        ((ExpressionOperand) (operand)).addUnaryOperation(operation);
        historyList.addLast(operand);
    }

    public void changeLastOperation(CalculatorOperation operation) {
        if (operation.isUnary()) {
            throw new IllegalArgumentException("Operation must be binary");
        }
        if (!(historyList.getLast() instanceof ExpressionOperation)) {
            throw new NoSuchElementException("Last element of History must be operand");
        }
        historyList.removeLast();
        historyList.addLast(new ExpressionOperation(operation));
    }

    public boolean lastIsOperand() {
        return (historyList.getLast() instanceof ExpressionOperand);
    }

    public boolean historyIsEmpty() {
        return historyList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ExpressionNode node : historyList) {
            sb.append(node.toString());
        }
        return sb.toString();
    }

    public String toString(int base) {
        StringBuilder sb = new StringBuilder();
        for (ExpressionNode node : historyList) {
            if (node instanceof ExpressionOperand) {
                sb.append(((ExpressionOperand) node).toString(base));
            } else {
                sb.append(node.toString());
            }
        }
        return sb.toString();
    }
}
