package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculatorController {
    @FXML
    private TextField inputField;
    private ArrayList<String> Expression = new ArrayList<>();

    private boolean Result = false;
    public void onNumericButtonClick(ActionEvent actionEvent) {
        if (Result){ inputField.setText(""); Result = false; }

            Button Clicked  = (Button) actionEvent.getSource();
            inputField.setText(inputField.getText() + Clicked.getText());


    }

    public void onOperationButtonClick(ActionEvent actionEvent) {
        Button Clicked = (Button) actionEvent.getSource();
        if (Objects.equals(Clicked.getText(), "-") && inputField.getText().isEmpty()){
            if(Expression.isEmpty()){
                inputField.setText("-");
            }else{
                Expression.add(Clicked.getText());
            }

        }else{
            if(Expression.isEmpty()){
                Expression.add(inputField.getText());

            }else {
                Expression.add(Clicked.getText());
                Expression.add(inputField.getText());
            }


            System.out.println(Expression);

            inputField.setText("");
        }


    }

    public void onEqualsButtonClick(ActionEvent actionEvent) {
        inputField.setText(String.valueOf(evaluate(Expression)));
    }
    public static int evaluate(ArrayList<String> expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty or null");
        }

        List<Object> intermediateExpression = new ArrayList<>();
        int currentValue = 0;
        String operator = "";
        for (String item : expression) {
            if (isNumeric(item)) {
                int num = Integer.parseInt(item);
                if (operator.equals("*")) {
                    currentValue = (int) intermediateExpression.remove(intermediateExpression.size() - 1);
                    currentValue *= num;
                    intermediateExpression.add(currentValue);
                } else if (operator.equals("/")) {
                    currentValue = (int) intermediateExpression.remove(intermediateExpression.size() - 1);
                    currentValue /= num;
                    intermediateExpression.add(currentValue);
                } else {
                    intermediateExpression.add(num);
                }
            } else {
                operator = item;
                if (!operator.equals("*") && !operator.equals("/")) {
                    intermediateExpression.add(operator);
                }
            }
        }

        int result = (int) intermediateExpression.get(0);
        operator = "";
        for (Object item : intermediateExpression) {
            if (item instanceof Integer) {
                switch (operator) {
                    case "+":
                        result += (int) item;
                        break;
                    case "-":
                        result -= (int) item;
                        break;
                }
            } else if (item instanceof String) {
                operator = (String) item;
            }
        }

        return result;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void onClearButtonClick(ActionEvent actionEvent) {
        inputField.setText("");
    }
}