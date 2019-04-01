package calculator.model;

import calculator.model.memory.Memory;
import calculator.model.memory.MemoryOperation;
import calculator.model.numbers.Number;
import calculator.model.stats.CalculatorOperation;
import calculator.model.stats.CalculatorState;

public class ControlUnit {
    public static final ControlUnit INSTANCE = new ControlUnit();
    private CalculatorState state;
    private Number resultValue;
    private boolean needToSetResult;
    private boolean newValue;

    private ControlUnit() {
        resetCalculator();
    }

    public void resetCalculator() {
        Processor.INSTANCE.reset();
        state = CalculatorState.FIRST_OPERAND_INPUT;
        needToSetResult = false;
        LocalHistory.INSTANCE.reset();
        newValue = true;
    }

    private void debug() {
        System.out.println(Processor.INSTANCE.getLeftResultOperand());
        System.out.println(Processor.INSTANCE.getOperation());
        System.out.println(Processor.INSTANCE.getRightOperand());
        System.out.println(state);
        System.out.println();
    }

    @SuppressWarnings("Duplicates")
    public void equalsPressed(Number valueOnDisplay) {
        debug();
        switch (state) {
            case ERROR:
                break;
            case FIRST_OPERAND_INPUT:
                if (Processor.INSTANCE.getRightOperand() != null && Processor.INSTANCE.getOperation() != null) {
                    Processor.INSTANCE.setLeftResultOperand(valueOnDisplay);
                    Processor.INSTANCE.operationRun();
                    needToSetResult = true;
                    state = CalculatorState.EQUALS_PRESSED;
                }
                break;
            case OPERATOR_SET:
                Processor.INSTANCE.setRightOperand(valueOnDisplay);
                Processor.INSTANCE.operationRun();
                needToSetResult = true;
                state = CalculatorState.EQUALS_PRESSED;
                break;
            case SECOND_OPERAND_INPUT:
                Processor.INSTANCE.setRightOperand(valueOnDisplay);
                Processor.INSTANCE.operationRun();
                needToSetResult = true;
                state = CalculatorState.EQUALS_PRESSED;
                break;
            case EQUALS_PRESSED:
                Processor.INSTANCE.operationRun();
                needToSetResult = true;
                break;
            default:
                break;
        }
        LocalHistory.INSTANCE.reset();
        newValue = true;
        resultValue = Processor.INSTANCE.getLeftResultOperand();
    }

    @SuppressWarnings("Duplicates")
    public void operatorPressed(Number valueOnDisplay, CalculatorOperation operation) {
        debug();
        CalculatorOperation operationInProcessor;
        switch (state) {
            case ERROR:
                break;
            case FIRST_OPERAND_INPUT:
                Processor.INSTANCE.setLeftResultOperand(valueOnDisplay);
                Processor.INSTANCE.setOperation(operation);
                if (operation.isUnary()) {
                    addNumberAndUnaryOperation(valueOnDisplay, operation);
                    operationInProcessor = Processor.INSTANCE.getOperation();

                    Processor.INSTANCE.operationRun();
                    state = CalculatorState.FIRST_OPERAND_INPUT;
                    needToSetResult = true;
                    Processor.INSTANCE.setOperation(operationInProcessor);
                } else {
                    addNumberAndBinaryOperation(valueOnDisplay, operation);
                    state = CalculatorState.OPERATOR_SET;
                    newValue = true;
                }
                break;
            case OPERATOR_SET:
                if (operation.isUnary()) {
                    addNumberAndUnaryOperation(valueOnDisplay, operation);

                    Number leftOperand = Processor.INSTANCE.getLeftResultOperand();
                    operationInProcessor = Processor.INSTANCE.getOperation();
                    Processor.INSTANCE.setOperation(operation);

                    Processor.INSTANCE.operationRun();
                    resultValue = Processor.INSTANCE.getLeftResultOperand();

                    Processor.INSTANCE.setRightOperand(Processor.INSTANCE.getLeftResultOperand());
                    Processor.INSTANCE.setLeftResultOperand(leftOperand);
                    Processor.INSTANCE.setOperation(operationInProcessor);
                    needToSetResult = true;
                    state = CalculatorState.SECOND_OPERAND_INPUT;
                    return;
                } else {
                    changeBinaryOperation(operation);
                    Processor.INSTANCE.setOperation(operation);
                    newValue = true;
                }
                break;
            case SECOND_OPERAND_INPUT:
                if (operation.isUnary()) {
                    addNumberAndUnaryOperation(valueOnDisplay, operation);

                    Number leftOPerand = Processor.INSTANCE.getLeftResultOperand();
                    operationInProcessor = Processor.INSTANCE.getOperation();
                    Processor.INSTANCE.setOperation(operation);
                    Processor.INSTANCE.setLeftResultOperand(valueOnDisplay);

                    Processor.INSTANCE.operationRun();
                    resultValue = Processor.INSTANCE.getLeftResultOperand();

                    Processor.INSTANCE.setRightOperand(Processor.INSTANCE.getLeftResultOperand());
                    Processor.INSTANCE.setLeftResultOperand(leftOPerand);
                    Processor.INSTANCE.setOperation(operationInProcessor);
                    needToSetResult = true;
                    state = CalculatorState.SECOND_OPERAND_INPUT;
                    return;
                } else {
                    addNumberAndBinaryOperation(valueOnDisplay, operation);

                    Processor.INSTANCE.setRightOperand(valueOnDisplay);
                    Processor.INSTANCE.operationRun();
                    needToSetResult = true;
                    Processor.INSTANCE.setOperation(operation);
                    state = CalculatorState.OPERATOR_SET;
                    newValue = true;
                }
                break;
            case EQUALS_PRESSED:
                Processor.INSTANCE.setOperation(operation);
                if (operation.isUnary()) {
                    addNumberAndUnaryOperation(valueOnDisplay, operation);
                    operationInProcessor = Processor.INSTANCE.getOperation();

                    Processor.INSTANCE.operationRun();
                    needToSetResult = true;
                    Processor.INSTANCE.setOperation(operationInProcessor);
                } else {
                    addNumberAndBinaryOperation(valueOnDisplay, operation);
                    state = CalculatorState.OPERATOR_SET;
                }
                break;
            default:
                break;
        }
        resultValue = Processor.INSTANCE.getLeftResultOperand();
    }

    public void memoryOperationPressed(Number valueOnDisplay, MemoryOperation operation) {
        debug();
        switch (operation) {
            case MEMORY_ADD:
                Memory.INSTANCE.memoryAdd(valueOnDisplay);
                break;
            case MEMORY_READ:
                resultValue = Memory.INSTANCE.memoryRead();
                break;
            case MEMORY_SAVE:
                Memory.INSTANCE.memorySave(valueOnDisplay);
                break;
            case MEMORY_CLEAR:
                Memory.INSTANCE.memoryClear();
                break;
            default:
                break;
        }
    }

    public Number getResultValue() {
        return resultValue;
    }

    public boolean needToSetResult() {
        return needToSetResult;
    }

    public void resultIsSet() {
        this.needToSetResult = false;
    }

    public void enteringNewValue() {
        if (state == CalculatorState.OPERATOR_SET) {
            state = CalculatorState.SECOND_OPERAND_INPUT;
        } else if (state == CalculatorState.EQUALS_PRESSED) {
            state = CalculatorState.FIRST_OPERAND_INPUT;
        }
    }

    private void addUnaryOperation(CalculatorOperation operation) {
        LocalHistory.INSTANCE.addUnaryOperation(operation);
    }

    private void addNumberAndBinaryOperation(Number number, CalculatorOperation operation) {
        if (LocalHistory.INSTANCE.historyIsEmpty() || !LocalHistory.INSTANCE.lastIsOperand()) {
            LocalHistory.INSTANCE.addNumber(number);
        }
        LocalHistory.INSTANCE.addBinaryOperation(operation);

    }

    private void addNumberAndUnaryOperation(Number number, CalculatorOperation operation) {
        if (newValue) {
            LocalHistory.INSTANCE.addNumber(number);
            LocalHistory.INSTANCE.addUnaryOperation(operation);
            newValue = false;
        } else {
            LocalHistory.INSTANCE.addUnaryOperation(operation);
        }
    }

    private void changeBinaryOperation(CalculatorOperation operation) {
        LocalHistory.INSTANCE.changeLastOperation(operation);
    }

}
