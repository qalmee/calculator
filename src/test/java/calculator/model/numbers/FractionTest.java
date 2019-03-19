package calculator.model.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FractionTest {

    @ParameterizedTest
    @CsvSource({"1,1,1,1,2,1", "0,1,1,1,1,1", "1,2,1,2,1,1", "-1,2,1,2,0,2", "123,17,6534,45,12957,85"
            , "17,123,45,6534,4319,29766", "0,2,0,4,0,123456"})
    void addTest(BigInteger numerator1, BigInteger denominator1,
                 BigInteger numerator2, BigInteger denominator2,
                 BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator1, denominator1);
        Fraction b = new Fraction(numerator2, denominator2);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.add(b).simplify());
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,1,0,1", "0,1,1,1,-1,1", "-1,2,1,2,-1,1", "1,2,-1,2,1,1", "123,17,6534,45,-11727,85"
            , "17,123,45,6534,1303,9922", "0,2,0,4,0,123456"})
    void subtractTest(BigInteger numerator1, BigInteger denominator1,
                      BigInteger numerator2, BigInteger denominator2,
                      BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator1, denominator1);
        Fraction b = new Fraction(numerator2, denominator2);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.subtract(b).simplify());
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,1,1,1", "0,1,1,1,0,1", "-3,2,5,2,-15,4", "1,2,-2,1,-1,1", "123,-17,6534,45,-89298,85"
            , "0,2,0,4,0,123456"})
    void multiplyTest(BigInteger numerator1, BigInteger denominator1,
                      BigInteger numerator2, BigInteger denominator2,
                      BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator1, denominator1);
        Fraction b = new Fraction(numerator2, denominator2);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.multiply(b).simplify());
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,1,1,1", "0,1,1,1,0,1", "-3,2,5,2,-3,5", "1,2,2,-1,-1,4", "123,-17,6534,45,-205,4114"
            , "7,15,14,30,1,1", "-17,123,6534,45,-85,89298"})
    void divideTest(BigInteger numerator1, BigInteger denominator1,
                    BigInteger numerator2, BigInteger denominator2,
                    BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator1, denominator1);
        Fraction b = new Fraction(numerator2, denominator2);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.divide(b).simplify());
    }

    @ParameterizedTest
    @CsvSource({"1,1,-1,1", "0,1,0,1", "-3,2,3,2", "1,-2,1,2", "-123,-17,-123,17"})
    void negateTest(BigInteger numerator, BigInteger denominator,
                    BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator, denominator);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.negate().simplify());
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,1", "-3,2,-2,3", "1,-2,-2,1", "-123,-17,17,123"})
    void reverseTest(BigInteger numerator, BigInteger denominator,
                     BigInteger resultNumerator, BigInteger resultDenominator) {
        Fraction a = new Fraction(numerator, denominator);
        assertEquals(new Fraction(resultNumerator, resultDenominator).simplify(), a.reverse().simplify());
    }
}