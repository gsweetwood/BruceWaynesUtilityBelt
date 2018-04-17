package com.example.garret.brucewaynesutilitybelt;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TipCalculator extends AppCompatActivity {
    private double total = 0.00;
    private double tip = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        EditText et = (EditText) findViewById(R.id.numberInput_tipCalc);
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                View view = (View) findViewById(R.id.textview_total);
                updateTip(view);
            }
        });
    }

    public void updateTip(View view){
        //Determine which button is selected
        RadioGroup radGrp = findViewById(R.id.radioGroup);
        int checkedRadioButton = radGrp.getCheckedRadioButtonId();

        //Get the text that's entered by the user
        EditText enteredSubTotal = findViewById(R.id.numberInput_tipCalc);
        String value = enteredSubTotal.getText().toString();

        //If there's something there, calculate the tip and total
        if (!value.equals("")) {
            final double subTotal = Double.parseDouble(value);
            if (subTotal != 0.0) {
                switch (checkedRadioButton) {
                    case R.id.radioButton:
                        total = subTotal;
                        tip = 0.00;
                        break;
                    case R.id.radioButton15:
                        total = subTotal * 1.15;
                        tip = subTotal * 0.15;
                        break;
                    case R.id.radioButton20:
                        total = subTotal * 1.2;
                        tip = subTotal * 0.2;
                        break;
                    case R.id.radioButton50:
                        total = subTotal * 1.5;
                        tip = subTotal * 0.5;
                        break;
                }
            } else {
                //If the field is empty, display a temporary message to the user
                Toast.makeText(TipCalculator.this ,R.string.tipCalc_notEmpty, Toast.LENGTH_LONG).show();
            }

        }else {
            tip = 0.00;
            total = 0.00;
        }
        //Set the tip and total views to the new calculated values
        TextView tipTotal = findViewById((R.id.textView_tipTotal));
        tipTotal.setText(String.format(Locale.getDefault(), "%.2f", tip));
        TextView finalTotal =  findViewById(R.id.textview_total);
        finalTotal.setText(String.format(Locale.getDefault(), "%.2f", total));

    }





}
