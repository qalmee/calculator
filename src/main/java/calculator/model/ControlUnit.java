package calculator.model;

public class ControlUnit {
    public static final ControlUnit INSTANCE = new ControlUnit();
    Memory memory;
    Processor processor;

    private ControlUnit() {

    }
}
