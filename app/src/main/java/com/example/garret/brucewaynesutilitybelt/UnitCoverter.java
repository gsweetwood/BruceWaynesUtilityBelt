package com.example.garret.brucewaynesutilitybelt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Locale;

public class UnitCoverter extends AppCompatActivity {
    private Spinner spinnerIn, spinnerOut;
    private ArrayAdapter<CharSequence> adapterIn, adapterOut;
    private Hashtable<String, Double> conversionTable;
    private static final double conversionRateInToCM = 2.54;
    private RadioButton radButtonLength, radButtonVolume, radButtonMass, radButtonTemperature;
    private RadioGroup unitSelection;
    private TextView result;
    private double convertedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_coverter);


        result = findViewById(R.id.resultValue);

        unitSelection = findViewById(R.id.unitSelection);

        //A hashtable that will contain the rate from the original unit to the base unit ex. ft to in, miles to inches, etc
        conversionTable = new Hashtable<>();
        conversionTable.put("in", 1.0);
        conversionTable.put("ft", 12.0);
        conversionTable.put("miles", 63360.0);
        conversionTable.put("cm", 1.0);
        conversionTable.put("m", 100.0);
        conversionTable.put("km", 100000.0);


        //Set up spinners to read from an array using an array adapter
        spinnerIn = findViewById(R.id.spinnerIn);
        adapterIn = ArrayAdapter.createFromResource(this,R.array.imp_length, android.R.layout.simple_spinner_item);
        adapterIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIn.setAdapter(adapterIn);

        spinnerOut = findViewById(R.id.spinnerOut);
        adapterOut = ArrayAdapter.createFromResource(this,R.array.metric_length, android.R.layout.simple_spinner_item);
        adapterOut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOut.setAdapter(adapterOut);

        final EditText inputValue = (EditText) findViewById(R.id.inputValue);
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (!inputValue.getText().equals("")){
                        convertedValue = convert(Double.parseDouble(inputValue.getText().toString()), conversionTable.get(spinnerIn.getSelectedItem().toString()), conversionTable.get(spinnerOut.getSelectedItem().toString()));
                        result.setText(String.format(Locale.getDefault(), "%.2f", convertedValue));
                    }
                    else{
                        result.setText("0.00");
                    }
                }catch(Exception e){
                    System.out.println("After text changed did not work");
                    Toast.makeText(UnitCoverter.this, "ERR: <afterTextChanged failed", Toast.LENGTH_LONG);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    //This will calculate the conversion to the new set of units
    public double convert(double startingValue, double leftConvRate, double rightConvRate){
        double calculatedValue;
        calculatedValue = (startingValue * leftConvRate) * conversionRateInToCM / (rightConvRate);
        return calculatedValue;
    }
}
