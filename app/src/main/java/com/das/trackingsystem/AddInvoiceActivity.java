package com.das.trackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddInvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
    }

    /** Called when the user taps the Save button is clicked*/
    public void saveInvoice(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.invoiceIdText);
        // check for empty string
        Log.i("AddInvoiceActivity", "Invoice ID: " + editText.getText().toString());
    }
}
