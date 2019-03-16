package calculator.view.scene;

public enum CalculatorMode {

    BASIC(5, 6),
    FRACTION(5, 6),
    COMPLEX(6, 6),
    P_NUMBER(6, 6);

    private int countButtonsGridPaneRows;
    private int countButtonsGridPaneColumns;

    CalculatorMode(int countButtonsGridPaneRows, int countButtonsGridPaneColumns) {
        this.countButtonsGridPaneRows = countButtonsGridPaneRows;
        this.countButtonsGridPaneColumns = countButtonsGridPaneColumns;
    }

    public int getCountButtonsGridPaneRows() {
        return countButtonsGridPaneRows;
    }

    public int getCountButtonsGridPaneColumns() {
        return countButtonsGridPaneColumns;
    }
}
