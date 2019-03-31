package calculator.model.memory;

import calculator.model.numbers.Number;

public class Memory <T extends Number<T>> {

    public static final Memory INSTANCE = new Memory();
    private Number<T> memoryValue;

    private Memory() {
    }

    public void memoryClear() {
        memoryValue = null;
    }

    //this function may return null if memory was cleared or never used
    public Number<T> memoryRead() {
        return memoryValue;
    }

    public Number<T> memoryAdd(T addend) {
        if (memoryValue == null) {
            memoryValue = addend;
        } else {
            memoryValue.add(addend);
        }
        return memoryValue;
    }

    public void memorySave(Number<T> value) {
        memoryValue = value;
    }
}
