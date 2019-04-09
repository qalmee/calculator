package calculator.view.scene;

import calculator.model.observer.FractionCalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.view.scene.components.CalculatorButtons;
import javafx.scene.control.Button;

import static calculator.view.localization.LanguageProperties.getProperty;

class FractionCalculatorScene extends CalculatorScene implements FractionCalculatorObserver {

    private static final int TEXT_FIELD_VALUE_MAX_INPUT_TEXT_LENGTH = 25;

    private static final String BUTTON_DELIMITER_NUMERATOR_TEXT;
    private static final String BUTTON_DELIMITER_DENOMINATOR_TEXT;
    private static final String START_NUMERATOR;
    private static final String START_DENOMINATOR;

    static {
        BUTTON_DELIMITER_NUMERATOR_TEXT = getProperty("fraction_calculator_scene.button_delimiter_numerator");
        BUTTON_DELIMITER_DENOMINATOR_TEXT = getProperty("fraction_calculator_scene.button_delimiter_denominator");

        START_NUMERATOR = CalculatorMode.FRACTION.getStartValue().split("/")[0];
        START_DENOMINATOR = CalculatorMode.FRACTION.getStartValue().split("/")[1];
    }

    private FractionCaretPosition caretPosition;
    private boolean denominatorDefaultDigitClicked;

    FractionCalculatorScene() {
        super(CalculatorMode.FRACTION);
    }

    @Override
    public void setCaretToNumerator() {
        caretPosition = FractionCaretPosition.NUMERATOR;
        Button delimiterButton = CalculatorButtons.BUTTON_DELIMITER.getButton();
        delimiterButton.setText(BUTTON_DELIMITER_NUMERATOR_TEXT);
    }

    @Override
    public void initializeScene() {
        super.initializeScene();
        setupDelimiterButton();
        disableCommaButton(true);
        controllerListener.setFractionCalculatorObserver(this);
        caretPosition = FractionCaretPosition.NUMERATOR;
    }

    @Override
    void disableButtonsAfterErrorState() {
        super.disableButtonsAfterErrorState();
        CalculatorButtons.BUTTON_COMMA.getButton().setDisable(true);
    }

    @Override
    void appendDigitToTextFieldValue(String digitText) {
        String currentNumerator = getValueFromTextFieldValue().split("/")[0];
        String currentDenominator = getValueFromTextFieldValue().split("/")[1];

        if (currentNumerator.length() + currentDenominator.length() > TEXT_FIELD_VALUE_MAX_INPUT_TEXT_LENGTH) {
            textFieldValueSetText(currentNumerator + "/" + currentDenominator);
            return;
        }

        if (caretPosition == FractionCaretPosition.NUMERATOR) {
            if (currentNumerator.equals(START_NUMERATOR)) {
                currentNumerator = digitText;
            } else {
                currentNumerator = currentNumerator + digitText;
            }
        } else {
            if (currentDenominator.equals(START_DENOMINATOR)) {
                if (digitText.equals(START_DENOMINATOR) && !denominatorDefaultDigitClicked) {
                    denominatorDefaultDigitClicked = true;
                } else if (denominatorDefaultDigitClicked) {
                    denominatorDefaultDigitClicked = false;
                    currentDenominator = currentDenominator + digitText;
                } else {
                    currentDenominator = digitText;
                }
            } else {
                currentDenominator = currentDenominator + digitText;
            }
        }
        textFieldValueSetText(currentNumerator + "/" + currentDenominator);
        replaceZeroDenominator();
    }

    @Override
    void backspaceClickedAction() {
        String currentNumerator = getValueFromTextFieldValue().split("/")[0];
        String currentDenominator = getValueFromTextFieldValue().split("/")[1];

        if (caretPosition == FractionCaretPosition.NUMERATOR) {
            currentNumerator = currentNumerator.substring(0, currentNumerator.length() - 1);
            if (currentNumerator.isEmpty()) {
                currentNumerator = START_NUMERATOR;
            }
        } else {
            currentDenominator = currentDenominator.substring(0, currentDenominator.length() - 1);
            if (currentDenominator.isEmpty()) {
                currentDenominator = START_DENOMINATOR;
            }
        }
        textFieldValueSetText(currentNumerator + "/" + currentDenominator);
    }

    @Override
    void setupAndSetNewScene(CalculatorScene calculatorScene) {
        super.setupAndSetNewScene(calculatorScene);
        disableCommaButton(false);
    }

    private void replaceZeroDenominator() {
        String currentNumerator = getValueFromTextFieldValue().split("/")[0];
        String currentDenominator = getValueFromTextFieldValue().split("/")[1];

        if (currentDenominator.equals("0")) {
            currentDenominator = START_DENOMINATOR;
        }
        textFieldValueSetText(currentNumerator + "/" + currentDenominator);
    }

    private void setupDelimiterButton() {
        Button delimiterButton = CalculatorButtons.BUTTON_DELIMITER.getButton();
        delimiterButton.setText(BUTTON_DELIMITER_NUMERATOR_TEXT);
        delimiterButton.setOnAction(event -> {
            if (caretPosition == FractionCaretPosition.NUMERATOR) {
                caretPosition = FractionCaretPosition.DENOMINATOR;
                delimiterButton.setText(BUTTON_DELIMITER_DENOMINATOR_TEXT);
            } else {
                caretPosition = FractionCaretPosition.NUMERATOR;
                delimiterButton.setText(BUTTON_DELIMITER_NUMERATOR_TEXT);
            }
        });
    }

    private void disableCommaButton(boolean value) {
        Button commaButton = CalculatorButtons.BUTTON_COMMA.getButton();
        commaButton.setDisable(value);
    }

    private enum FractionCaretPosition {NUMERATOR, DENOMINATOR}
}
