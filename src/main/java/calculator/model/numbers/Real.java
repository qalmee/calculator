package calculator.model.numbers;

import java.math.BigDecimal;
import java.util.Objects;

public class Real implements Number<Real> {

    private BigDecimal value;

    public Real(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
        this.value = value;
    }

    @Override
    public Real add(Real b) {
        return new Real(this.value.add(b.value));
    }

    @Override
    public Real subtract(Real b) {
        return new Real(this.value.subtract(b.value));
    }

    @Override
    public Real multiply(Real b) {
        return new Real(this.value.multiply(b.value));
    }

    @Override
    public Real divide(Real b) {
        return new Real(this.value.divide(b.value, MAX_PRECISION, BigDecimal.ROUND_FLOOR));
    }

    @Override
    public Real reverse() {
        return new Real(this.value.pow(-1));
    }

    @Override
    public Real negate() {
        return new Real(this.value.multiply(BigDecimal.ONE.negate()));
    }

    @Override
    public String toString() {
        return "Real{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Real real = (Real) o;
        return value.subtract(real.value).abs().compareTo(EPS) < 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}