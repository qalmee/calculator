package calculator.model.numbers;

import calculator.model.stats.CalculatorPrecision;
import calculator.model.utils.MathUtils;
import calculator.model.utils.NumberConstant;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public class Complex implements Number<Complex> {

    private BigDecimal real;
    private BigDecimal imaginary;

    private static final int MAX_PRECISION = CalculatorPrecision.COMPLEX_PRECISION.getPrecision();
    private static final int MAX_COMPARE_PRECISION = CalculatorPrecision.COMPLEX_PRECISION.getComparePrecision();
    private static final BigDecimal EPS = BigDecimal.ONE
            .divide(BigDecimal.TEN.pow(MAX_COMPARE_PRECISION), MAX_COMPARE_PRECISION, RoundingMode.FLOOR);


    public Complex(BigDecimal real, BigDecimal imaginary) {
        if (real == null || imaginary == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
        real = real.round(new MathContext(MAX_PRECISION, HALF_UP));
        imaginary = imaginary.round(new MathContext(MAX_PRECISION, HALF_UP));
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public Complex add(Complex b) {
        Complex a = this;
        BigDecimal re = a.real.add(b.real);
        BigDecimal im = a.imaginary.add(b.imaginary);
        return new Complex(re, im);
    }

    @Override
    public Complex subtract(Complex b) {
        Complex a = this;
        BigDecimal re = a.real.subtract(b.real);
        BigDecimal im = a.imaginary.subtract(b.imaginary);
        return new Complex(re, im);
    }

    @Override
    public Complex multiply(Complex b) {
        Complex a = this;
        BigDecimal re = a.real.multiply(b.real).subtract(a.imaginary.multiply(b.imaginary));
        BigDecimal im = a.real.multiply(b.imaginary).add(a.imaginary.multiply(b.real));
        return new Complex(re, im);
    }

    @Override
    public Complex square() {
        return this.multiply(this);
    }

    public Complex scale(BigDecimal b) {
        return new Complex(this.real.multiply(b), this.imaginary.multiply(b));
    }

    @Override
    public Complex divide(Complex b) {
        BigDecimal scale = b.real.multiply(b.real).add(b.imaginary.multiply(b.imaginary));
        Complex c = new Complex(b.real.divide(scale, MAX_PRECISION, HALF_UP),
                b.imaginary.negate().divide(scale, MAX_PRECISION, HALF_UP));
        Complex a = this;
        return a.multiply(c);
    }

    //this method counts 1/x value
    @Override
    public Complex reverse() {
        BigDecimal square = this.squareScalar();
        return new Complex(this.conjugate().real.divide(square, MAX_PRECISION, HALF_UP),
                this.conjugate().imaginary.divide(square, MAX_PRECISION, HALF_UP));
    }

    @Override
    public Complex negate() {
        return new Complex(this.real.negate(), this.imaginary.negate());
    }

    public Complex negateIm() {
        return new Complex(this.real, this.imaginary.negate());
    }

    private Complex conjugate() {
        return new Complex(this.real, this.imaginary.negate());
    }

    public BigDecimal module() {
        return MathUtils.sqrt(this.real.multiply(this.real).add(this.imaginary.multiply(this.imaginary)));
    }

    private BigDecimal squareScalar() {
        return this.real.multiply(this.real).add(this.imaginary.multiply(this.imaginary));
    }

    public Complex sqrt() {
        BigDecimal module = MathUtils.sqrt(this.module());
        BigDecimal fi = this.complexArgument().multiply(BigDecimal.valueOf(0.5));
        return new Complex(MathUtils.taylorCos(fi).multiply(module), MathUtils.taylorSin(fi).multiply(module));
    }

    public BigDecimal complexArgument() {
        if (real.abs().compareTo(EPS) <= 0) {
            return BigDecimal.valueOf(Math.PI)
                    .divide(BigDecimal.valueOf(2), MAX_PRECISION, HALF_UP)
                    .multiply(BigDecimal.valueOf(imaginary.compareTo(BigDecimal.ZERO)));
        } else if (real.compareTo(BigDecimal.ZERO) < 0) {
            return MathUtils.taylorATan(this.imaginary.divide(this.real, MAX_PRECISION, HALF_UP))
                    .add(BigDecimal.valueOf(Math.PI));

        } else {
            return MathUtils.taylorATan(this.imaginary.divide(this.real, MAX_PRECISION, HALF_UP));
        }
    }

    public Complex pow(int exponent) {
        if (exponent < 0 || exponent > 9999) {
            throw new IllegalArgumentException("Negative Exponent");
        }
        BigDecimal module = this.module().pow(exponent, new MathContext(MAX_PRECISION, HALF_UP));
        BigDecimal fi = this.complexArgument().multiply(BigDecimal.valueOf(exponent));
        return new Complex(MathUtils.taylorCos(fi).multiply(module), MathUtils.taylorSin(fi).multiply(module));
    }

    @Override
    public String toString() {
        real = real.stripTrailingZeros();
        imaginary = imaginary.stripTrailingZeros();
        if (imaginary.signum() < 0) {
            return real.toPlainString() + imaginary.toPlainString() + "i";
        } else {
            return real.toPlainString() + "+" + imaginary.toPlainString() + "i";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;

        return real.subtract(complex.real).abs().compareTo(EPS) < 0 &&
                imaginary.subtract(complex.imaginary).abs().compareTo(EPS) < 0;
    }

    @Override
    public boolean compareToConst(NumberConstant constant) {
        return this.equals(constant.getComplex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return null;
    }

    public BigDecimal getReal() {
        real = real.stripTrailingZeros();
        return real;
    }

    public BigDecimal getImaginary() {
        imaginary = imaginary.stripTrailingZeros();
        return imaginary;
    }
}
