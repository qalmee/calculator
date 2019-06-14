package calculator.model.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplexTest {

    @ParameterizedTest
    @CsvSource({"3,5,1,2,4,7", "0,0,0,0,0,0", "3,5,-3,-6,0,-1"})
    void add(BigDecimal real1, BigDecimal im1,
             BigDecimal real2, BigDecimal im2,
             BigDecimal realResult, BigDecimal imResult) {
        Complex complex1 = new Complex(real1, im1);
        Complex complex2 = new Complex(real2, im2);
        Complex complexResult = new Complex(realResult, imResult);
        assertEquals(complexResult, complex1.add(complex2));
    }

    @ParameterizedTest
    @CsvSource({"3,5,1,2,2,3", "0,0,0,0,0,0", "3,5,-3,-6,6,11", "-1,0,-3,5,2,-5"})
    void subtract(BigDecimal real1, BigDecimal im1,
                  BigDecimal real2, BigDecimal im2,
                  BigDecimal realResult, BigDecimal imResult) {
        Complex complex1 = new Complex(real1, im1);
        Complex complex2 = new Complex(real2, im2);
        Complex complexResult = new Complex(realResult, imResult);
        assertEquals(complexResult, complex1.subtract(complex2));
    }

    @ParameterizedTest
    @CsvSource({"3,5,-1,-6,27,-23", "-4,-2,-1,-6,-8,26", "-4,-2,1,6,8,-26"
            , "-2,-7,0,6,42,-12", "-2,-7,3,0,-6,-21", "1,2,0,0,0,0"})
    void multiply(BigDecimal real1, BigDecimal im1,
                  BigDecimal real2, BigDecimal im2,
                  BigDecimal realResult, BigDecimal imResult) {
        Complex complex1 = new Complex(real1, im1);
        Complex complex2 = new Complex(real2, im2);
        Complex complexResult = new Complex(realResult, imResult);
        assertEquals(complexResult, complex1.multiply(complex2));
    }

    @ParameterizedTest
    @CsvSource({"0,0,0,0,0", "3,-5,0,0,0", "3,-5,1,3,-5", "-1,-3,-2,2,6"})
    void scale(BigDecimal real, BigDecimal im,
               BigDecimal scale,
               BigDecimal realResult, BigDecimal imResult) {
        Complex complex1 = new Complex(real, im);
        Complex complexResult = new Complex(realResult, imResult);
        assertEquals(complexResult, complex1.scale(scale));
    }

    @ParameterizedTest
    @CsvSource({"3,5,-1,-6,-33,13,37", "-4,-2,-1,-6,16,-22,37", "-4,-2,1,6,-16,22,37"
            , "-2,-7,0,6,-7,2,6", "-2,-7,3,0,-2,-7, 3", "0,0,2,2,0,0,1"})
    void divide(BigDecimal real1, BigDecimal im1,
                BigDecimal real2, BigDecimal im2,
                BigDecimal realResultNumerator, BigDecimal imResultNumerator,
                BigDecimal resultDenominator) {
        Complex complex1 = new Complex(real1, im1);
        Complex complex2 = new Complex(real2, im2);
        Complex complexResult =
                new Complex(realResultNumerator.divide(resultDenominator, 100, BigDecimal.ROUND_FLOOR),
                        imResultNumerator.divide(resultDenominator, 100, BigDecimal.ROUND_FLOOR));
        assertEquals(complexResult, complex1.divide(complex2));
    }

    @ParameterizedTest
    @CsvSource({"3,5,3,-5,34", "-4,-2,-2,1,10", "0,-2,0,1,2"})
    void reverse(BigDecimal real, BigDecimal im,
                 BigDecimal realResultNumerator, BigDecimal imResultNumerator,
                 BigDecimal resultDenominator) {
        Complex complex = new Complex(real, im);
        Complex complexResult = new Complex(realResultNumerator.divide(resultDenominator, 100, BigDecimal.ROUND_FLOOR),
                imResultNumerator.divide(resultDenominator, 100, BigDecimal.ROUND_FLOOR));
        assertEquals(complexResult, complex.reverse());
    }

    @ParameterizedTest
    @CsvSource({"3,5,-3,-5", "-4,-2,4,2", "0,-2,0,2"})
    void negate(BigDecimal real, BigDecimal im,
                BigDecimal realResult, BigDecimal imResult) {
        Complex complex = new Complex(real, im);
        Complex complexResult = new Complex(realResult, imResult);
        assertEquals(complexResult, complex.negate());
    }

//    @ParameterizedTest
//    @CsvSource({"3,5,-3,-6"})
//    void negate1(BigDecimal real, BigDecimal im,
//                BigDecimal realResult, BigDecimal imResult) {
//        Complex complex = new Complex(real, im);
//        Complex complexResult = new Complex(realResult, imResult);
//        assertEquals(complexResult, complex.negate());
//    }
}