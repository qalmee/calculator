package calculator.model.utils;

import calculator.model.numbers.Complex;
import calculator.model.numbers.Fraction;
import calculator.model.numbers.Real;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum NumberConstant {
    ZERO(new Real(BigDecimal.ZERO), new Fraction(BigInteger.ZERO, BigInteger.ONE), new Complex(BigDecimal.ZERO, BigDecimal.ZERO));

    private Real real;
    private Fraction fraction;
    private Complex complex;

    NumberConstant(Real r, Fraction f, Complex c) {
        real = r;
        fraction = f;
        complex = c;
    }

    public Real getReal() {
        return real;
    }

    public Fraction getFraction() {
        return fraction;
    }

    public Complex getComplex() {
        return complex;
    }
}
