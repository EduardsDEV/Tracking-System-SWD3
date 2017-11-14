package com.das.trackingsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.sql.*;
import java.util.List;

public class AddComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_component);
    }

    /** Called when the user taps the Save button is clicked*/
    public void openAddComponentUi(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.componentIdText);
        // check for empty string
        Log.i("AddComponentActivity", "Component ID: " + editText.getText().toString());
    }
}
