package calculator.view.scene.components;

import calculator.controller.ControllerListener;
import calculator.model.CalculatorMode;
import calculator.view.localization.Language;
import javafx.application.Platform;
import javafx.scene.control.*;

import static calculator.view.localization.LanguageProperties.getProperty;

public class CalculatorMenu extends MenuBar {

    private ControllerListener controllerListener;

    public CalculatorMenu(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
        setupMenu();
    }

    private void setupMenu() {
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
        this.getMenus().add(menuFile);

        menuItemExit.setOnAction(event -> Platform.exit());
    }

    private void setupMenuEdit() {
        Menu menuEdit = new Menu(getProperty("calculator_scene.menu_edit"));
        MenuItem menuItemCopy = new MenuItem(getProperty("calculator_scene.menu_item_copy"));
        MenuItem menuItemPaste = new MenuItem(getProperty("calculator_scene.menu_item_paste"));
        menuEdit.getItems().addAll(menuItemCopy, menuItemPaste);
        this.getMenus().add(menuEdit);
    }

    private void setupModeMenu() {
        ToggleGroup modeToggleGroup = new ToggleGroup();
        Menu menuMode = new Menu(getProperty("calculator_scene.menu_mode"));
        RadioMenuItem menuItemFraction = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_fraction"));
        RadioMenuItem menuItemComplex = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_complex"));
        RadioMenuItem menuItemPNumber = new RadioMenuItem(getProperty("calculator_scene.menu_item_mode_p-value"));
        menuMode.getItems().addAll(menuItemFraction, menuItemComplex, menuItemPNumber);
        this.getMenus().add(menuMode);

        menuItemFraction.setToggleGroup(modeToggleGroup);
        menuItemComplex.setToggleGroup(modeToggleGroup);
        menuItemPNumber.setToggleGroup(modeToggleGroup);

        menuItemFraction.setOnAction(event -> controllerListener.updateCalculatorMode(CalculatorMode.FRACTION));
        menuItemComplex.setOnAction(event -> controllerListener.updateCalculatorMode(CalculatorMode.COMPLEX));
        menuItemPNumber.setOnAction(event -> controllerListener.updateCalculatorMode(CalculatorMode.P_NUMBER));
    }

    private void setupMenuHelp() {
        Menu menuHelp = new Menu(getProperty("calculator_scene.menu_help"));
        MenuItem menuItemHelp = new MenuItem(getProperty("calculator_scene.menu_item_help"));
        menuHelp.getItems().addAll(menuItemHelp);
        this.getMenus().add(menuHelp);
    }

    private void changeLanguage(Language language) {
        controllerListener.updateLanguage(language);
        Alert needRestartAlert = new Alert(Alert.AlertType.INFORMATION);
        needRestartAlert.setTitle(getProperty("calculator_scene.restart_alert_title"));
        needRestartAlert.setHeaderText(null);
        needRestartAlert.setContentText(getProperty("calculator_scene.restart_alert_message"));
        needRestartAlert.showAndWait();
    }
}
