package calculator.view.scene;

import calculator.controller.ControllerListener;
import calculator.model.CalculatorMode;
import calculator.model.CalculatorOperation;
import calculator.model.observer.CalculatorObserver;
import calculator.view.localization.Language;
import calculator.view.localization.LanguageProperties;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static calculator.view.localization.LanguageProperties.getProperty;

public abstract class CalculatorScene extends Scene implements CalculatorObserver {

    private static final int MAIN_PANEL_PADDING_SIZE = 5;

    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int ROW_CONSTRAINS_HEIGHT = 40;
    private static final int COLUMN_CONSTRAINS_WIDTH = 65;

    private static final String FONT_FAMILY = "System";

    private static final Font TEXT_FIELD_VALUE_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 30);
    private static final Font TEXT_FIELD_PREVIOUS_OPERATION_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 14);
    private static final Font BUTTONS_FONT = Font.font(FONT_FAMILY, 15);
    private static final Font BUTTONS_DIGIT_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 15);

    private static final Duration BUTTON_CLICK_EFFECT_DURATION = Duration.seconds(0.1);

    ControllerListener controllerListener;
    private CalculatorMode calculatorMode;

    private VBox mainPanel;
    private GridPane buttonsGridPane;
    private MenuBar menuBar;
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

    }

    @Override
    public void setPreviousOperationText(String text) {

    }

    public void setControllerListener(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    private void setupMainPanel() {
        mainPanel = (VBox) this.getRoot();
    }

    private void setupMenu() {
        menuBar = new MenuBar();
        mainPanel.getChildren().add(menuBar);
        setupMenuFile();
        setupMenuEdit();
        setupModeMenu();
        setupMenuHelp();
    }

    private Menu generateLanguageMenu() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        Menu languageMenu = new Menu(getProperty("calculator_scene.menu_language"));
        for (Language language : Language.values()) {
            RadioMenuItem languageMenuItem = new RadioMenuItem(language.getLanguageName());
            languageMenuItem.setToggleGroup(languageToggleGroup);
            languageMenu.getItems().add(languageMenuItem);

            languageMenuItem.setOnAction(event -> changeLanguage(language));
        }
        return languageMenu;
    }

    private void setupMenuFile() {
        Menu menuFile = new Menu(getProperty("calculator_scene.menu_file"));
        Menu menuLanguage = generateLanguageMenu();
        MenuItem separator = new SeparatorMenuItem();
        MenuItem menuItemExit = new MenuItem(getProperty("calculator_scene.menu_exit"));
        menuFile.getItems().add(menuLanguage);
        menuFile.getItems().add(separator);
        menuFile.getItems().addAll(menuItemExit);
        menuBar.getMenus().add(menuFile);

        menuItemExit.setOnAction(event -> Platform.exit());
    }

    private void setupMenuEdit() {
        Menu menuEdit = new Menu(getProperty("calculator_scene.menu_edit"));
        MenuItem menuItemCopy = new MenuItem(getProperty("calculator_scene.menu_item_copy"));
        MenuItem menuItemPaste = new MenuItem(getProperty("calculator_scene.menu_item_paste"));
        menuEdit.getItems().addAll(menuItemCopy, menuItemPaste);
        menuBar.getMenus().add(menuEdit);
    }

    private void setupModeMenu() {
        ToggleGroup modeToggleGroup = new ToggleGroup();
        Menu menuMode = new Menu(getProperty("calculator_scene.menu_mode"));
        RadioMenuItem menuItemFraction = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_fraction"));
        RadioMenuItem menuItemComplex = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_complex"));
        RadioMenuItem menuItemPNumber = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_p-value"));
        menuMode.getItems().addAll(menuItemFraction, menuItemComplex, menuItemPNumber);
        menuBar.getMenus().add(menuMode);

        menuItemFraction.setToggleGroup(modeToggleGroup);
        menuItemComplex.setToggleGroup(modeToggleGroup);
        menuItemPNumber.setToggleGroup(modeToggleGroup);

        menuItemFraction.setOnAction(event -> changeScene(CalculatorMode.FRACTION));
        menuItemComplex.setOnAction(event -> changeScene(CalculatorMode.COMPLEX));
        menuItemPNumber.setOnAction(event -> changeScene(CalculatorMode.P_NUMBER));
    }

    private void setupMenuHelp() {
        Menu menuHelp = new Menu(getProperty("calculator_scene.menu_help"));
        MenuItem menuItemHelp = new MenuItem(getProperty("calculator_scene.menu_item_help"));
        menuHelp.getItems().addAll(menuItemHelp);
        menuBar.getMenus().add(menuHelp);
    }

    private void setupTextFields() {
        textFieldValue = new TextField();
        textFieldValue.setFont(TEXT_FIELD_VALUE_FONT);
        setupTextFieldStyle(textFieldValue);

        textFieldPreviousOperation = new TextField();
        textFieldPreviousOperation.setFont(TEXT_FIELD_PREVIOUS_OPERATION_FONT);
        setupTextFieldStyle(textFieldPreviousOperation);

        mainPanel.getChildren().addAll(textFieldPreviousOperation, textFieldValue);
    }

    private void setupTextFieldStyle(TextField textField) {
        textField.setAlignment(Pos.BASELINE_RIGHT);
        textField.setBackground(Background.EMPTY);
        textField.setStyle("-fx-display-caret: false");
        textField.setCursor(Cursor.DEFAULT);
        textField.setEditable(false);
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
        setupEnterButton();
    }

    private void setupHotKeys() {
        ObservableMap<KeyCombination, Runnable> accelerators = this.getAccelerators();
        CalculatorButtons[] buttons = CalculatorButtons.values();
        Arrays.stream(buttons)
                .filter(button -> button.getKeyCode() != null)
                .forEach(button -> {
                    KeyCombination keyCombination = new KeyCodeCombination(button.getKeyCode());
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

    private void setupClearButtons() {
        Button buttonGlobalClear = CalculatorButtons.BUTTON_GLOBAL_CLEAR.getButton();
        Button buttonClearEntry = CalculatorButtons.BUTTON_CLEAR_ENTRY.getButton();
        Button buttonBackSpace = CalculatorButtons.BUTTON_BACKSPACE.getButton();

        buttonGlobalClear.setOnAction(event -> clearTextFields());
        buttonClearEntry.setOnAction(event -> clearTextFields());

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

    private void setupEnterButton() {
        String number = textFieldValue.getText();
        Button enterButton = CalculatorButtons.BUTTON_ENTER.getButton();
        enterButton.setOnAction(event -> controllerListener.buttonEnterClicked(number, calculatorMode));
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
    }

    private void changeLanguage(Language language) {
        controllerListener.updateLanguage(language);
        Alert needRestartAlert = new Alert(Alert.AlertType.INFORMATION);
        needRestartAlert.setTitle(getProperty("calculator_scene.restart_alert_title"));
        needRestartAlert.setHeaderText(null);
        needRestartAlert.setContentText(getProperty("calculator_scene.restart_alert_message"));
        needRestartAlert.showAndWait();
    }

    private void changeScene(CalculatorMode mode) {
        if (calculatorMode != mode) {
            controllerListener.updateCalculatorMode(mode);
            switch (mode) {
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
        Stage calculatorWindow = (Stage) this.getWindow();
        calculatorScene.setControllerListener(controllerListener);
        calculatorScene.initializeScene();
        calculatorWindow.setScene(calculatorScene);
    }

    void configureDigitButton(Button button) {
        button.setFont(BUTTONS_DIGIT_FONT);
        button.setOnAction(event -> {
            String digitText = button.getText();
            textFieldValue.setText(textFieldValue.getText() + digitText);
        });
    }

    void addElementToMainPanel(Node element) {
        mainPanel.getChildren().add(element);
    }
}
