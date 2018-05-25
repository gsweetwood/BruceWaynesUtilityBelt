package com.example.garret.brucewaynesutilitybelt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
    private  double conversionRate;
    private RadioButton radButtonLength, radButtonVolume, radButtonMass, radButtonTemperature;
    private RadioGroup unitSelection;
    private TextView result;
    private double convertedValue;
    private EditText inputValue;


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
        conversionTable.put("Km", 100000.0);
        conversionTable.put("oz (mass)", 1.0);
        conversionTable.put("lb", 16.0);
        conversionTable.put("tons", 32000.0);
        conversionTable.put("mg", 1.0);
        conversionTable.put("g", 1000.0);
        conversionTable.put("Kg", 1000000.0);
        conversionTable.put("oz (liquid)", 6.0);
        conversionTable.put("teaspoon", 1.0);
        conversionTable.put("tablespoon", 3.0);
        conversionTable.put("cups", 48.0);
        conversionTable.put("pints", 96.0);
        conversionTable.put("quarts", 192.0);
        conversionTable.put("gallons", 768.0);
        conversionTable.put("mL", 1.0);
        conversionTable.put("L", 1000.0);



        //Set up spinners to read from an array using an array adapter
        spinnerIn = findViewById(R.id.spinnerIn);

        spinnerOut = findViewById(R.id.spinnerOut);
        setupSpinners();
        unitSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setupSpinners();
            }
        });

        inputValue = (EditText) findViewById(R.id.inputValue);
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateResult();
            }
        });

        spinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }

    //This will calculate the conversion to the new set of units
    public double convert(double startingValue, double leftConvRate, double rightConvRate){
        return (startingValue * leftConvRate) * conversionRate / (rightConvRate);
    }

    //Method to update the result of the conversion
    public void updateResult(){
        try{
            if (!inputValue.getText().toString().equals("")){
                convertedValue = convert(Double.parseDouble(inputValue.getText().toString()), conversionTable.get(spinnerIn.getSelectedItem().toString()), conversionTable.get(spinnerOut.getSelectedItem().toString()));
                result.setText(String.format(Locale.getDefault(), "%,.2f", convertedValue));
            }
            else{
                result.setText(R.string.unitConvertDefault);
            }
        }catch(Exception e){
            System.out.println("After text changed did not work");
            Toast.makeText(UnitCoverter.this, "ERR: <afterTextChanged> failed", Toast.LENGTH_LONG).show();

        }
    }

    //set populate spinners with the chosen units
    public void setupSpinners(){
        int unitSelectionChoice = unitSelection.getCheckedRadioButtonId();

        switch (unitSelectionChoice){
            case R.id.radMass:
                conversionRate = 28349.5;
                adapterIn = ArrayAdapter.createFromResource(this,R.array.imp_mass, android.R.layout.simple_spinner_item);
                adapterOut = ArrayAdapter.createFromResource(this,R.array.metric_mass, android.R.layout.simple_spinner_item);
                break;
            case R.id.radLength:
                conversionRate = 2.54;
                adapterIn = ArrayAdapter.createFromResource(this,R.array.imp_length, android.R.layout.simple_spinner_item);
                adapterOut = ArrayAdapter.createFromResource(this,R.array.metric_length, android.R.layout.simple_spinner_item);
                break;
            case R.id.radVolume:
                conversionRate = 4.92892;
                adapterIn = ArrayAdapter.createFromResource(this,R.array.imp_volume, android.R.layout.simple_spinner_item);
                adapterOut = ArrayAdapter.createFromResource(this,R.array.metric_volume, android.R.layout.simple_spinner_item);
        }
       // adapterIn = ArrayAdapter.createFromResource(this,R.array.imp_length, android.R.layout.simple_spinner_item);
        adapterIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIn.setAdapter(adapterIn);

       // adapterOut = ArrayAdapter.createFromResource(this,R.array.metric_length, android.R.layout.simple_spinner_item);
        adapterOut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOut.setAdapter(adapterOut);


    }

}
