package calculator;

import calculator.controller.Controller;
import calculator.model.CalculatorModel;
import calculator.view.scene.CalculatorScene;
import calculator.view.window.CalculatorWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
//        Fraction a = new Fraction(BigInteger.TEN, BigInteger.ONE);
//        a = a.multiply(a).multiply(a);
//        a = a.multiply(a).multiply(a);
//        Fraction b = a;
//        String sc = NumberConverter.toScientific(b.toString(), 3, CalculatorMode.FRACTION);
//        String fromSc = NumberConverter.fromScientific(sc, CalculatorMode.FRACTION);
//        System.out.println(b.toString());
//        System.out.println(sc);
//        System.out.println(fromSc);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CalculatorModel calculatorModel = new CalculatorModel();
        Controller controller = new Controller(calculatorModel);

        CalculatorScene calculatorScene = new CalculatorScene();
        calculatorScene.initializeScene();

        calculatorScene.setControllerListener(controller);

        calculatorModel.setCalculatorObserver(calculatorScene);
        calculatorModel.readLanguageFromConfig();


        Stage calculatorWindow = new CalculatorWindow();
        calculatorWindow.setScene(calculatorScene);
        calculatorModel.readConfigInformation();

        calculatorWindow.show();
    }
}
