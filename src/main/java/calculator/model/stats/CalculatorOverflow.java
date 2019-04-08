package calculator.model.stats;

public enum CalculatorOverflow {

    REAL_OVERFLOW(1000),
    COMPLEX_OVERFLOW(15);

    int length;

    CalculatorOverflow(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}

