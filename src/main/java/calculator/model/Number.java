package calculator.model;

public interface Number<T extends Number> {
    T add(T b);

    T subtract(T b);

    T multiply(T b);

    T divide(T b);

    T reverse();

    T negate();
}
