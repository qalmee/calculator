package calculator.model;

public interface Number<T extends Number> {
    T add(T b);
    T substract(T b);
    T multiply(T b);
    T divide(T b);
    //this method counts 1/x value
    T reverse();
}
