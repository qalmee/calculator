package calculator.view.scene;

import calculator.model.stats.CalculatorMode;
import calculator.view.scene.components.caretposition.FractionCaretPosition;
import calculator.view.scene.components.CalculatorButtons;
import javafx.scene.control.Button;

import static calculator.view.localization.LanguageProperties.getProperty;

class FractionCalculatorScene extends CalculatorScene {

    private static final String DEFAULT_NUMERATOR;
    private static final String DEFAULT_DENOMINATOR;

    static {
        DEFAULT_NUMERATOR = CalculatorMode.FRACTION.getStartValue().split("/")[0];
        DEFAULT_DENOMINATOR = CalculatorMode.FRACTION.getStartValue().split("/")[1];
    }

    private FractionCaretPosition caretPosition;
    private boolean denominatorDefaultDigitClicked;

    FractionCalculatorScene() {
        super(CalculatorMode.FRACTION);
    }

    @Override
    public void initializeScene() {
        super.initializeScene();
        setupDelimiterButton();
        disableCommaButton();
        caretPosition = FractionCaretPosition.NUMERATOR;
    }

    @Override
    void appendDigitToTextFieldValue(String digitText) {
        String currentNumerator = getValueFromTextFieldValue().split("/")[0];
        String currentDenominator = getValueFromTextFieldValue().split("/")[1];

        if (caretPosition == FractionCaretPosition.NUMERATOR) {
            if (currentNumerator.equals(DEFAULT_NUMERATOR)) {
                currentNumerator = digitText;
            } else {
                currentNumerator = currentNumerator + digitText;
            }
        } else {
            if (currentDenominator.equals(DEFAULT_DENOMINATOR)) {
                if (digitText.equals(DEFAULT_DENOMINATOR) && !denominatorDefaultDigitClicked) {
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
                currentNumerator = DEFAULT_NUMERATOR;
            }
        } else {
            currentDenominator = currentDenominator.substring(0, currentDenominator.length() - 1);
            if (currentDenominator.isEmpty()) {
                currentDenominator = DEFAULT_DENOMINATOR;
            }
        }
        textFieldValueSetText(currentNumerator + "/" + currentDenominator);
    }

    private void replaceZeroDenominator() {
        String currentNumerator = getValueFromTextFieldValue().split("/")[0];
        String currentDenominator = getValueFromTextFieldValue().split("/")[1];

        if (currentDenominator.equals("0")) {
            currentDenominator = DEFAULT_DENOMINATOR;
        }
        textFieldValueSetText(currentNumerator + "/" + currentDenominator);
    }

    private void setupDelimiterButton() {
        Button delimiterButton = CalculatorButtons.BUTTON_DELIMITER.getButton();
        delimiterButton.setText(getProperty("fraction_calculator_scene.button_delimiter_numerator"));
        delimiterButton.setOnAction(event -> {
            if (caretPosition == FractionCaretPosition.NUMERATOR) {
                caretPosition = FractionCaretPosition.DENOMINATOR;
                delimiterButton.setText(getProperty("fraction_calculator_scene.button_delimiter_denominator"));
            } else {
                caretPosition = FractionCaretPosition.NUMERATOR;
                delimiterButton.setText(getProperty("fraction_calculator_scene.button_delimiter_numerator"));
            }
        });
    }

    private void disableCommaButton() {
        Button commaButton = CalculatorButtons.BUTTON_COMMA.getButton();
        commaButton.setDisable(true);
    }
}
