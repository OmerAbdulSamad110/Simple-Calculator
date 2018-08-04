package com.example.omer.myapplication;

import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
    }

    private void UpdateScreen() {
        screen.setText(display);
    }

    public void onClickNumber(View v) {
        if (result != "") {
            Clear();
            UpdateScreen();
        }
        Button btn = (Button) v;
        display += btn.getText();
        UpdateScreen();
    }

    private boolean isOperator(char op) {
        switch (op) {
            case '+':
                return true;
            case '-':
                return true;
            case '*':
                return true;
            case '/':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View v) {
        Button btn = (Button) v;
        if (display == "") {
            return;
        }
        if (currentOperator != "") {
            if (isOperator(display.charAt(display.length() - 1))) {
                display.replace(display.charAt(display.length() - 1), btn.getText().charAt(0));
                return;
            } else {
                display = result;
                result = "";
            }
            currentOperator = btn.getText().toString();
        }
        display += btn.getText();
        currentOperator = btn.getText().toString();
        UpdateScreen();
    }

    private void Clear() {
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v) {
        Clear();
        UpdateScreen();
    }

    private double Operate(String a, String b, String op) {
        switch (op) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "*":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            default:
                return -1;
        }
    }

    public boolean Getresult() {
        if (display.charAt(0) == '-') {
            StringBuilder sb = new StringBuilder(display);
            display = String.valueOf(sb.deleteCharAt(0));
            String[] operation = display.split(Pattern.quote(currentOperator));
            if (operation.length < 2) return false;
            display="-"+display;
            operation[0] = "-" + operation[0];
            result = String.valueOf(Operate(operation[0], operation[1], currentOperator));
            return true;
        } else {
            String[] operation = display.split(Pattern.quote(currentOperator));
            if (operation.length < 2) return false;
            result = String.valueOf(Operate(operation[0], operation[1], currentOperator));
            return true;
        }
    }

    public void onClickEqual(View v) {
        if (currentOperator == "") return;
        if (!Getresult()) return;
        screen.setText(display + "\n" + result);
    }
}
