package tbgsinc.cs399_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
    }

    public void onTap(View v){
        Button b = (Button) findViewById(v.getId());
        TextView tv = (TextView) findViewById(R.id.equationView);

        tv.setText(tv.getText() + (String)b.getText());
    }
}