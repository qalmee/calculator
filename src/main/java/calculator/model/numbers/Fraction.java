package calculator.model.numbers;

import java.math.BigInteger;
import java.util.Objects;

public class Fraction implements Number<Fraction> {
    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        if (numerator == null || denominator == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        if (denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Denominator can not be zero");
        }
        if (this.denominator.compareTo(BigInteger.ZERO) < 0) {
            this.numerator = this.numerator.negate();
            this.denominator = this.denominator.negate();
        }
        BigInteger gcd = this.denominator.gcd(this.numerator);
        this.numerator = this.numerator.divide(gcd);
        this.denominator = this.denominator.divide(gcd);
    }

    @Override
    public Fraction add(Fraction b) {
        BigInteger lcm = this.denominator.divide(this.denominator.gcd(b.denominator)).multiply(b.denominator);
        return new Fraction(this.numerator
                .multiply(lcm.divide(this.denominator))
                .add(b.numerator.multiply(lcm.divide(b.denominator))), lcm);
    }

    @Override
    public Fraction subtract(Fraction b) {
        BigInteger lcm = this.denominator.divide(this.denominator.gcd(b.denominator)).multiply(b.denominator);
        return new Fraction(this.numerator
                .multiply(lcm.divide(this.denominator))
                .subtract(b.numerator.multiply(lcm.divide(b.denominator))), lcm);
    }

    @Override
    public Fraction multiply(Fraction b) {
        return new Fraction(this.numerator.multiply(b.numerator), this.denominator.multiply(b.denominator));
    }

    @Override
    public Fraction divide(Fraction b) {
        return new Fraction(this.numerator.multiply(b.denominator), this.denominator.multiply(b.numerator));
    }

    @Override
    public Fraction reverse() {
        return new Fraction(this.denominator, this.numerator);
    }

    @Override
    public Fraction negate() {
        return new Fraction(this.numerator.negate(), this.denominator);
    }

    public Fraction simplify() {
        BigInteger gcd = this.numerator.gcd(this.denominator);
        return new Fraction(this.numerator.divide(gcd), this.denominator.divide(gcd));
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "numerator=" + numerator +
                ", denominator=" + denominator +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return numerator.equals(fraction.numerator) &&
                denominator.equals(fraction.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
