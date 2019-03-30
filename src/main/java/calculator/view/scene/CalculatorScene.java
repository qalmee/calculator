package calculator.view.scene;

import calculator.controller.ControllerListener;
import calculator.model.CalculatorMode;
import calculator.model.CalculatorOperation;
import calculator.model.MemoryOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.view.localization.Language;
import calculator.view.localization.LanguageProperties;
import calculator.view.scene.components.CalculatorButtons;
import calculator.view.scene.components.CalculatorMenu;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalculatorScene extends Scene implements CalculatorObserver {

    private static final int MAIN_PANEL_PADDING_SIZE = 5;

    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int ROW_CONSTRAINS_HEIGHT = 40;
    private static final int COLUMN_CONSTRAINS_WIDTH = 65;
    private static final int TEXT_FIELD_HEIGHT = 65;
    private static final int TEXT_FIELD_VALUE_MAX_TEXT_WIDTH_PIXELS = 380;
    private static final int TEXT_FIELD_VALUE_MAX_TEXT_LENGTH = 40;

    private static final String FONT_FAMILY = "System";

    private static final Font TEXT_FIELD_VALUE_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 30);
    private static final Font TEXT_FIELD_PREVIOUS_OPERATION_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 14);
    private static final Font BUTTONS_FONT = Font.font(FONT_FAMILY, 15);
    private static final Font BUTTONS_DIGIT_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 15);

    private static final Duration BUTTON_CLICK_EFFECT_DURATION = Duration.seconds(0.1);
    ControllerListener controllerListener;
    private CalculatorMode calculatorMode;

    private boolean needClearResult;

    private Clipboard clipboard;
    private VBox mainPanel;
    private GridPane buttonsGridPane;
    private TextField textFieldValue;
    private TextField textFieldPreviousOperation;

    CalculatorScene(CalculatorMode calculatorMode) {
        super(new VBox());
        this.calculatorMode = calculatorMode;
    }

    public void initializeScene() {
        setupMainPanel();
        setupMenu();
        setupTextFields();
        setupButtonsGridPane();
        setupButtons();
        setupHotKeys();
        setupClipboard();
        setStartValue();
    }

    @Override
    public void updateDigitButtons(List<String> buttonsText) {
        List<CalculatorButtons> allDigitButtons = new ArrayList<>();
        allDigitButtons.addAll(CalculatorButtons.getDigitButtons());
        allDigitButtons.addAll(CalculatorButtons.getPNumberDigitButtons());
        for (CalculatorButtons digitButton : allDigitButtons) {
            Button button = digitButton.getButton();
            String buttonText = button.getText();
            button.setDisable(buttonsText.contains(buttonText));
        }
    }

    @Override
    public void updateCalculatorMode(CalculatorMode calculatorMode) {
        changeScene(calculatorMode);
    }

    @Override
    public void updateLanguage(Language language) {
        LanguageProperties.setLanguage(language);
    }

    @Override
    public void setResult(String result) {
        System.out.println(result);
        textFieldValue.setText(result);
    }

    @Override
    public void setPreviousOperationText(String text) {
        textFieldPreviousOperation.setText(text);
    }

    @Override
    public void clearResultAfterEnteringDigit() {
        needClearResult = true;
    }

    @Override
    public void copyValueToClipboard() {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(textFieldValue.getText());
        clipboard.setContent(clipboardContent);
    }

    @Override
    public void pasteValueFromClipboard() {
        String value = clipboard.getString();
        textFieldValue.setText(value);
    }

    public void setControllerListener(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    private void setupMainPanel() {
        mainPanel = (VBox) this.getRoot();
    }

    private void setupMenu() {
        MenuBar calculatorMenu = new CalculatorMenu(controllerListener, calculatorMode);
        mainPanel.getChildren().add(calculatorMenu);
    }

    private void setupTextFields() {
        textFieldValue = new TextField();
        textFieldValue.setFont(TEXT_FIELD_VALUE_FONT);
        textFieldValue.setPrefSize(Region.USE_PREF_SIZE, TEXT_FIELD_HEIGHT);
        textFieldValue.textProperty().addListener((observable, oldValue, newValue) ->
                configureTextInTextFieldValue(oldValue, newValue));
        setupTextFieldStyle(textFieldValue);

        textFieldPreviousOperation = new TextField();
        textFieldPreviousOperation.setFont(TEXT_FIELD_PREVIOUS_OPERATION_FONT);
        setupTextFieldStyle(textFieldPreviousOperation);

        mainPanel.getChildren().addAll(textFieldPreviousOperation, textFieldValue);
    }

    private void setupTextFieldStyle(TextField textField) {
        textField.setAlignment(Pos.TOP_RIGHT);
        textField.setBackground(Background.EMPTY);
        textField.setStyle("-fx-display-caret: false");
        textField.setCursor(Cursor.DEFAULT);
        textField.setEditable(false);
        textField.setMouseTransparent(true);
        textField.setFocusTraversable(false);
    }

    private void setupButtonsGridPane() {
        buttonsGridPane = new GridPane();
        addRowAndColumnConstraintsToButtonsGridPane(
                calculatorMode.getCountButtonsGridPaneRows(), calculatorMode.getCountButtonsGridPaneColumns());
        buttonsGridPane.setAlignment(Pos.CENTER);
        buttonsGridPane.setGridLinesVisible(true);
        buttonsGridPane.setPadding(new Insets(0,
                MAIN_PANEL_PADDING_SIZE, MAIN_PANEL_PADDING_SIZE, MAIN_PANEL_PADDING_SIZE));
        mainPanel.getChildren().add(buttonsGridPane);
    }

    private void setupButtons() {
        CalculatorButtons[] buttons = CalculatorButtons.values();
        Arrays.stream(buttons).forEach(button -> configureButton(button.getButton()));
        List<CalculatorButtons> digitButtons = CalculatorButtons.getDigitButtons();
        digitButtons.forEach(button -> configureDigitButton(button.getButton()));
        addButtonsToGridPane();

        setupClearButtons();
        setupActionButtons();
        setupMemoryButtons();
        setupEnterButton();
        setupDotButton();
    }

    private void setupHotKeys() {
        ObservableMap<KeyCombination, Runnable> accelerators = this.getAccelerators();
        CalculatorButtons[] buttons = CalculatorButtons.values();
        Arrays.stream(buttons)
                .filter(button -> button.getKeyCodeCombination() != null)
                .forEach(button -> {
                    KeyCombination keyCombination = button.getKeyCodeCombination();
                    Runnable runnable = () -> setMouseClickEffectAndRunAction(button.getButton());
                    accelerators.put(keyCombination, runnable);
                });
    }

    private void setMouseClickEffectAndRunAction(Button button) {
        if (button.isDisabled()) {
            return;
        }
        button.arm();
        PauseTransition pause = new PauseTransition(BUTTON_CLICK_EFFECT_DURATION);
        pause.setOnFinished(e -> {
            button.disarm();
            button.fire();
        });
        pause.play();
    }

    private void setupClipboard() {
        clipboard = Clipboard.getSystemClipboard();
    }

    private void setupClearButtons() {
        Button buttonGlobalClear = CalculatorButtons.BUTTON_GLOBAL_CLEAR.getButton();
        Button buttonClearEntry = CalculatorButtons.BUTTON_CLEAR_ENTRY.getButton();
        Button buttonBackSpace = CalculatorButtons.BUTTON_BACKSPACE.getButton();

        buttonGlobalClear.setOnAction(event -> {
            clearTextFields();
            controllerListener.buttonGlobalClearClicked();
        });

        buttonClearEntry.setOnAction(event -> {
            clearTextFields();
            controllerListener.buttonClearEntryClicked();
        });

        buttonBackSpace.setOnAction(event -> {
            String textInTextField = textFieldValue.getText();
            if (!textInTextField.isEmpty()) {
                textFieldValue.setText(textInTextField.substring(0, textInTextField.length() - 1));
            }
        });
    }

    private void setupActionButtons() {
        List<CalculatorButtons> actionButtons = CalculatorButtons.getActionButtons();
        actionButtons.forEach(calculatorButton -> {
            Button button = calculatorButton.getButton();
            button.setOnAction(event -> {
                String number = textFieldValue.getText();
                CalculatorOperation calculatorOperation = CalculatorButtons.getCalculatorOperationFromButton(button);
                controllerListener.actionButtonClicked(number, calculatorOperation, calculatorMode);
            });
        });
    }

    private void setupMemoryButtons() {
        List<CalculatorButtons> memoryButtons = CalculatorButtons.getMemoryButtons();
        memoryButtons.forEach(calculatorButton -> {
            Button button = calculatorButton.getButton();
            button.setOnAction(event -> {
                String number = textFieldValue.getText();
                MemoryOperation memoryOperation = CalculatorButtons.getMemoryOperationFromButton(button);
                controllerListener.memoryButtonClicked(number, memoryOperation, calculatorMode);
            });
        });
    }

    private void setupEnterButton() {
        Button enterButton = CalculatorButtons.BUTTON_ENTER.getButton();
        enterButton.setOnAction(event -> {
            String number = textFieldValue.getText();
            controllerListener.buttonEnterClicked(number, calculatorMode);
        });
    }

    private void setupDotButton() {
        Button dotButton = CalculatorButtons.BUTTON_DOT.getButton();
        String commaSymbol = dotButton.getText();
        dotButton.setOnAction(event -> {
            if (!textFieldValue.getText().contains(commaSymbol)) {
                textFieldValue.appendText(dotButton.getText());
            }
        });
    }

    private void clearTextFields() {
        textFieldValue.clear();
        textFieldPreviousOperation.clear();
    }

    private void addButtonsToGridPane() {
        int countRows = calculatorMode.getCountButtonsGridPaneColumns();
        int countColumns = calculatorMode.getCountButtonsGridPaneColumns();
        Node[][] elementsInGridPane = new Node[countRows][countColumns];

        CalculatorButtons[] buttons = CalculatorButtons.values();
        Arrays.stream(buttons)
                .filter(button -> button.getCalculatorMode() == CalculatorMode.BASIC ||
                        button.getCalculatorMode() == calculatorMode)
                .forEach(button -> {
                    int row = button.getRowInGridPane();
                    int column = button.getColumnInGridPane();
                    elementsInGridPane[row][column] = button.getButton();
                });
        addEmptyPaneToBullGridPaneElements(elementsInGridPane);

        for (int i = 0; i < elementsInGridPane.length; ++i) {
            buttonsGridPane.addRow(i, elementsInGridPane[i]);
        }
    }

    private void addRowAndColumnConstraintsToButtonsGridPane(int countRows, int countColumns) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(ROW_CONSTRAINS_HEIGHT);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(COLUMN_CONSTRAINS_WIDTH);
        columnConstraints.setHalignment(HPos.CENTER);

        buttonsGridPane.getRowConstraints().addAll(Collections.nCopies(countRows, rowConstraints));
        buttonsGridPane.getColumnConstraints().addAll(Collections.nCopies(countColumns, columnConstraints));
    }

    private void addEmptyPaneToBullGridPaneElements(Node[][] elementsInGridPane) {
        for (int i = 0; i < elementsInGridPane.length; ++i) {
            for (int j = 0; j < elementsInGridPane[i].length; ++j) {
                if (elementsInGridPane[i][j] == null) {
                    elementsInGridPane[i][j] = new Pane();
                }
            }
        }
    }

    private void configureButton(Button button) {
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setFont(BUTTONS_FONT);
        button.setFocusTraversable(false);
    }

    private void changeScene(CalculatorMode mode) {
        if (calculatorMode != mode) {
            switch (mode) {
                case BASIC:
                    setupAndSetNewScene(new CalculatorScene(CalculatorMode.BASIC));
                    break;
                case FRACTION:
                    setupAndSetNewScene(new FractionCalculatorScene());
                    break;
                case COMPLEX:
                    setupAndSetNewScene(new ComplexCalculatorScene());
                    break;
                case P_NUMBER:
                    setupAndSetNewScene(new PNumberCalculatorScene());
                    break;
                default:
                    break;
            }
        }
    }

    private void setupAndSetNewScene(CalculatorScene calculatorScene) {
        Window calculatorWindow = this.getWindow();
        calculatorScene.setControllerListener(controllerListener);
        calculatorScene.initializeScene();
        ((Stage) calculatorWindow).setScene(calculatorScene);
        controllerListener.setNewObserver(calculatorScene);
    }

    private void setStartValue() {
        String startValue = calculatorMode.getStartValue();
        textFieldValue.setText(startValue);
    }

    private void configureTextInTextFieldValue(String oldValue, String newValue) {
        if (newValue.length() >= TEXT_FIELD_VALUE_MAX_TEXT_LENGTH) {
            textFieldValue.setText(newValue.substring(0, newValue.length() - 1));
        }
        if (newValue.isEmpty()) {
            textFieldValue.setText(calculatorMode.getStartValue());
        }
        if (oldValue.equals(calculatorMode.getStartValue()) && newValue.contains(calculatorMode.getStartValue())) {
            textFieldValue.setText(newValue.replaceAll(calculatorMode.getStartValue(), ""));
        }
        configureValueTextFieldFont();
    }

    private void configureValueTextFieldFont() {
        String text = textFieldValue.getText();
        double textWidth;
        Font textFont = TEXT_FIELD_VALUE_FONT;
        do {
            Text formattedText = new Text(text);
            formattedText.setFont(textFont);
            textWidth = formattedText.getLayoutBounds().getWidth();
            textFont = Font.font(textFont.getFamily(), FontWeight.BOLD, textFont.getSize() - 1);
        } while (textWidth > TEXT_FIELD_VALUE_MAX_TEXT_WIDTH_PIXELS);
        textFont = Font.font(textFont.getFamily(), FontWeight.BOLD, textFont.getSize() + 1);
        textFieldValue.setFont(textFont);
    }

    void configureDigitButton(Button button) {
        button.setFont(BUTTONS_DIGIT_FONT);
        button.setOnAction(event -> {
            String digitText = button.getText();
            if (needClearResult) {
                needClearResult = false;
                textFieldValue.clear();
            }
            textFieldValue.setText(textFieldValue.getText() + digitText);
            controllerListener.buttonDigitClicked();
        });
    }

    void addElementToMainPanel(Node element) {
        mainPanel.getChildren().add(element);
    }
}
