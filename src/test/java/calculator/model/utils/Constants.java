package calculator.model.utils;

enum Constants {
    PI_D_2(Math.PI / 2 - 0.001),
    PI_M_2(Math.PI * 2 - 0.001),
    PI(Math.PI - 0.0001),
    PI_D_6(Math.PI / 6),
    PI_D_4(Math.PI / 4);


    double value;

    Constants(double x) {
        value = x;
    }

    public double getValue() {
        return value;
    }
}
