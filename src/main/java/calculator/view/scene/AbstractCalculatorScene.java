package calculator.view.scene;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static calculator.view.LanguageProperties.getProperty;

abstract class AbstractCalculatorScene extends Scene {

    private static final int GRID_PANE_ROWS = 5;
    private static final int GRID_PANE_COLUMNS = 6;

    private VBox mainPanel;

    private TextField textField;

    private List<Node> elementsInGridPane;

    AbstractCalculatorScene() {
        super(new VBox());
        mainPanel = (VBox) this.getRoot();
        setupMenu();
        setupTextField();
        setupButtons();
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

    private void setupTextField() {
        textField = new TextField();
    }

    private void setupButtons() {
        GridPane gridPane = new GridPane();
        mainPanel.getChildren().add(gridPane);
        elementsInGridPane = new ArrayList<>(GRID_PANE_ROWS * GRID_PANE_COLUMNS);
        createDigitButtons();
        createClearButtons();
        createMemoryButtons();
        createUnaryOperationButtons();
        createBinaryOperationButtons();
        createOtherButtons();
    }

    private void createDigitButtons() {

    }

    private void createClearButtons() {

    }

    private void createMemoryButtons() {

    }

    private void createUnaryOperationButtons() {

    }

    private void createBinaryOperationButtons() {

    }

    private void createOtherButtons() {

    }
}
