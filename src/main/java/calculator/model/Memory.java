package calculator.model;

import calculator.model.numbers.Number;

public class Memory {
    private static final Memory INSTANCE = new Memory();
    private Number memoryValue;

    private Memory() {
    }

    public void MemoryClear() {
        memoryValue = null;
    }

    //this function may return null if memory was cleared or never used
    public Number MemoryRead() {
        return memoryValue;
    }

    public Number MemoryAdd(Number addend) {
        if (memoryValue == null) {
            memoryValue = addend;
        } else {
            memoryValue.add(addend);
        }
        return memoryValue;
    }

    public void MemorySave(Number value) {
        memoryValue = value;
    }
}
