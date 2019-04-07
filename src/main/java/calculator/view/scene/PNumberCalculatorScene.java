package calculator.view.scene;

import calculator.model.observer.PNumberCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.ErrorState;
import calculator.view.scene.components.CalculatorButtons;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.util.List;

import static calculator.view.localization.LanguageProperties.getProperty;

class PNumberCalculatorScene extends CalculatorScene implements PNumberCalculatorObserver {

    private static final int SLIDER_MIN_VALUE = 2;
    private static final int SLIDER_MAX_VALUE = 16;
    private static final int SLIDER_DEFAULT_VALUE = 10;

    private HBox bottomPanel;
    private Slider sliderBase;

    PNumberCalculatorScene() {
        super(CalculatorMode.P_NUMBER);
    }

    @Override
    public void setBase(int base) {
        sliderBase.setValue(base);
    }

    @Override
    public void initializeScene() {
        super.initializeScene();
        setupPNumberButtons();
        setupBottomPanel();
        setupLabelBase();
        setupSliderBase();
        updateDigitButtonsOnStart();
        controllerListener.setPNumberCalculatorObserver(this);
    }

    @Override
    void setAtErrorState(ErrorState errorState) {
        super.setAtErrorState(errorState);
        sliderBase.setDisable(true);
        controllerListener.updateDigitButtons((int) sliderBase.getValue());
    }

    @Override
    void setToNormalState() {
        super.setToNormalState();
        sliderBase.setDisable(false);
        controllerListener.updateDigitButtons((int) sliderBase.getValue());
    }

    private void setupPNumberButtons() {
        List<CalculatorButtons> pNumberDigitButtons = CalculatorButtons.getPNumberDigitButtons();
        pNumberDigitButtons.forEach(button -> configureDigitButton(button.getButton()));
    }

    private void setupBottomPanel() {
        bottomPanel = new HBox();
        bottomPanel.getStyleClass().add("h_box_bottom_pane");
        this.addElementToMainPanel(bottomPanel);
    }

    private void setupLabelBase() {
        Label labelBase = new Label(getProperty("p-number_calculator_scene.label_base"));
        labelBase.getStyleClass().add("label_base");
        bottomPanel.getChildren().add(labelBase);
    }

    private void setupSliderBase() {
        sliderBase = new Slider();
        sliderBase.getStyleClass().add("slider_base");
        sliderBase.setMin(SLIDER_MIN_VALUE);
        sliderBase.setMax(SLIDER_MAX_VALUE);
        sliderBase.setValue(SLIDER_DEFAULT_VALUE);
        bottomPanel.getChildren().add(sliderBase);
        setupSliderBaseUpdateListener();
    }

    private void setupSliderBaseUpdateListener() {
        sliderBase.valueProperty().addListener((observable, oldBase, newBase) -> {
            controllerListener.setNewBase(newBase.intValue());
            controllerListener.updateDigitButtons(newBase.intValue());
            controllerListener.convertValue(getValueFromTextFieldValue(), oldBase.intValue(), newBase.intValue());
        });
    }

    private void updateDigitButtonsOnStart() {
        controllerListener.updateDigitButtons((int) sliderBase.getValue());
    }
}
