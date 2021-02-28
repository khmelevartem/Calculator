package android1.calculator;

import java.util.LinkedList;
import java.util.Queue;

import static android1.calculator.MainActivity.Operation;

public class SingleOperationController implements Controller {

    private final MainActivity gui;
    private final Queue<Float> numbers = new LinkedList<>();
    private final String DOT = ".";
    private StringBuilder temp = new StringBuilder("");
    private String lastSymbol;
    private Operation mOperation;

    public SingleOperationController(MainActivity mainActivity) {
        gui = mainActivity;
    }

    @Override
    public void number(int num){
        lastSymbol = String.valueOf(num);
        temp.append(num);
        gui.appendText(String.valueOf(num));
    }

    @Override
    public void dot(){
        lastSymbol = DOT;
        if (temp.length()==0){
            temp.append(0 + DOT);
            gui.appendText(0 + DOT);
        } else {
            temp.append(DOT);
            gui.appendText(DOT);
        }
    }

    @Override
    public void arithmetic(Operation operation) {
        if (temp.length()==0) temp.append("0");

        if (isOperation(lastSymbol)) gui.reduceText();
        else {
            if (mOperation != null) equal();
            numbers.offer(Float.parseFloat(temp.toString()));
            temp.delete(0, temp.length());
        }

        gui.appendText(operation.getSymbol());
        lastSymbol = operation.getSymbol();
        mOperation = operation;
    }

    @Override
    public void equal() {
        float result = 0;
        if (mOperation == null) {
            return;
        } else if (isOperation(lastSymbol)) {
            throw new RuntimeException("Invalid syntax");
            // позже здесь будет диалоговое окошко
        } else {
            numbers.offer(Float.parseFloat(temp.toString()));
            try {
                switch (mOperation) {
                    case PLUS:
                        result = numbers.poll() + numbers.poll();
                        break;
                    case MINUS:
                        result = numbers.poll() - numbers.poll();
                        break;
                    case DIVIDE:
                        result = numbers.poll();
                        if (numbers.peek() == 0) {
                            throw new RuntimeException("Division by zero");
                            // позже здесь будет диалоговое окошко
                        } else result /= numbers.poll();
                        break;
                    case MULTIPLY:
                        result = numbers.poll() * numbers.poll();
                        break;
                }
            } catch (NullPointerException e) {
                throw new RuntimeException("Unexpected operand behavior");
            }
        }
        gui.setText(String.valueOf(result));
        temp.delete(0, temp.length());
        temp.append(result);
        mOperation = null;
    }

    @Override
    public void delete() {
        if(gui.reduceText()) {
            if (temp.length()>0) temp.deleteCharAt(temp.length() - 1);
            else {
                mOperation = null;
                temp.append(numbers.poll());
                gui.setText(String.valueOf(temp));
            }
        }
    }

    @Override
    public void clean() {
        temp.delete(0, temp.length());
        gui.setText("");
        mOperation = null;
        numbers.clear();
    }

    @Override
    public void alternate() {
        if (temp.length()==0) return;
        else if (temp.charAt(0)!='-'){
            for (int i = 0; i < temp.length(); i++) {
                gui.reduceText();
            }
            temp.insert(0, '-');
        } else if (temp.charAt(0)=='-'){
            for (int i = 0; i < temp.length(); i++) {
                gui.reduceText();
            }
            temp.delete(0, 1);
        }
        gui.appendText(String.valueOf(temp));

    }

    @Override
    public void percent() {
        //в разработке
    }

    private boolean isOperation(String symbol) {
        return symbol.equals(Operation.DIVIDE.getSymbol()) ||
                symbol.equals(Operation.PLUS.getSymbol()) ||
                symbol.equals(Operation.MULTIPLY.getSymbol()) ||
                symbol.equals(Operation.MINUS.getSymbol()
                );
    }
}
