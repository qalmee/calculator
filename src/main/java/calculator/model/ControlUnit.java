package calculator.model;

import calculator.model.numbers.Number;

public class ControlUnit {
    public static final ControlUnit INSTANCE = new ControlUnit();
    private CalculatorState state;
    private Number resultValue;
    private boolean needToSetResult;

    private ControlUnit() {
        resetCalculator();
    }

    public void resetCalculator() {
        Processor.INSTANCE.reset();
        state = CalculatorState.START;
        needToSetResult = false;
    }

    public void equalsPressed(Number valueOnDisplay) {
        switch (state) {
            case START:
                break;
            case FIRST_OPERAND_INPUT:
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
        }
        resultValue = Processor.INSTANCE.getLeftResultOperand();
    }

    public void operatorPressed(Number valueOnDisplay, CalculatorOperation operation) {
        //todo: operation is unary = do the math;
        switch (state) {
            case START:
                Processor.INSTANCE.setLeftResultOperand(valueOnDisplay);
                Processor.INSTANCE.setOperation(operation);
                state = CalculatorState.OPERATOR_SET;
                break;
            case FIRST_OPERAND_INPUT:
                Processor.INSTANCE.setLeftResultOperand(valueOnDisplay);
                Processor.INSTANCE.setOperation(operation);
                state = CalculatorState.OPERATOR_SET;
                break;
            case OPERATOR_SET:
                Processor.INSTANCE.setOperation(operation);
                break;
            case SECOND_OPERAND_INPUT:
                Processor.INSTANCE.setRightOperand(valueOnDisplay);
                Processor.INSTANCE.operationRun();
                needToSetResult = true;
                Processor.INSTANCE.setOperation(operation);
                state = CalculatorState.OPERATOR_SET;
                break;
            case EQUALS_PRESSED:
                Processor.INSTANCE.setOperation(operation);
                break;
        }
        resultValue = Processor.INSTANCE.getLeftResultOperand();
    }

    public void memoryOperationPressed(Number valueOnDisplay, MemoryOperation operation) {
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

    public void enteringNewValue(){
        if (state == CalculatorState.OPERATOR_SET || state == CalculatorState.EQUALS_PRESSED){
            state = CalculatorState.SECOND_OPERAND_INPUT;
        }
        else if (state == CalculatorState.START){
            state = CalculatorState.FIRST_OPERAND_INPUT;
        }
    }
}
