package tbgsinc.cs399_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class CalculationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
    }

    public void onTap(View v){
        Button b = (Button) findViewById(v.getId());
        TextView tv = (TextView) findViewById(R.id.equationView);

        switch (v.getId()) {
            case R.id.buttonBack:
                if (tv.getText().length() > 0) {
                    tv.setText(tv.getText().subSequence(0, tv.getText().length() - 1));
                }
                break;

            case R.id.buttonEquals:
                Parser p = new Parser();
                Double DD = solveEquation(p.translate(tv.getText().toString()));
                tv.setText(DD.toString());
                break;

            default:
                tv.append(b.getText());
                break;
        }

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