package vmaestro.pis2017.ub.edu;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner currency1;
    private Spinner currency2;

    private CheckBox checkComission;
    private CheckBox checkFactor;

    private EditText amountCurrency1;
    private EditText comision;
    private EditText factorEx;

    private TextView amountCurrency2;
    private ImageButton btnChange;

    private List<Currency> currencies;
    private CustomAdapter adapter;
    private ExchangeController exchangeController;
    private CurrencyData data;

    private MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        // Create new CurrencyData and add them to the Currency Selector
        currencies = new ArrayList<>();
        data = new CurrencyData(currencies);

        currency1 = findViewById(R.id.spinner_id1);
        currency2 = findViewById(R.id.spinner_id2);

        adapter = new CustomAdapter(this, currencies);
        currency1.setAdapter(adapter);
        currency2.setAdapter(adapter);
        currency2.setSelection(1);

        //Load Controller
        exchangeController = ExchangeController.getInstance((Currency) currency1.getSelectedItem(), (Currency) currency2.getSelectedItem());

        // Find the View Components
        amountCurrency1 = findViewById(R.id.amountCurrency1);
        amountCurrency2 = findViewById(R.id.amountCurrency2);
        btnChange = findViewById(R.id.conversor_id);
        comision = findViewById(R.id.commissionAmount);
        checkComission = findViewById(R.id.checkBox);
        factorEx = findViewById(R.id.factorIn);
        checkFactor = findViewById(R.id.checkBox_Factor);


        this.updateDisplay();


        // Change the currency converter order
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrder(v);
            }
        });

        // Hide the keyBoard on touch the Screen
        FocusChangeListener focusListener = new FocusChangeListener();
        amountCurrency1.setOnFocusChangeListener(focusListener);
        comision.setOnFocusChangeListener(focusListener);
        factorEx.setOnFocusChangeListener(focusListener);

        // Auto update the Currency exchange on select a new currency
        ItemSelectedListener itemSelect = new ItemSelectedListener();
        currency1.setOnItemSelectedListener(itemSelect);
        currency2.setOnItemSelectedListener(itemSelect);


        // Auto update the Currency exchange while user is typing
        amountCurrency1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateExchanger();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Update Result + commission
        comision.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCommissionExchange();
                updateExchanger();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Update result + Factor
        factorEx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCustomFactorExchange();
                updateExchanger();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // Auto update the currency on check the commission
        checkComission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateDisplayCommission();
                updateCommissionExchange();
                updateExchanger();
                if(isChecked){
                    if(!((Currency) currency1.getSelectedItem()).isDifferentCurrency((Currency) currency2.getSelectedItem())){
                        Toast.makeText(getApplicationContext(), "Commission will not be applied because the currencies are the same!", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });

        // Auto update the currency on check the Custom factor
        checkFactor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // is checked corresponds to the new state from -> to
                updateDisplayFactorExchange();
                updateCustomFactorExchange();
                updateExchanger();
            }
        });
    }

    // FactorExchange
    private void updateDisplayFactorExchange() {
        if(checkFactor.isChecked()){
            factorEx.setText(String.valueOf(exchangeController.getCustomFactor()));
        }else{
            factorEx.getText().clear();
            factorEx.setHint(String.valueOf(exchangeController.getCustomFactor()));
        }
    }
    private void updateCustomFactorExchange(){
        String cfs = this.factorEx.getText().toString();
        if(isValidLength(cfs)){
            if(isValidStart(cfs)){
                float cf = Float.parseFloat(cfs);
                exchangeController.setCustomFactor(cf);
            }else{
                this.factorEx.getText().clear();
                this.factorEx.setError("Error! The input should start with a number!");
            }
        }else{
            this.factorEx.getText().clear();
            this.factorEx.setHint(String.valueOf(exchangeController.getCustomFactor()));
        }
    }

    // CommissionExchange
    private void updateDisplayCommission() {
        if (checkComission.isChecked()) {
            this.comision.setText(String.valueOf(exchangeController.getCommission().getCommission()));
        } else {
            this.comision.getText().clear();
            this.comision.setHint(String.valueOf(exchangeController.getCommission().getCommission()));
        }
    }
    private void updateCommissionExchange() {

        String cms = this.comision.getText().toString();
        if (isValidLength(cms)) {
            if (isValidStart(cms)) {
                float cm = Float.parseFloat(cms);
                try {
                    exchangeController.changeCommission(cm);
                } catch (AppDivisesException e) { // if it causes an error it defaults sets to 0.25
                    this.comision.getText().clear();
                    this.comision.setHint(String.valueOf(exchangeController.getCommission().getCommission()));
                    comision.setError(e.getMessage());
                    e.printStackTrace();
                }
            } else {
                this.comision.getText().clear();
                comision.setError("Error! The input should start with a number!");
        }
        } else {
            this.comision.getText().clear();
            this.comision.setHint(String.valueOf(exchangeController.getCommission().getCommission()));
        }
    }

    // Both Displays
    private void updateDisplay() {
        this.updateDisplayCommission();
        this.updateDisplayFactorExchange();

    }

    // Exchange result
    private void updateExchanger(){
        Currency c2 = (Currency) currency2.getSelectedItem();
        String samount1 = amountCurrency1.getText().toString();

        if(isValidStart(samount1)){
            if(isValidLength(samount1)) {
                float amount = Float.parseFloat(samount1);
                float newAmount = exchangeController.performExchange(amount, checkComission.isChecked(), checkFactor.isChecked());
                amountCurrency2.setText(String.valueOf(newAmount) + " " + c2.getType().getSymbol());
            }else{
                amountCurrency2.setText(String.valueOf(0.00f) + " " + c2.getType().getSymbol());
            }
        }else{
            this.amountCurrency1.getText().clear();
            this.amountCurrency1.setError("Error! The input should start with a number!");
        }
    }

    // Button to change order
    private void changeOrder(View v) {
        int pos1 = currency1.getSelectedItemPosition();
        int pos2 = currency2.getSelectedItemPosition();

        if (pos1 != pos2) {
            currency1.setSelection(pos2);
            currency2.setSelection(pos1);
            exchangeController.changeOrder();
            this.updateDisplay();
            this.updateExchanger();

        }
    }


    // Methods to verify inputs
    private boolean isValidInput(String input) {
        return (input.length() != 0 && !input.startsWith("."));
    }
    private boolean isValidLength(String input) {
        return (input.length() != 0);
    }
    private boolean isValidStart(String input) {
        return !input.startsWith(".");
    }


    private class FocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            touchScreen(v, hasFocus);
        }

        private void touchScreen(View v, boolean hasFocus) {
            if (!hasFocus) {
                this.hideKeyBoard(v);
                updateDisplay();
            }
        }

        private void hideKeyBoard(View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Currency c1 = (Currency) currency1.getSelectedItem();
            Currency c2 = (Currency) currency2.getSelectedItem();
            String factor = factorEx.getText().toString();
            exchangeController.updateFactorExchange(c1, c2);
            if(isValidInput(factor)){
                exchangeController.setCustomFactor(Float.parseFloat(factor));
            }
            updateDisplay();
            updateExchanger();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}

