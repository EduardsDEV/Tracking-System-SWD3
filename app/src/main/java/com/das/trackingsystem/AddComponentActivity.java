package com.das.trackingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_component);

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_components_componentsLayout);
        final EditText editText = (EditText) findViewById(R.id.componentIdText);
        editText.addTextChangedListener(new MyTextWatcher(editText, layout));
    }

    /**
     * Called when the user taps the Save button is clicked
     */
    public void saveComponent(View view) { // view is the View object that was clicked
        LinearLayout componentsLayout = (LinearLayout) findViewById(R.id.activity_add_components_componentsLayout);
        RequestParams requestParams = new RequestParams();
        AlertDialog alertDialog = new AlertDialog.Builder(AddComponentActivity.this).create();

        for (int i = 0; i < componentsLayout.getChildCount(); i++) {
            String uniqueCode = ((EditText)componentsLayout.getChildAt(i)).getText().toString();
            // check for empty string
            if (Utility.isNotNull(uniqueCode)) {
                Log.i("AddComponentActivity", "Saving Component with ID: " + uniqueCode);
                requestParams.add("uniqueCodes", uniqueCode);
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Component was saved");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
        if(requestParams.has("uniqueCodes")) { // if at least 1 component was scanned
            invokeWebService(requestParams);

        }else{
            // Display Snackbar pop-up
            Log.i("AddComponentActivity", "No components scanned. No action taken");
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please provide Component ID");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public void invokeWebService(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        // host loopback interface in the emulator
        client.post("http://10.0.2.2:8080/component", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("id") > 0) {
                        Log.i("AddComponentActivity", "Component with ID: " + obj.getString("uniqueCode") + " was saved");
                    } else {
                        Log.e("AddComponentActivity", "smth");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.i("AddComponentActivity", statusCode + " " + response);
            }
        });
    }
}
