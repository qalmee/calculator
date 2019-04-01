package calculator.model.utils;

import calculator.model.numbers.Complex;
import calculator.model.numbers.Fraction;
import calculator.model.numbers.Number;
import calculator.model.numbers.Real;
import calculator.model.stats.CalculatorMode;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberConverter {

    private NumberConverter() {

    }

    public static Number stringToNumber(String value, CalculatorMode mode) {
        switch (mode) {
            case P_NUMBER:
                return stringToReal(value);
            case COMPLEX:
                return stringToComplex(value);
            case FRACTION:
                return stringToFraction(value);
            case BASIC:
                return stringToReal(value);
            default:
                return null;
        }

    }

    private static Number stringToReal(String value) {
        return new Real(new BigDecimal(value));
    }

    //format : x + yi,
    private static Number stringToComplex(String value) {
        value = value.replaceAll("\\s+", "");
        boolean realIsNegative = false;
        boolean imIsNegative = false;
        if (value.startsWith("-")) {
            realIsNegative = true;
            value = value.replaceFirst("-", "");
        }
        if (value.contains("-")) {
            imIsNegative = true;
        }
        value = value.replaceFirst("[-+]", " ");
        String[] arr = value.split("\\s");
        if (arr.length > 2) {
            throw new IllegalArgumentException("");
        }
        BigDecimal real = BigDecimal.ZERO;
        BigDecimal im = BigDecimal.ZERO;
        if (arr[0].endsWith("i")) {
            im = new BigDecimal(arr[0].replaceFirst("i", ""));
        } else {
            real = new BigDecimal(arr[0]);
        }
        if (arr.length > 1) {
            im = new BigDecimal(arr[1].replaceFirst("i", ""));
        }

        if (realIsNegative) {
            real = real.negate();
        }
        if (imIsNegative) {
            im = im.negate();
        }
        return new Complex(real, im);
    }

    //format : a/b
    private static Number stringToFraction(String value) {
        value = value.replaceAll("\\s+", "");
        String[] values = value.split("/");
        if (values.length != 2) {
            throw new IllegalArgumentException("");
        }
        BigInteger numerator = new BigInteger(values[0]);
        BigInteger denominator = new BigInteger(values[1]);
        return new Fraction(numerator, denominator);
    }
}
