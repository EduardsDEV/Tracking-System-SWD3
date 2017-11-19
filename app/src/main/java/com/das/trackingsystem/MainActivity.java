package com.das.trackingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Add component button
     */
    public void openAddComponentUi(View view) { // view is the View object that was clicked
        Intent intent = new Intent(this, AddComponentActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Add component button
     */
    public void openAddProductUi(View view) { // view is the View object that was clicked
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    public void openAddInvoiceUi(View view) { // view is the View object that was clicked
        Intent intent = new Intent(this, AddInvoiceActivity.class);
        startActivity(intent);
    }


    public void openAddComponentsRangeUi(View view) {
        Intent intent = new Intent(this, AddComponentsInRangeActivity.class);
        startActivity(intent);
    }
}
