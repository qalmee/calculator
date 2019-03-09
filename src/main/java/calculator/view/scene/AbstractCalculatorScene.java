package calculator.view.scene;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractCalculatorScene extends Scene {

    private static final int GRID_PANE_ROWS = 5;
    private static final int GRID_PANE_COLUMNS = 6;

    private VBox mainPanel;

    private TextField textField;

    private List<Node> elementsInGridPane;

    AbstractCalculatorScene() {
        super(new VBox());
        mainPanel = (VBox) this.getRoot();
        setupTextField();
        setupButtons();
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
