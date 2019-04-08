package calculator.model.numbers;

import calculator.model.utils.NumberConstant;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Real implements Number<Real> {

    private BigDecimal value;

    public Real(BigDecimal number) {
        if (number == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
        number = number.round(new MathContext(100, RoundingMode.HALF_UP));
        value = number;
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
        return new Real(this.value.divide(b.value, MAX_PRECISION, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public Real reverse() {
        return new Real(BigDecimal.ONE.divide(this.value, MAX_PRECISION, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public Real negate() {
        return new Real(this.value.multiply(BigDecimal.ONE.negate()));
    }

    @Override
    public Real square() {
        return this.multiply(this);
    }

    @Override
    public String toString() {
        return value.stripTrailingZeros().toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Real real = (Real) o;
        return value.subtract(real.value).abs().compareTo(EPS) < 0;
    }

    @Override
    public boolean compareToConst(NumberConstant constant) {
        return this.equals(constant.getReal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return null;
    }

    String getExp() {
        return value.toEngineeringString();
    }

}
