package calculator.view.scene;

import calculator.model.CalculatorMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

public class PNumberCalculatorScene extends CalculatorScene {

    private static final int SLIDER_MIN_VALUE = 2;
    private static final int SLIDER_MAX_VALUE = 16;
    private static final int SLIDER_DEFAULT_VALUE = 10;
    private static final int SLIDER_BLOCK_INCREMENT = 1;
    private static final int SLIDER_MAJOR_TICK_UNIT = 1;
    private static final int SLIDER_MINOR_TICK_COUNT = 0;

    private static final int SLIDER_WIDTH_SIZE = 490;

    private static final int LABEL_PADDING_LEFT = 10;
    private static final int SLIDER_PADDING_TOP = 25;

    private static final String FONT_FAMILY = "System";

    private static final Font LABEL_TEXT_FONT = Font.font(FONT_FAMILY, 20);

    private HBox bottomPanel;
    private Slider sliderBase;

    public PNumberCalculatorScene() {
        super(CalculatorMode.P_NUMBER);
        setupPNumberButtons();
        setupBottomPanel();
        setupLabelBase();
        setupSliderBase();
    }

    @Override
    public void onInitializationComplete() {
        updateDigitButtonsOnStart();
    }

    private void setupPNumberButtons() {
        List<CalculatorButtons> pNumberDigitButtons = CalculatorButtons.getPNumberDigitButtons();
        pNumberDigitButtons.forEach(button -> configureDigitButton(button.getButton()));
    }

    private void setupBottomPanel() {
        bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.CENTER_LEFT);
        this.addElementToMainPanel(bottomPanel);
    }

    private void setupLabelBase() {
        Label labelBase = new Label(getProperty("p-number_calculator_scene.label_base"));
        labelBase.setPadding(new Insets(LABEL_PADDING_LEFT));
        labelBase.setFont(LABEL_TEXT_FONT);
        bottomPanel.getChildren().add(labelBase);
    }

    private void setupSliderBase() {
        sliderBase = new Slider();
        sliderBase.setMin(SLIDER_MIN_VALUE);
        sliderBase.setMax(SLIDER_MAX_VALUE);
        sliderBase.setShowTickLabels(true);
        sliderBase.setShowTickMarks(true);
        sliderBase.setBlockIncrement(SLIDER_BLOCK_INCREMENT);
        sliderBase.setMajorTickUnit(SLIDER_MAJOR_TICK_UNIT);
        sliderBase.setMinorTickCount(SLIDER_MINOR_TICK_COUNT);
        sliderBase.setSnapToTicks(true);
        sliderBase.setPadding(new Insets(SLIDER_PADDING_TOP, 0, 0, 0));
        sliderBase.setPrefWidth(SLIDER_WIDTH_SIZE);
        sliderBase.setValue(SLIDER_DEFAULT_VALUE);
        bottomPanel.getChildren().add(sliderBase);
        setupSliderBaseUpdateListener();
    }

    private void setupSliderBaseUpdateListener() {
        sliderBase.valueProperty().addListener((observable, oldValue, newValue) ->
                controllerListener.updateDigitButtons(newValue.intValue()));
    }

    private void updateDigitButtonsOnStart() {
        controllerListener.updateDigitButtons((int) sliderBase.getValue());
    }
}