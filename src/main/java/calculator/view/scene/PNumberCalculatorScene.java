package calculator.view.scene;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

public class PNumberCalculatorScene extends AbstractCalculatorScene {

    private List<Button> pNumberButtons;

    public PNumberCalculatorScene() {
        createButtons();
        setupPNumberButtons();
    }

    private void createButtons() {
        pNumberButtons = new ArrayList<>();
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonA")));
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonB")));
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonC")));
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonD")));
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonE")));
        pNumberButtons.add(new Button(getProperty("p-number_calculator_scene.buttonF")));
    }

    private void setupPNumberButtons() {
        this.addRowToGridPane(pNumberButtons.toArray(new Button[0]));
        pNumberButtons.forEach(this::configureDigitButton);
    }
}
