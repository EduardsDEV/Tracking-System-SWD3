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

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    /**
     * Called when the user taps the Save button is clicked
     */
    public void saveProduct(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.productIdText);
        // check for empty string
        String uniqueCode = editText.getText().toString();
        EditText editTextC = (EditText) findViewById(R.id.componentIdText);
        String componentUniqueCode = editTextC.getText().toString();
        if (Utility.isNotNull(uniqueCode) && Utility.isNotNull(componentUniqueCode)) {
            RequestParams requestParams = new RequestParams("uniqueCode", uniqueCode);
            requestParams.add("componentUniqueCode", componentUniqueCode);
            Log.i("AddProductActivity", "Saving Product with ID: " + editText.getText().toString());
            invokeWebService(requestParams);
        }
    }

    public void invokeWebService(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        // host loopback interface in the emulator
        client.post("http://10.0.2.2:8080/product", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject obj = new JSONObject(result.length() > 0 ? result : "{}");


                    if (obj.has("id") && obj.getInt("id") > 0) {
                        Log.i("AddProductActivity", "Product with ID: " + obj.getString("uniqueCode") + " was saved");

                    } else {
                        Log.e("AddProductActivity", obj.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.i("AddProductActivity", statusCode + " " + response);
            }
        });
    }


    public void addNewComponentField(View view) {
//        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
//
//        Button b = new Button(this);
//        b.setText("A" + (layout.getChildCount() + 1));
//        LinearLayout hor = new LinearLayout(this);
//        TextView textView = new TextView(this);
//        textView.setText("Component #" + (layout.getChildCount() + 1));
//        hor.addView(textView);
//        hor.addView(b);
//        layout.addView(hor);
    }

}
