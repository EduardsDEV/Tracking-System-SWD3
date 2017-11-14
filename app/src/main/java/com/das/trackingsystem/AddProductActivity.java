package com.das.trackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    /** Called when the user taps the Save button is clicked*/
    public void saveProduct(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.productIdText);
        // check for empty string
        Log.i("AddProductActivity", "Product ID: " + editText.getText().toString());
    }
}
