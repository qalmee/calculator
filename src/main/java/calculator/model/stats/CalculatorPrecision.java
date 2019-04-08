package calculator.model.stats;

public enum CalculatorPrecision {
    REAL_PRECISION(100, 90),
    COMPLEX_PRECISION(11, 10),
    SCIENTIFIC_DIGITS_REAL(30, 30),
    SCIENTIFIC_DIGITS_FRACTION(13, 13);

    int precision;
    int comparePrecision;

    CalculatorPrecision(int precision, int comparePrecision) {
        this.precision = precision;
        this.comparePrecision = comparePrecision;
    }

    public int getPrecision() {
        return precision;
    }

    public int getComparePrecision() {
        return comparePrecision;
    }
}
