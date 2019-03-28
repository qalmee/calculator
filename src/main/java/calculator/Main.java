package calculator;

import calculator.controller.Controller;
import calculator.model.CalculatorModel;
import calculator.model.Utils.MathUtils;
import calculator.model.numbers.Complex;
import calculator.view.scene.CalculatorScene;
import calculator.view.scene.PNumberCalculatorScene;
import calculator.view.window.CalculatorWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class Main extends Application {

    public static void main(String[] args) {

        System.out.println(MathUtils.taylorATan(BigDecimal.ONE));
        Complex c = new Complex(BigDecimal.ONE, BigDecimal.ONE);
        System.out.println(c.pow(3));
        System.out.println(c.pow(4));
        System.out.println(c.pow(5));
        System.out.println(c.pow(6));


        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CalculatorModel calculatorModel = new CalculatorModel();
        Controller controller = new Controller(calculatorModel);

        CalculatorScene calculatorScene = new PNumberCalculatorScene();
        calculatorScene.setControllerListener(controller);

        calculatorModel.setCalculatorObserver(calculatorScene);
        calculatorModel.readLanguageFromConfig();

        calculatorScene.initializeScene();

        Stage calculatorWindow = new CalculatorWindow();
        CalculatorScene.setWindow(calculatorWindow);

        calculatorWindow.setScene(calculatorScene);
        calculatorModel.readConfigInformation();

        calculatorWindow.show();
    }
}
