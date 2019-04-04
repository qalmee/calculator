package calculator.model;

import calculator.model.numbers.Complex;
import calculator.model.numbers.Number;
import calculator.model.stats.CalculatorOperation;
import calculator.model.utils.NumberConstant;
import calculator.model.utils.exceptions.DivisionByZeroException;

import java.math.BigDecimal;

public class Processor<T extends Number<T>> {

    public static final Processor INSTANCE = new Processor();
    private CalculatorOperation operation;

    private Number leftResultOperand;
    private Number rightOperand;

    private Processor() {
    }

    public void reset() {
        resetLeftOperand();
        resetRightOperand();
        resetOperation();
    }

    public void resetOperation() {
        operation = null;
    }

    public void resetLeftOperand() {
        leftResultOperand = null;
    }

    public void resetRightOperand() {
        rightOperand = null;
    }

    public void operationRun() {
        if (leftResultOperand == null || operation == null) {
            throw new IllegalStateException("Left operand or operation is not set");
        }
        if (rightOperand == null && (
                        operation == CalculatorOperation.ADD ||
                        operation == CalculatorOperation.SUBTRACT ||
                        operation == CalculatorOperation.MULTIPLY ||
                        operation == CalculatorOperation.DIVIDE ||
                        operation == CalculatorOperation.POW)) {
            throw new IllegalStateException("Right operand is not set");
        }
        switch (operation) {
            case ADD:
                leftResultOperand = leftResultOperand.add(rightOperand);
                break;
            case SUBTRACT:
                leftResultOperand = leftResultOperand.subtract(rightOperand);
                break;
            case MULTIPLY:
                leftResultOperand = leftResultOperand.multiply(rightOperand);
                break;
            case DIVIDE:
                if (rightOperand.equals(NumberConstant.ZERO)) {
                    throw new DivisionByZeroException("Cannot divide by zero");
                }
                leftResultOperand = leftResultOperand.divide(rightOperand);
                break;
            case NEGATE:
                leftResultOperand = leftResultOperand.negate();
                break;
            case REVERSE:
                leftResultOperand = leftResultOperand.reverse();
                break;
            case SQUARE:
                leftResultOperand = leftResultOperand.square();
                break;
            case ABS:
                leftResultOperand = new Complex(((Complex)(leftResultOperand)).module(), BigDecimal.ZERO);
                break;
            case DEG:
                //wtf????
                leftResultOperand = new Complex(((Complex)(leftResultOperand)).complexArgument(), BigDecimal.ZERO);
                break;
            case POW:
                leftResultOperand =(((Complex)(leftResultOperand)).pow(rightOperand.toBigDecimal().intValue()));
                break;
            case RAD:
                leftResultOperand = new Complex(((Complex)(leftResultOperand)).complexArgument(), BigDecimal.ZERO);
                break;
            default:
                //throw
        }
    }

    public Number getLeftResultOperand() {
        return leftResultOperand;
    }

    public void setLeftResultOperand(Number leftResultOperand) {
        this.leftResultOperand = leftResultOperand;
    }

    public Number getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(Number rightOperand) {
        this.rightOperand = rightOperand;
    }

    public CalculatorOperation getOperation() {
        return operation;
    }

    public void setOperation(CalculatorOperation operation) {
        this.operation = operation;
    }

    public boolean rightOperandSet() {
        return rightOperand != null;
    }

    public boolean leftOperandSet() {
        return leftResultOperand != null;
    }

    public boolean operationSet() {
        return operation != null;
    }
}
