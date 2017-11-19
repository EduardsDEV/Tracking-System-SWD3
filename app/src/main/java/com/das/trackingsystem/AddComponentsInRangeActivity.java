package com.das.trackingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AddComponentsInRangeActivity extends AppCompatActivity {

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_components_in_range);

        alertDialog = new AlertDialog.Builder(AddComponentsInRangeActivity.this).create();


        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // TODO: 17-Nov-17 Add listener on second edit text, so that it call saveComponents() after ID entered
        ((EditText) findViewById(R.id.startBoundaryId)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                findViewById(R.id.endBoundaryId).requestFocus();
            }
        });
    }

    public void saveComponents(View view) {
        String startId = ((EditText) findViewById(R.id.startBoundaryId)).getText().toString();
        String endId = ((EditText) findViewById(R.id.endBoundaryId)).getText().toString();
        if (Utility.isNotNull(startId) && Utility.isNotNull(endId)) {
            RequestParams requestParams = new RequestParams();

            requestParams.add("startId", startId);
            requestParams.add("endId", endId);

            Log.d("Save range", "Trying to call the web service to save range of components");

            invokeWebService(requestParams);
        } else {
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please provide Components IDs");
            alertDialog.show();
        }
    }

    private void invokeWebService(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://10.0.2.2:8080/components-range", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Components have been saved");
                alertDialog.show();

                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d("AddComponentsInRange", response);
                } else {
                    Log.d("Invocation succeeded", "Empty response");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String message = "Something happened.\n" +
                        "Server responded with status " +
                        statusCode;

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage(message);
                alertDialog.show();


                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.i("AddComponentsInRange", statusCode + " " + response);
                } else {
                    Log.d("Invocation failed", "Empty response");
                }
            }
        });
    }
}
