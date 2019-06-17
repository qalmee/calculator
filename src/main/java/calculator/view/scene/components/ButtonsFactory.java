package calculator.view.scene.components;

import javafx.scene.control.Button;

import static calculator.view.localization.LanguageProperties.getProperty;

public abstract class ButtonsFactory {

    static Button getEquals() {
        Button equals = new Button(getProperty("calculator_scene.button_enter"));
        equals.setId("equals");
        return equals;
    }

    static Button getDivide() {
        Button divide = new Button(getProperty("calculator_scene.button_divide"));
        divide.setId("divide");
        return divide;
    }

    static Button getMultiply() {
        Button multiply = new Button(getProperty("calculator_scene.button_multiply"));
        multiply.setId("multiply");
        return multiply;
    }

    static Button getPlus() {
        Button button = new Button(getProperty("calculator_scene.button_add"));
        button.setId("add");
        return button;
    }

    static Button getMinus() {
        Button button = new Button(getProperty("calculator_scene.button_subtract"));
        button.setId("subtract");
        return button;
    }
}
