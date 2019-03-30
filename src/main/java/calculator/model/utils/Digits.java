package calculator.model.utils;

import java.util.HashMap;
import java.util.Map;

class Digits {

    private static final Map<Character, Integer> DIGITS_FROM_CHAR;
    private static final Map<Integer, Character> DIGITS_FROM_INT;

    static {
        DIGITS_FROM_CHAR = new HashMap<>();
        DIGITS_FROM_INT = new HashMap<>();
        final int zero = '0';
        final int A = 'A';
        for (int i = 0; i<10; i++){
            DIGITS_FROM_CHAR.put((char)(zero + i), i);
            DIGITS_FROM_INT.put(i, (char)(zero + i));
        }
        for (int i = 0; i<6; i++){
            DIGITS_FROM_CHAR.put((char)(A + i), i + 10);
            DIGITS_FROM_INT.put(i + 10, (char)(A + i));
        }
    }

    private Digits() {

    }

    static int getDigitFromChar(char hexDigit) {
        if (!DIGITS_FROM_CHAR.containsKey(hexDigit)) {
            throw new IllegalArgumentException("Digit must be from 0 to F");
        }
        return DIGITS_FROM_CHAR.get(hexDigit);
    }

    static char getDigitFromInt(int digit) {
        if (!DIGITS_FROM_INT.containsKey(digit)) {
            throw new IllegalArgumentException("Digit must be from 0 to 15");
        }
        return DIGITS_FROM_INT.get(digit);
    }
}
