package calculator.model.numbers;

import java.math.BigDecimal;
import java.util.Objects;

public class Complex implements Number<Complex> {
    private static final int MAX_PRECISION = 100;
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

    public Complex scale(BigDecimal b) {
        return new Complex(this.real.multiply(b), this.imaginary.multiply(b));
    }

    @Override
    public Complex divide(Complex b) {
        BigDecimal scale = b.real.multiply(b.real).add(b.imaginary.multiply(b.imaginary));
        Complex c = new Complex(b.real.divide(scale, MAX_PRECISION, BigDecimal.ROUND_FLOOR),
                b.imaginary.negate().divide(scale, MAX_PRECISION, BigDecimal.ROUND_FLOOR));
        Complex a = this;
        return a.multiply(c);
    }

    //this method counts 1/x value
    @Override
    public Complex reverse() {
        BigDecimal square = this.square();
        return new Complex(this.conjugate().real.divide(square, MAX_PRECISION, BigDecimal.ROUND_FLOOR),
                this.conjugate().imaginary.divide(square, MAX_PRECISION, BigDecimal.ROUND_FLOOR));
    }

    @Override
    public Complex negate() {
        return new Complex(this.real.negate(), this.imaginary.negate());
    }


    public Complex conjugate() {
        return new Complex(this.real, this.imaginary.negate());
    }

    public BigDecimal square() {
        return this.real.multiply(this.real).subtract(this.imaginary.multiply(this.imaginary));
    }

    public BigDecimal getReal() {
        return real;
    }

    public void setReal(BigDecimal real) {
        this.real = real;
    }

    public BigDecimal getImaginary() {
        return imaginary;
    }

    public void setImaginary(BigDecimal imaginary) {
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return "Complex{" +
                "real=" + real +
                ", imaginary=" + imaginary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return real.equals(complex.real) &&
                imaginary.equals(complex.imaginary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }
}
