package calculator.model;

import calculator.model.calculatorStats.CalculatorOperation;
import calculator.model.numbers.Number;
import calculator.model.utils.DTO.ExpressionNode;
import calculator.model.utils.DTO.ExpressionOperand;
import calculator.model.utils.DTO.ExpressionOperation;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LocalHistory {

    public static LocalHistory INSTANCE = new LocalHistory();

    private LinkedList<ExpressionNode> historyList;

    private LocalHistory() {
        historyList = new LinkedList<>();
    }

    public void reset() {
        historyList.clear();
    }

//    public String add(Number number, CalculatorOperation operation){
//        if (operation.isUnary()){
//            history += operation.getMathSign() + "(" + number.toString() + ") ";
//            historyList.addLast(new ExpressionOperation(operation));
//            historyList.addLast(new ExpressionOperand(number));
//        }
//        else{
//            history += number.toString() + " " + operation.getMathSign() + " ";
//            historyList.addLast(new ExpressionOperand(number));
//            historyList.addLast(new ExpressionOperation(operation));
//        }
//        return history;
//    }

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
