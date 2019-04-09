package calculator.model.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtilsTest {

    @ParameterizedTest
    @EnumSource(Constants.class)
    void taylorSin(Constants constants) {
        assertEquals(BigDecimal.valueOf(Math.sin(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros(),
                MathUtils.taylorSin(BigDecimal.valueOf(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros());
    }

    @ParameterizedTest
    @EnumSource(Constants.class)
    void taylorCos(Constants constants) {
        assertEquals(BigDecimal.valueOf(Math.cos(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros(),
                MathUtils.taylorCos(BigDecimal.valueOf(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros());
    }

    @ParameterizedTest
    @EnumSource(Constants.class)
    void taylorATan(Constants constants) {
        assertEquals(BigDecimal.valueOf(Math.atan(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros(),
                MathUtils.taylorATan(BigDecimal.valueOf(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros());
    }

    @ParameterizedTest
    @EnumSource(Constants.class)
    void bigSqrt(Constants constants) {
        assertEquals(BigDecimal.valueOf(Math.sqrt(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros(),
                MathUtils.bigSqrt(BigDecimal.valueOf(constants.getValue())).round(new MathContext(5, RoundingMode.HALF_UP)).stripTrailingZeros());
    }
}