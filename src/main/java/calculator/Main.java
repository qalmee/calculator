package calculator;

import calculator.controller.Controller;
import calculator.model.CalculatorModel;
import calculator.view.scene.CalculatorScene;
import calculator.view.scene.PNumberCalculatorScene;
import calculator.view.window.CalculatorWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage calculatorWindow = new CalculatorWindow();
        CalculatorModel calculatorModel = new CalculatorModel();
        Controller controller = new Controller(calculatorModel);

        CalculatorScene calculatorScene = new PNumberCalculatorScene();
        calculatorScene.setControllerListener(controller);

        calculatorModel.setCalculatorObserver(calculatorScene);

        calculatorScene.initializeScene();

        calculatorWindow.setScene(calculatorScene);
        calculatorWindow.show();
    }
}
