package calculator.view.scene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

abstract class AbstractCalculatorScene extends Scene {

    private static final int MAIN_PANEL_PADDING_SIZE = 5;

    private static final int GRID_PANE_ROWS = 5;
    private static final int GRID_PANE_COLUMNS = 6;

    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 50;
    private static final int ROW_CONSTRAINS_HEIGHT = 60;
    private static final int COLUMN_CONSTRAINS_WIDTH = 90;

    private static final String FONT_FAMILY = "System";

    private static final Font TEXT_FIELD_VALUE_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 30);
    private static final Font TEXT_FIELD_PREVIOUS_OPERATION_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 14);
    private static final Font BUTTONS_FONT = Font.font(FONT_FAMILY, 20);
    private static final Font DIGIT_BUTTONS_FONT = Font.font(FONT_FAMILY, FontWeight.BOLD, 24);

    private VBox mainPanel;
    private GridPane buttonsGridPane;
    private TextField textFieldValue;
    private TextField textFieldPreviousOperation;

    AbstractCalculatorScene() {
        super(new VBox());
        setupMainPanel();
        setupMenu();
        setupTextFields();
        setupButtonsGridPane();
        setupButtons();
    }

    private void setupMainPanel() {
        mainPanel = (VBox) this.getRoot();
    }

    private void setupMenu() {
        MenuItem menuItemCopy = new MenuItem(getProperty("abstract_calculator_scene.menu_item_copy"));
        MenuItem menuItemPaste = new MenuItem(getProperty("abstract_calculator_scene.menu_item_paste"));
        MenuItem menuItemHelp = new MenuItem(getProperty("abstract_calculator_scene.menu_item_help"));

        MenuItem menuItemFraction = new RadioMenuItem(getProperty("abstract_calculator_scene.menu_item_mode_fraction"));
        MenuItem menuItemComplex = new RadioMenuItem(getProperty("abstract_calculator_scene.menu_item_mode_complex"));
        MenuItem menuItemPNumber = new RadioMenuItem(getProperty("abstract_calculator_scene.menu_item_mode_p-number"));

        Menu menuFile = new Menu(getProperty("abstract_calculator_scene.menu_file"));
        Menu menuEdit = new Menu(getProperty("abstract_calculator_scene.menu_edit"));
        Menu menuMode = new Menu(getProperty("abstract_calculator_scene.menu_mode"));
        Menu menuHelp = new Menu(getProperty("abstract_calculator_scene.menu_help"));

        menuEdit.getItems().addAll(menuItemCopy, menuItemPaste);
        menuMode.getItems().addAll(menuItemFraction, menuItemComplex, menuItemPNumber);
        menuHelp.getItems().addAll(menuItemHelp);

        MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuMode, menuHelp);
        mainPanel.getChildren().add(menuBar);
    }

    private void setupTextFields() {
        textFieldValue = new TextField();
        textFieldValue.setFont(TEXT_FIELD_VALUE_FONT);
        textFieldValue.setAlignment(Pos.BASELINE_RIGHT);
        textFieldValue.setBackground(Background.EMPTY);
        textFieldValue.setText("555");
        textFieldValue.setStyle("-fx-display-caret: false");
        textFieldValue.setCursor(Cursor.DEFAULT);

        textFieldPreviousOperation = new TextField();
        textFieldPreviousOperation.setFont(TEXT_FIELD_PREVIOUS_OPERATION_FONT);
        textFieldPreviousOperation.setAlignment(Pos.BASELINE_RIGHT);
        textFieldPreviousOperation.setBackground(Background.EMPTY);
        textFieldPreviousOperation.setText("500+55 =");
        textFieldPreviousOperation.setStyle("-fx-display-caret: false");
        textFieldPreviousOperation.setCursor(Cursor.DEFAULT);

        mainPanel.getChildren().addAll(textFieldPreviousOperation, textFieldValue);
    }

    private void setupButtonsGridPane() {
        buttonsGridPane = new GridPane();
        addRowAndColumnConstraintsToButtonsGridPane(GRID_PANE_ROWS, GRID_PANE_COLUMNS);
        buttonsGridPane.setAlignment(Pos.CENTER);
        buttonsGridPane.setGridLinesVisible(true);
        buttonsGridPane.setPadding(new Insets(0,
                MAIN_PANEL_PADDING_SIZE, MAIN_PANEL_PADDING_SIZE, MAIN_PANEL_PADDING_SIZE));
        mainPanel.getChildren().add(buttonsGridPane);
    }

    private void setupButtons() {
        AbstractCalculatorSceneButtons[] buttons = AbstractCalculatorSceneButtons.values();
        Arrays.stream(buttons).forEach(button -> configureButton(button.getButton()));
        List<AbstractCalculatorSceneButtons> digitButtons = AbstractCalculatorSceneButtons.getDigitButtons();
        digitButtons.forEach(button -> configureDigitButton(button.getButton()));
        addButtonsToGridPane();
    }


    private void addButtonsToGridPane() {
        Node[][] elementsInGridPane = new Node[GRID_PANE_ROWS][GRID_PANE_COLUMNS];

        AbstractCalculatorSceneButtons[] buttons = AbstractCalculatorSceneButtons.values();
        Arrays.stream(buttons).forEach(button -> {
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

    void addRowToGridPane(Node... elements) {
        addRowAndColumnConstraintsToButtonsGridPane(1, 0);
        buttonsGridPane.addRow(GRID_PANE_ROWS, elements);
    }

    void configureButton(Button button) {
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setFont(BUTTONS_FONT);
    }

    void configureDigitButton(Button button) {
        configureButton(button);
        button.setFont(DIGIT_BUTTONS_FONT);
    }
}
