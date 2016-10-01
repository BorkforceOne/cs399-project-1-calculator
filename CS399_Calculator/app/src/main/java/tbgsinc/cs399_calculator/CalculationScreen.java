package tbgsinc.cs399_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class CalculationScreen extends AppCompatActivity {

    String currentEquation = "";
    String lastSolution = "";

    String[] reservedWords = {
            "ANS",
            "Infinity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
    }

    public void onTap(View v){
        Button b = (Button) findViewById(v.getId());
        final TextView tv = (TextView) findViewById(R.id.equationView);

        switch (v.getId()) {
            case R.id.buttonBack:
                if (currentEquation.length() > 0) {
                    // Look for non-operator strings and erase them all the way
                    boolean found = false;

                    // Check for reserved words and delete them when BACK is pressed
                    for (String reservedWord : reservedWords) {
                        if (currentEquation.endsWith(reservedWord)) {
                            currentEquation = currentEquation.subSequence(0, currentEquation.length() - reservedWord.length()).toString();
                            found = true;
                            break;
                        }
                    }

                    // See if we replaced anything, if we didn't then we can just truncate it
                    if (!found)
                        currentEquation = currentEquation.subSequence(0, currentEquation.length() - 1).toString();
                }

                tv.setText(currentEquation);
                break;

            case R.id.buttonEquals:
                Parser p = new Parser();

                String equation = currentEquation.replaceAll("ANS", lastSolution);

                try {
                    Double DD = solveEquation(p.translate(equation));
                    lastSolution = DD.toString();
                    currentEquation = "";
                    String newString = "= " + lastSolution;
                    tv.setText(newString);
                }
                catch (Exception e) {
                    // An error occurred, display an error and clear the equation screen
                    tv.setText("ERROR");
                    currentEquation = "";
                }
                break;

            case R.id.buttonAdd:
            case R.id.buttonSubtract:
            case R.id.buttonMultiply:
            case R.id.buttonDivide:
            case R.id.buttonPow:
            case R.id.buttonSqrt:
                // Let an operator use the previous ANS
                if (currentEquation.equals("")) {
                    currentEquation += "ANS";
                }
                currentEquation += b.getText();
                tv.setText(currentEquation);
                break;

            default:
                currentEquation += b.getText();
                tv.setText(currentEquation);
                break;
        }

        // Scroll horizontally to the end of the TextView component
        tv.post(new Runnable() {
            public void run() {
                tv.measure(0, 0);
                int width = Math.max(tv.getMeasuredWidth() - tv.getWidth(), 0);
                tv.scrollTo(width, 0);
            }
        });
    }

    public double solveEquation(String equation){
        Stack<Double> eqStack = new Stack<Double>();

        String[] sEq = equation.split(" ");
        for(int s=0;s<sEq.length;s++){
            if(sEq[s].equals(Operator.EXPONENTIATION.symbol())){
                double y = eqStack.pop();
                double x = eqStack.pop();
                eqStack.push(Math.pow(x,y));
            }
            else if(sEq[s].equals(Operator.MULTIPLICATION.symbol()))
                eqStack.push(eqStack.pop()*eqStack.pop());
            else if(sEq[s].equals(Operator.DIVISION.symbol())){
                double y = eqStack.pop();
                double x = eqStack.pop();
                eqStack.push(x/y);
            }
            else if(sEq[s].equals(Operator.ADDITION.symbol()))
                eqStack.push(eqStack.pop()+eqStack.pop());
            else if(sEq[s].equals(Operator.SUBTRACTION.symbol())){
                double y = eqStack.pop();
                double x = eqStack.pop();
                eqStack.push(x-y);
            }
            else if(sEq[s].equals(Operator.SIN.symbol()))
                eqStack.push(Math.sin(eqStack.pop()));
            else if(sEq[s].equals(Operator.COS.symbol()))
                eqStack.push(Math.cos(eqStack.pop()));
            else if(sEq[s].equals(Operator.TAN.symbol()))
                eqStack.push(Math.tan(eqStack.pop()));
            else if(sEq[s].equals(Operator.LOG.symbol()))
                eqStack.push(Math.log(eqStack.pop()));
            else if(sEq[s].equals(Operator.SQRT.symbol()))
                eqStack.push(Math.sqrt(eqStack.pop()));
            else
                eqStack.push(Double.parseDouble(sEq[s]));
        }

        return eqStack.pop();
    }
}