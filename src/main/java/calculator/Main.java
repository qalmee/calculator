package calculator;

import calculator.controller.Controller;
import calculator.model.CalculatorModel;
import calculator.view.scene.CalculatorScene;
import calculator.view.window.CalculatorWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CalculatorModel calculatorModel = new CalculatorModel();
        Controller controller = new Controller(calculatorModel);

        CalculatorScene calculatorScene = new CalculatorScene();
        calculatorScene.setControllerListener(controller);

        calculatorModel.setCalculatorObserver(calculatorScene);
        calculatorModel.readLanguageFromConfig();

        calculatorScene.initializeScene();

        Stage calculatorWindow = new CalculatorWindow();
        calculatorWindow.setScene(calculatorScene);
        calculatorModel.readConfigInformation();

        calculatorWindow.show();
    }
}