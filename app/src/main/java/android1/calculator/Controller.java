package android1.calculator;

import static android1.calculator.MainActivity.Operation;

public interface Controller {
    void number(int num);
    void dot();
    void arithmetic(Operation operation);
    void equal();
    void delete();
    void clean();
    void alternate();
    void percent();
}
