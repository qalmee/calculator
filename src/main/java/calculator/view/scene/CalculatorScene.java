package calculator.view.scene;

import calculator.controller.ControllerListener;
import calculator.model.memory.MemoryOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.model.stats.CalculatorMode;
import calculator.model.stats.CalculatorOperation;
import calculator.model.stats.ErrorState;
import calculator.view.localization.Language;
import calculator.view.localization.LanguageProperties;
import calculator.view.scene.components.CalculatorButtons;
import calculator.view.scene.components.CalculatorMenu;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
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

import static calculator.view.localization.LanguageProperties.getProperty;

public class CalculatorScene extends Scene implements CalculatorObserver {

    private static final int MAIN_PANEL_PADDING_SIZE = 5;

    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int ROW_CONSTRAINS_HEIGHT = 40;
    private static final int COLUMN_CONSTRAINS_WIDTH = 65;
    private static final int TEXT_FIELD_WIDTH = 415;
    private static final int TEXT_FIELD_HEIGHT = 65;
    private static final int TEXT_FIELD_PREVIOUS_OPERATION_MAX_TEXT_WIDTH_PIXELS = 360;
    private static final int TEXT_FIELD_PREVIOUS_OPERATION_MAX_TEXT_LENGTH = 40;
    private static final int TEXT_FIELD_VALUE_MAX_TEXT_WIDTH_PIXELS = 380;
    private static final int TEXT_FIELD_VALUE_MAX_TEXT_LENGTH = 40;
    private static final int SCROLL_BUTTONS_SCROLL_SIZE = 10;

    private static final String FONT_FAMILY = "System";

    private static final Font TEXT_FIELD_VALUE_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 30);
    private static final Font TEXT_FIELD_PREVIOUS_OPERATION_FONT = Font.font("Consolas", FontWeight.BOLD, 14);
    private static final Font BUTTONS_FONT = Font.font(FONT_FAMILY, 15);
    private static final Font BUTTONS_DIGIT_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 15);

    private static final Duration BUTTON_CLICK_EFFECT_DURATION = Duration.seconds(0.1);
    ControllerListener controllerListener;
    private CalculatorMode calculatorMode;

    private boolean needClearResult;
    private boolean backSpaceEnabled;
    private boolean isErrorState;
    private String textFieldPreviousOperationText;
    private int textFieldPreviousOperationPosition;

    private Clipboard clipboard;
    private VBox mainPanel;
    private GridPane buttonsGridPane;
    private TextField textFieldValue;
    private TextField textFieldPreviousOperation;
    private Button buttonScrollLeft;
    private Button buttonScrollRight;

    public CalculatorScene() {
        this(CalculatorMode.BASIC);
    }

    CalculatorScene(CalculatorMode calculatorMode) {
        super(new VBox());
        this.calculatorMode = calculatorMode;
        backSpaceEnabled = true;
    }

    public void initializeScene() {
        setupMainPanel();
        setupMenu();
        setupScrollButtons();
        setupTextFieldPreviousOperation();
        setupTextFieldValue();
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
    public void setBackSpaceEnabled(boolean value) {
        backSpaceEnabled = value;
    }

    @Override
    public void setResult(String result) {
        textFieldValue.setText(result);
    }

    @Override
    public void setPreviousOperationText(String text) {
        textFieldPreviousOperationText = text;
        textFieldPreviousOperationPosition = text.length();
        textFieldPreviousOperation.setText(text);
        buttonScrollLeft.setDisable(false);
        buttonScrollRight.setDisable(true);
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
        clearTextFieldValueIfError();
        String value = clipboard.getString();
        textFieldValue.setText(value);
        controllerListener.checkPastedValue(value, calculatorMode);
    }

    @Override
    public void setErrorState(ErrorState errorState) {
        setAtErrorState(errorState);
    }

    @Override
    public void disableMemoryButtons(boolean value) {
        Button buttonMemoryClear = CalculatorButtons.BUTTON_MEMORY_CLEAR.getButton();
        Button buttonMemoryRead = CalculatorButtons.BUTTON_MEMORY_READ.getButton();
        buttonMemoryClear.setDisable(value);
        buttonMemoryRead.setDisable(value);
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

    private void setupTextFieldPreviousOperation() {
        textFieldPreviousOperation = new TextField();
        textFieldPreviousOperation.setFont(TEXT_FIELD_PREVIOUS_OPERATION_FONT);
        textFieldPreviousOperation.setPrefWidth(TEXT_FIELD_PREVIOUS_OPERATION_MAX_TEXT_WIDTH_PIXELS);
        textFieldPreviousOperation.textProperty().addListener((observable, oldValue, newValue) ->
                configureTextIntTextFieldPreviousOperation(newValue));
        setupTextFieldStyle(textFieldPreviousOperation);

        HBox hBox = new HBox(buttonScrollLeft, textFieldPreviousOperation, buttonScrollRight);
        mainPanel.getChildren().add(hBox);
    }

    private void setupTextFieldValue() {
        textFieldValue = new TextField();
        textFieldValue.setFont(TEXT_FIELD_VALUE_FONT);
        textFieldValue.setPrefSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        textFieldValue.textProperty().addListener((observable, oldValue, newValue) ->
                configureTextInTextFieldValue(newValue));
        setupTextFieldStyle(textFieldValue);
        mainPanel.getChildren().add(textFieldValue);
    }

    private void setupTextFieldStyle(TextField textField) {
        textField.setAlignment(Pos.TOP_RIGHT);
        textField.setBackground(Background.EMPTY);
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

                    if (button.equals(CalculatorButtons.BUTTON_MULTIPLY)) {
                        accelerators.put(new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.SHIFT_DOWN), runnable);
                    }
                    if (button.equals(CalculatorButtons.BUTTON_PLUS)) {
                        accelerators.put(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHIFT_DOWN), runnable);
                    }
                    if (button.equals(CalculatorButtons.BUTTON_MINUS)) {
                        accelerators.put(new KeyCodeCombination(KeyCode.MINUS), runnable);
                    }
                    if (button.equals(CalculatorButtons.BUTTON_DIVIDE)) {
                        accelerators.put(new KeyCodeCombination(KeyCode.SLASH), runnable);
                    }
                });
        addHotKeysToNumericKeyboardDigits();
    }

    private void addHotKeysToNumericKeyboardDigits() {
        ObservableMap<KeyCombination, Runnable> accelerators = this.getAccelerators();
        CalculatorButtons.getDigitButtons().forEach(button -> {
            KeyCombination keyCombination = new KeyCodeCombination(
                    KeyCode.valueOf("NUMPAD" + button.getButton().getText()));
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

    private void setupScrollButtons() {
        buttonScrollLeft = new Button(getProperty("calculator_scene.button_scroll_left"));
        buttonScrollRight = new Button(getProperty("calculator_scene.button_scroll_right"));
        buttonScrollLeft.setFocusTraversable(false);
        buttonScrollRight.setFocusTraversable(false);
        setVisibleScrollButtons(false);
        final int maxLength = TEXT_FIELD_PREVIOUS_OPERATION_MAX_TEXT_LENGTH;
        final int scrollSize = SCROLL_BUTTONS_SCROLL_SIZE;

        buttonScrollLeft.setOnAction(event -> {
            int startPos = textFieldPreviousOperationPosition - maxLength - scrollSize;
            int endPos = startPos + maxLength;
            if (startPos > 0) {
                textFieldPreviousOperation.setText(textFieldPreviousOperationText.substring(startPos, endPos));
                textFieldPreviousOperationPosition -= scrollSize;
            } else {
                textFieldPreviousOperation.setText(textFieldPreviousOperationText.substring(0, maxLength));
                textFieldPreviousOperationPosition = maxLength;
                buttonScrollLeft.setDisable(true);
            }
            buttonScrollRight.setDisable(false);
        });

        buttonScrollRight.setOnAction(event -> {
            int startPos = textFieldPreviousOperationPosition - maxLength + scrollSize;
            int endPos = startPos + maxLength;
            int textLength = textFieldPreviousOperationText.length();
            if (endPos < textFieldPreviousOperationText.length()) {
                textFieldPreviousOperation.setText(textFieldPreviousOperationText.substring(startPos, endPos));
                textFieldPreviousOperationPosition += scrollSize;
            } else {
                textFieldPreviousOperation.setText(textFieldPreviousOperationText.substring(textLength - maxLength));
                textFieldPreviousOperationPosition = textLength;
                buttonScrollRight.setDisable(true);
            }
            buttonScrollLeft.setDisable(false);
        });
    }

    private void setupClearButtons() {
        Button buttonGlobalClear = CalculatorButtons.BUTTON_GLOBAL_CLEAR.getButton();
        Button buttonClearEntry = CalculatorButtons.BUTTON_CLEAR_ENTRY.getButton();
        Button buttonBackSpace = CalculatorButtons.BUTTON_BACKSPACE.getButton();

        buttonGlobalClear.setOnAction(event -> {
            clearTextFieldValueIfError();
            clearTextFields();
            controllerListener.buttonGlobalClearClicked();
        });

        buttonClearEntry.setOnAction(event -> {
            clearTextFieldValueIfError();
            clearTextFields();
            controllerListener.buttonClearEntryClicked();
        });

        buttonBackSpace.setOnAction(event -> {
            clearTextFieldValueIfError();
            if (backSpaceEnabled) {
                backspaceClickedAction();
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
        addTooltipsToMemoryButtons();
    }

    private void addTooltipsToMemoryButtons() {
        Button buttonMemoryAdd = CalculatorButtons.BUTTON_MEMORY_ADD.getButton();
        Button buttonMemoryClear = CalculatorButtons.BUTTON_MEMORY_CLEAR.getButton();
        Button buttonMemoryRead = CalculatorButtons.BUTTON_MEMORY_READ.getButton();
        Button buttonMemorySave = CalculatorButtons.BUTTON_MEMORY_SAVE.getButton();

        buttonMemoryAdd.setTooltip(new Tooltip(getProperty("calculator_scene.tooltip_button_memory_add")));
        buttonMemoryClear.setTooltip(new Tooltip(getProperty("calculator_scene.tooltip_button_memory_clear")));
        buttonMemoryRead.setTooltip(new Tooltip(getProperty("calculator_scene.tooltip_button_memory_read")));
        buttonMemorySave.setTooltip(new Tooltip(getProperty("calculator_scene.tooltip_button_memory_save")));
    }

    private void setupEnterButton() {
        Button enterButton = CalculatorButtons.BUTTON_ENTER.getButton();
        enterButton.setOnAction(event -> {
            clearTextFieldValueIfError();
            String number = textFieldValue.getText();
            controllerListener.buttonEnterClicked(number, calculatorMode);
        });
    }

    private void setupDotButton() {
        Button dotButton = CalculatorButtons.BUTTON_COMMA.getButton();
        String commaSymbol = dotButton.getText();
        dotButton.setOnAction(event -> {
            if (!textFieldValue.getText().contains(commaSymbol)) {
                textFieldValue.appendText(dotButton.getText());
            }
        });
    }

    private void setButtonsStateDueToAnError(boolean value) {
        List<CalculatorButtons> notDisabledButtons = new ArrayList<>();
        notDisabledButtons.addAll(CalculatorButtons.getDigitButtons());
        notDisabledButtons.addAll(CalculatorButtons.getAllClearButtons());
        notDisabledButtons.add(CalculatorButtons.BUTTON_ENTER);

        ObservableList<Node> elementToGridPane = buttonsGridPane.getChildren();
        for (Node element : elementToGridPane) {
            if (element instanceof Button) {
                boolean setNewState = notDisabledButtons.stream()
                        .map(CalculatorButtons::getButton)
                        .noneMatch(button -> button.equals(element));
                if (setNewState) {
                    element.setDisable(value);
                }
            }
        }
    }

    private void clearTextFields() {
        needClearResult = false;
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

    private void setStartValue() {
        String startValue = calculatorMode.getStartValue();
        textFieldValue.setText(startValue);
    }

    private void clearTextFieldValueIfError() {
        if (isErrorState) {
            setToNormalState();
        }
    }

    private void configureTextIntTextFieldPreviousOperation(String text) {
        int maxLength = TEXT_FIELD_PREVIOUS_OPERATION_MAX_TEXT_LENGTH;
        if (text.length() - maxLength > 0) {
            textFieldPreviousOperation.setText(text.substring(text.length() - maxLength));
        }
        setVisibleScrollButtons(text.length() >= maxLength);
    }

    private void configureTextInTextFieldValue(String text) {
        if (text.length() >= TEXT_FIELD_VALUE_MAX_TEXT_LENGTH) {
            textFieldValue.setText(text.substring(0, text.length() - 1));
        }
        if (text.isEmpty()) {
            textFieldValue.setText(calculatorMode.getStartValue());
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

    private void setVisibleScrollButtons(boolean value) {
        buttonScrollLeft.setVisible(value);
        buttonScrollRight.setVisible(value);
    }

    void setupAndSetNewScene(CalculatorScene calculatorScene) {
        Window calculatorWindow = this.getWindow();
        calculatorScene.setControllerListener(controllerListener);
        calculatorScene.initializeScene();
        ((Stage) calculatorWindow).setScene(calculatorScene);
        controllerListener.setNewObserver(calculatorScene);
    }

    void configureDigitButton(Button button) {
        button.setFont(BUTTONS_DIGIT_FONT);
        button.setOnAction(event -> {
            clearTextFieldValueIfError();
            String digitText = button.getText();
            if (needClearResult) {
                needClearResult = false;
                textFieldValue.clear();
            }
            appendDigitToTextFieldValue(digitText);
            controllerListener.buttonDigitClicked();
        });
    }

    void appendDigitToTextFieldValue(String digitText) {
        String text = textFieldValue.getText();
        if (text.equals(calculatorMode.getStartValue())) {
            if (digitText.equals(text)) {
                return;
            }
            if (!digitText.contains(calculatorMode.getStartValue())) {
                textFieldValue.setText(digitText);
            }
        } else {
            textFieldValue.setText(text + digitText);
        }
    }

    void backspaceClickedAction() {
        String textInTextField = textFieldValue.getText();
        textFieldValue.setText(textInTextField.substring(0, textInTextField.length() - 1));
    }

    void textFieldValueSetText(String text) {
        textFieldValue.setText(text);
    }

    void addElementToMainPanel(Node element) {
        mainPanel.getChildren().add(element);
    }

    void setAtErrorState(ErrorState errorState) {
        textFieldValue.setText(errorState.getErrorStateText());
        isErrorState = true;
        setButtonsStateDueToAnError(true);
    }

    void setToNormalState() {
        isErrorState = false;
        clearTextFields();
        setButtonsStateDueToAnError(false);
    }

    String getValueFromTextFieldValue() {
        return textFieldValue.getText();
    }
}
