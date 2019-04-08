package calculator.model.numbers;

import calculator.model.stats.CalculatorPrecision;
import calculator.model.utils.NumberConstant;

import java.math.BigDecimal;

public interface Number<T extends Number<T>> {

    int MAX_PRECISION = CalculatorPrecision.REAL_PRECISION.getPrecision();
    int MAX_COMPARE_PRECISION = CalculatorPrecision.REAL_PRECISION.getComparePrecision();
    BigDecimal EPS = BigDecimal.ONE
            .divide(BigDecimal.TEN.pow(MAX_COMPARE_PRECISION), MAX_COMPARE_PRECISION, BigDecimal.ROUND_FLOOR);

    T add(T b);

    T subtract(T b);

    T multiply(T b);

    T divide(T b);

    T reverse();

    T negate();

    T square();

    boolean compareToConst(NumberConstant constant);

    BigDecimal toBigDecimal();
}
