package com.das.trackingsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
    }

    /**
     * Called when the user taps the Save button is clicked
     */
    public void saveComponent(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.componentIdText);
        // check for empty string
        String uniqueCode = editText.getText().toString();
        if (Utility.isNotNull(uniqueCode)) {
            RequestParams requestParams = new RequestParams("uniqueCode", uniqueCode);
            Log.i("AddComponentActivity", "Saving Component with ID: " + editText.getText().toString());
            invokeWebService(requestParams);
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
