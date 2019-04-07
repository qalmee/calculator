package calculator.model.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigDecimal.ROUND_FLOOR;

public final class MathUtils {

    private static final int TAYLOR_SERIES_LENGTH = 30;
    private static final int MAX_PRECISION = 100;
    private static final int MAX_COMPARE_PRECISION = 90;
    private static final BigDecimal EPS = BigDecimal.ONE
            .divide(BigDecimal.TEN.pow(MAX_COMPARE_PRECISION), MAX_COMPARE_PRECISION, BigDecimal.ROUND_FLOOR);

    private MathUtils() {
    }

    public static BigDecimal taylorSin(BigDecimal a) {
        a = a.remainder(BigDecimal.valueOf(Math.PI).multiply(BigDecimal.valueOf(2)));
        BigDecimal addend = a;
        BigInteger denominator = BigInteger.ONE;
        BigDecimal ans = BigDecimal.ZERO;
        for (long i = 0; i < TAYLOR_SERIES_LENGTH; i++) {
            ans = ans.add(addend.divide(new BigDecimal(denominator), MAX_PRECISION, ROUND_FLOOR));
            denominator = denominator.multiply(BigInteger.valueOf((i * 2 + 2)));
            denominator = denominator.multiply(BigInteger.valueOf((i * 2 + 3)));
            addend = addend.multiply(a).multiply(a).negate();
        }
        return ans;
    }

    public static BigDecimal taylorCos(BigDecimal a) {
        a = a.remainder(BigDecimal.valueOf(Math.PI).multiply(BigDecimal.valueOf(2)));
        BigDecimal addend = a.multiply(a).negate();
        BigInteger denominator = BigInteger.ONE.add(BigInteger.ONE);
        BigDecimal ans = BigDecimal.ONE;
        for (long i = 1; i < TAYLOR_SERIES_LENGTH; i++) {
            ans = ans.add(addend.divide(new BigDecimal(denominator), MAX_PRECISION, ROUND_FLOOR));
            denominator = denominator.multiply(BigInteger.valueOf((i * 2 + 1)));
            denominator = denominator.multiply(BigInteger.valueOf((i * 2 + 2)));
            addend = addend.multiply(a).multiply(a).negate();
        }
        return ans;
    }

    public static BigDecimal taylorTan(BigDecimal a) {
        return taylorSin(a).divide(taylorCos(a), MAX_PRECISION, ROUND_FLOOR);
    }

    public static BigDecimal taylorCtan(BigDecimal a) {
        return taylorCos(a).divide(taylorSin(a), MAX_PRECISION, ROUND_FLOOR);
    }

    private static BigDecimal aTan(BigDecimal a) {
        BigDecimal addend = a;
        BigDecimal denominator = BigDecimal.ONE;
        BigDecimal ans = BigDecimal.ZERO;
        for (long i = 0; i < TAYLOR_SERIES_LENGTH; i++) {
            ans = ans.add(addend.divide(denominator, MAX_PRECISION, ROUND_FLOOR));
            denominator = denominator.add(BigDecimal.valueOf(2));
            addend = addend.multiply(a).multiply(a).negate();
        }
        return ans;
    }

    public static BigDecimal taylorATan(BigDecimal a) {
        if (a.abs().subtract(BigDecimal.ONE).compareTo(EPS) <= 0) {
            return BigDecimal.valueOf(Math.PI / 4.0).multiply(BigDecimal.valueOf(a.compareTo(BigDecimal.ZERO)));
        } else if (a.abs().compareTo(BigDecimal.ONE) > 0) {
            BigDecimal absOfA = a.abs();
            BigDecimal result = BigDecimal.valueOf(Math.PI / 2.0).subtract(aTan(BigDecimal.ONE.divide(absOfA, MAX_PRECISION, ROUND_FLOOR)));
            if (a.compareTo(BigDecimal.ZERO) < 0) {
                result = result.negate();
            }
            return result;
        } else {
            return aTan(a);
        }
    }

    public static BigDecimal radToDegrees(BigDecimal fi) {
        return fi.multiply(BigDecimal.valueOf(180).divide(BigDecimal.valueOf(Math.PI), MAX_PRECISION, ROUND_FLOOR));
    }
}
