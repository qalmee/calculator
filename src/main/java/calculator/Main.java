package calculator;

import calculator.view.scene.FractionCalculatorScene;
import calculator.view.window.CalculatorWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage calculatorWindow;

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        calculatorWindow = new CalculatorWindow();

        setupCalculatorWindow();
    }

    private void setupCalculatorWindow() {
        Scene calculatorScene = new FractionCalculatorScene();
        calculatorWindow.setScene(calculatorScene);
        calculatorWindow.show();
    }
}
