package calculator.model.numbers;

import calculator.model.utils.MathUtils;
import calculator.model.utils.NumberConstant;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_FLOOR;

public class Complex implements Number<Complex> {

    private BigDecimal real;
    private BigDecimal imaginary;


    public Complex(BigDecimal real, BigDecimal imaginary) {
        if (real == null || imaginary == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
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
        Complex c = new Complex(b.real.divide(scale, MAX_PRECISION, ROUND_FLOOR),
                b.imaginary.negate().divide(scale, MAX_PRECISION, ROUND_FLOOR));
        Complex a = this;
        return a.multiply(c);
    }

    //this method counts 1/x value
    @Override
    public Complex reverse() {
        BigDecimal square = this.module();
        return new Complex(this.conjugate().real.divide(square, MAX_PRECISION, ROUND_FLOOR),
                this.conjugate().imaginary.divide(square, MAX_PRECISION, ROUND_FLOOR));
    }

    @Override
    public Complex negate() {
        return new Complex(this.real.negate(), this.imaginary.negate());
    }

    public Complex conjugate() {
        return new Complex(this.real, this.imaginary.negate());
    }

    public BigDecimal module() {
        return this.real.multiply(this.real).add(this.imaginary.multiply(this.imaginary));
    }

    public BigDecimal complexArgument() {
        if (real.abs().compareTo(EPS) <= 0) {
            return BigDecimal.valueOf(Math.PI)
                    .divide(BigDecimal.valueOf(2), MAX_PRECISION, ROUND_FLOOR)
                    .multiply(BigDecimal.valueOf(imaginary.compareTo(BigDecimal.ZERO)));
        } else if (real.compareTo(BigDecimal.ZERO) < 0) {
            return MathUtils.taylorATan(this.imaginary.divide(this.real, MAX_PRECISION, ROUND_FLOOR))
                    .add(BigDecimal.valueOf(Math.PI));

        } else {
            return MathUtils.taylorATan(this.imaginary.divide(this.real, MAX_PRECISION, ROUND_FLOOR));
        }
    }

    public Complex pow(int exponent) {
        if (exponent < 0 || exponent > 9999) {
            throw new IllegalArgumentException("Negative Exponent");
        }
        BigDecimal module = this.module().pow(exponent);
        BigDecimal fi = this.complexArgument().multiply(BigDecimal.valueOf(exponent));
        return new Complex(MathUtils.taylorCos(fi).multiply(module), MathUtils.taylorSin(fi).multiply(module));
    }

    private BigInteger bigFact(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public BigDecimal getReal() {
        return real;
    }

    public BigDecimal getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        return real.toPlainString() + imaginary.toPlainString() + "i";
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
    public boolean equals(NumberConstant constant) {
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
}
