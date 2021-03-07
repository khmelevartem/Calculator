package android1.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class  MainActivity extends AppCompatActivity {

    private final HashMap<View, Integer> mNumButtons = new HashMap<>();
    public enum Operation {
        PLUS ("+"),
        MINUS ("-"),
        DIVIDE ("/"),
        MULTIPLY ("*");
        private final String symbol;
        Operation(String symbol){
            this.symbol = symbol;
        };
        public String getSymbol() {
            return symbol;
        }
    }
    private static final String TEMPORARY = "temp";
    private TextView mText;
    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); //уже стоит по умолчанию
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = new SingleOperationController(this);
        mText = findViewById(R.id.result);
        try {
            mText.setText(savedInstanceState.get(TEMPORARY).toString());
        } catch (NullPointerException ignored){}
        setUpButtons();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEMPORARY, mText.getText().toString());
    }

    private void setUpButtons() {
        int i = 0;
        mNumButtons.put((View) findViewById(R.id.button0), i++);
        mNumButtons.put((View) findViewById(R.id.button1), i++);
        mNumButtons.put((View) findViewById(R.id.button2), i++);
        mNumButtons.put((View) findViewById(R.id.button3), i++);
        mNumButtons.put((View) findViewById(R.id.button4), i++);
        mNumButtons.put((View) findViewById(R.id.button5), i++);
        mNumButtons.put((View) findViewById(R.id.button6), i++);
        mNumButtons.put((View) findViewById(R.id.button7), i++);
        mNumButtons.put((View) findViewById(R.id.button8), i++);
        mNumButtons.put((View) findViewById(R.id.button9), i);
        for (View button : mNumButtons.keySet()) {
            button.setOnClickListener(view -> mController.number(mNumButtons.get(view)));
        }

        findViewById(R.id.buttonDot)  .setOnClickListener(v -> mController.dot());
        findViewById(R.id.buttonPlus) .setOnClickListener(v -> mController.arithmetic(Operation.PLUS));
        findViewById(R.id.buttonMinus).setOnClickListener(v -> mController.arithmetic(Operation.MINUS));
        findViewById(R.id.buttonDiv)  .setOnClickListener(v -> mController.arithmetic(Operation.DIVIDE));
        findViewById(R.id.buttonMult) .setOnClickListener(v -> mController.arithmetic(Operation.MULTIPLY));
        findViewById(R.id.buttonEqual).setOnClickListener(v -> mController.equal());
        findViewById(R.id.buttonDel)  .setOnClickListener(v -> mController.delete());
        findViewById(R.id.buttonClean).setOnClickListener(v -> mController.clean());
        findViewById(R.id.buttonPM)   .setOnClickListener(v -> mController.alternate());
        findViewById(R.id.buttonPerc) .setOnClickListener(v -> mController.percent());

    }

    void appendText(String symbols){
        mText.append(symbols);
    }

    void setText(String text){
        mText.setText(text);
    }

    boolean reduceText() {
        try{
            mText.setText(mText.getText().subSequence(0, mText.getText().length()-1));
            return true;
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }


}