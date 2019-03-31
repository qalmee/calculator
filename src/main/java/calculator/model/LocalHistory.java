package calculator.model;

import java.util.NoSuchElementException;

public class LocalHistory {

    public static LocalHistory INSTANCE = new LocalHistory();

    private String history;
    private LocalHistory(){
        history = "";
    }

    public void reset(){
        history = "";
    }

    public String getHistory(){
        return history;
    }

    public String add(Number number, CalculatorOperation operation){
        if (operation.isUnary()){
            history += operation.getMathSign() + "(" + number.toString() + ") ";
        }
        else{
            history += number.toString() + " " + operation.getMathSign() + " ";
        }
        return history;
    }

    public String changeLastOperation(CalculatorOperation operation){
        if (operation.isUnary()){
            throw new IllegalArgumentException("Attempt to change binary to unary operation");
        }
        if (history.isEmpty()){
            throw new NoSuchElementException("History string is empty");
        }
        String mathSign = history.substring(history.length() - 2).trim();
        for (CalculatorOperation t : CalculatorOperation.values()){
            if (mathSign.equals(t.getMathSign())){
                history = history.substring(history.length() - 2) + operation.getMathSign() + " ";
                return history;
            }
        }
        throw new NoSuchElementException("At the end of history string no operation signs was found");
    }

}
