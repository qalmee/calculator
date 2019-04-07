package calculator.model.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConverterPToP {

    private static final int MAX_BASE = 16;
    private static final int MIN_BASE = 2;
    private static final int MAX_PRECISION = 100;

    private ConverterPToP() {

    }

    public static String convert10ToPAdaptive(String valueString, int base) {
        if (base == 10) return valueString;
        String result = convert10ToP(valueString, base, MAX_PRECISION);
        return cutTrailingZeros(result);
    }

    public static String convertPTo10Adaptive(String valueString, int base) {
        if (base == 10) return valueString;
        String result = convertPTo10(valueString, base, MAX_PRECISION);
        return cutTrailingZeros(result);
    }

    public static String convert10ToP(String valueString, int base, int precision) {
        checkArguments(valueString, base, precision);
        valueString = valueString.toUpperCase();
        BigDecimal value = new BigDecimal(valueString);
        value = value.stripTrailingZeros();
        BigInteger number = value.toBigInteger();
        StringBuilder intResult = convertBigInteger10ToP(number, base);

        if (precision == 0 || !valueString.contains(".")) {
            return intResult.toString();
        }

        if (value.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
            StringBuilder sb = new StringBuilder();
            addZerosToFractionPart(sb, precision);
            return intResult.toString() + "." + sb.toString();
        }

        BigDecimal fraction = value.subtract(new BigDecimal(number));
        StringBuilder fractionResult = convertFraction10ToP(fraction, base, precision);
        intResult.append(".");
        if (fractionResult.length() > precision) {
            fractionResult.delete(precision, fractionResult.length());
        } else {
            addZerosToFractionPart(fractionResult, precision);
        }
        intResult.append(fractionResult);
        if (number.equals(BigInteger.ZERO) && value.compareTo(BigDecimal.ZERO) < 0) {
            intResult.insert(0, '-');
        }
        return intResult.toString();
    }


    private static StringBuilder convertBigInteger10ToP(BigInteger value, int base) {
        if (value.equals(BigInteger.ZERO)) {
            return new StringBuilder("0");
        }
        boolean negative = value.compareTo(BigInteger.ZERO) < 0;
        value = value.abs();
        StringBuilder result = new StringBuilder();
        while (!value.equals(BigInteger.ZERO)) {
            result.append(Digits.getDigitFromInt(value.remainder(BigInteger.valueOf(base)).intValue()));
            value = value.divide(BigInteger.valueOf(base));
        }
        if (negative) {
            result.append('-');
        }
        result.reverse();
        return result;
    }

    private static StringBuilder convertFraction10ToP(BigDecimal value, int base, int precision) {
        BigDecimal multiplier = (BigDecimal.ONE.divide(BigDecimal.valueOf(base),
                (precision + 1) * 2, BigDecimal.ROUND_FLOOR));
        value = value.abs();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= precision; i++) {
            int digit = value.divideToIntegralValue(multiplier).intValue();
            if (digit > 0) {
                value = value.subtract(multiplier.multiply(BigDecimal.valueOf(digit)));
            }
            result.append(Digits.getDigitFromInt(digit));
            multiplier = multiplier.divide(BigDecimal.valueOf(base), (precision + 1) * 2, BigDecimal.ROUND_FLOOR);
        }
        return result;
    }

    public static String convertPTo10(String value, int base, int precision) {
        checkArguments(value, base, precision);
        value = cutTrailingZeros(value).toUpperCase();
        String[] stringArray = value.split("\\.");
        BigInteger number = convertBigIntegerPTo10(stringArray[0], base);
        String fraction = "";

        if (stringArray.length > 1) {
            fraction = convertFractionPTo10(stringArray[1], base, precision).toString();
            String[] tmp = fraction.split("\\.");
            if (tmp.length > 1) {
                fraction = tmp[1];
            } else {
                fraction = "";
            }
        }
        if (stringArray.length == 1 || precision == 0) {
            return number.toString();
        }

        StringBuilder sb = addZerosToFractionPart(new StringBuilder(fraction), precision);
        if (value.charAt(0) == '-' && number.equals(BigInteger.ZERO)) {
            return "-" + number.toString() + "." + sb.substring(0, precision);
        }
        return number.toString() + "." + sb.substring(0, precision);
    }

    private static BigInteger convertBigIntegerPTo10(String value, int base) {
        boolean negative = value.startsWith("-");
        if (negative) {
            value = value.substring(1);
        }
        BigInteger multiplier = BigInteger.ONE;
        BigInteger result = BigInteger.ZERO;
        for (int i = value.length() - 1; i >= 0; i--) {
            result = result.add(multiplier.multiply(BigInteger.valueOf(Digits.getDigitFromChar(value.charAt(i)))));
            multiplier = multiplier.multiply(BigInteger.valueOf(base));
        }
        if (negative) result = result.negate();
        return result;
    }

    private static BigDecimal convertFractionPTo10(String value, int base, int precision) {
        BigDecimal multiplier = (BigDecimal.ONE.divide(BigDecimal.valueOf(base),
                (precision + 1) * 2, BigDecimal.ROUND_FLOOR));
        BigDecimal result = new BigDecimal(0);
        for (char digit : value.toCharArray()) {
            result = result.add(multiplier.multiply(BigDecimal.valueOf(Digits.getDigitFromChar(digit))));
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.0 / base));
        }
        return result;
    }

    private static String cutTrailingZeros(String value) {
        if (!value.contains(".")) {
            return value;
        }
        StringBuilder sb = new StringBuilder(value);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }
        if (sb.charAt(sb.length() - 1) == '.') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static void checkArguments(String valueString, int base, int precision) {
        if (valueString.isEmpty()) {
            throw new IllegalArgumentException("Value string can not be empty");
        }
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be from " + MIN_BASE + " to " + MAX_BASE);
        }
        if (precision < 0 || precision > MAX_PRECISION) {
            throw new IllegalArgumentException("Precision must be from 0 to " + MAX_PRECISION);
        }
    }

    private static StringBuilder addZerosToFractionPart(StringBuilder builder, int precision) {
        for (int i = builder.length(); i < precision; i++) {
            builder.append('0');
        }
        return builder;
    }
}
