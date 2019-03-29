package calculator.model;

public enum CalculatorMode {

    BASIC("0", 5, 6),
    FRACTION("0/1", 5, 6),
    COMPLEX("0+0i",6, 6),
    P_NUMBER("0", 6, 6);

    private String startValue;
    private int countButtonsGridPaneRows;
    private int countButtonsGridPaneColumns;

    CalculatorMode(String startValue, int countButtonsGridPaneRows, int countButtonsGridPaneColumns) {
        this.startValue = startValue;
        this.countButtonsGridPaneRows = countButtonsGridPaneRows;
        this.countButtonsGridPaneColumns = countButtonsGridPaneColumns;
    }

    public String getStartValue() {
        return startValue;
    }

    public int getCountButtonsGridPaneRows() {
        return countButtonsGridPaneRows;
    }

    public int getCountButtonsGridPaneColumns() {
        return countButtonsGridPaneColumns;
    }
}
