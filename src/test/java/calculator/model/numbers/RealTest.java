package calculator.model.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RealTest {

    @ParameterizedTest
    @CsvSource({"1,1,2", "0,1,1", "-1,-2,-3", "100, -100, 0", "1.1, -1.1, 0"
            , "1.1, 1.2, 2.3", "7.80, 5.6, 13.4"})
    void add(BigDecimal first, BigDecimal second, BigDecimal result) {
        Real a = new Real(first);
        Real b = new Real(second);
        Real res = new Real(result);
        assertEquals(res, a.add(b));
    }

    @ParameterizedTest
    @CsvSource({"1,1,0", "0,1,-1", "-1,-2,1", "100, -100, 200", "1.1, -1.1, 2.2"
            , "1.1, 1.200, -0.1", "7.80, 5.6, 2.2"})
    void subtract(BigDecimal first, BigDecimal second, BigDecimal result) {
        Real a = new Real(first);
        Real b = new Real(second);
        Real res = new Real(result);
        assertEquals(res, a.subtract(b));
    }

    @ParameterizedTest
    @CsvSource({"1,1,1", "0,1,0", "-1,-2,2", "100, -100, -10000", "1.1, -1.1, -1.21"
            , "1.1, 1.200, 1.32", "7.80, 5.6, 43.68"})
    void multiply(BigDecimal first, BigDecimal second, BigDecimal result) {
        Real a = new Real(first);
        Real b = new Real(second);
        Real res = new Real(result);
        assertEquals(res, a.multiply(b));
    }

    @ParameterizedTest
    @CsvSource({"1,1,1", "0,1,0", "-1,-2,0.5", "100, -100, -1", "1.1, -1.1, -1"})
    void divide(BigDecimal first, BigDecimal second, BigDecimal result) {
        Real a = new Real(first);
        Real b = new Real(second);
        Real res = new Real(result);
        assertEquals(res, a.divide(b));
    }

    @ParameterizedTest
    @CsvSource({"1,1", "10,0.1", "-2,-0.5"})
    void reverse(BigDecimal first, BigDecimal result) {
        Real a = new Real(first);
        Real res = new Real(result);
        assertEquals(res, a.reverse());
    }

    @ParameterizedTest
    @CsvSource({"1,-1", "10,-10", "-2,2", "0, 0"})
    void negate(BigDecimal first, BigDecimal result) {
        Real a = new Real(first);
        Real res = new Real(result);
        assertEquals(res, a.negate());
    }
}