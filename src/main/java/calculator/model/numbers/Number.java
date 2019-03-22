package calculator.model.numbers;

import java.math.BigDecimal;

public interface Number<T extends Number> {

    int MAX_PRECISION = 100;
    int MAX_COMPARE_PRECISION = 90;
    BigDecimal EPS = BigDecimal.ONE
            .divide(BigDecimal.TEN.pow(MAX_COMPARE_PRECISION), MAX_COMPARE_PRECISION, BigDecimal.ROUND_FLOOR);

    T add(T b);

    T subtract(T b);

    T multiply(T b);

    T divide(T b);

    T reverse();

    T negate();
}
