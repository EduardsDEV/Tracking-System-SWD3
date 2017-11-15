package com.das.trackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddInvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
    }

    /** Called when the user taps the Save button is clicked*/
    public void saveInvoice(View view) { // view is the View object that was clicked
        EditText invoiceIdEditText = (EditText) findViewById(R.id.invoiceIdText);

        String uniqueCode = invoiceIdEditText.getText().toString();
        String product1UniqueCode = ((EditText) findViewById(R.id.product1IdEditText)).getText().toString();
        String product2UniqueCode = ((EditText) findViewById(R.id.product2IdEditText)).getText().toString();
        if (Utility.isNotNull(uniqueCode) && Utility.isNotNull(product1UniqueCode) && Utility.isNotNull(product2UniqueCode)) {
            RequestParams requestParams = new RequestParams("uniqueCode", uniqueCode);
            requestParams.add("productsUniqueCodes[]", product1UniqueCode);
            requestParams.add("productsUniqueCodes[]", product2UniqueCode);

            // add info about customer
            requestParams.add("firstName", "dummyFirstName");
            requestParams.add("lastName", "dummyLastName");
            requestParams.add("email", "dummyEmail@gma.com");
            requestParams.add("address", "saint carolina");
            requestParams.add("phoneNumber", "50 23 48 91");

            Log.i("AddInvoiceActivity", "Saving Invoice with ID: " + uniqueCode);
            invokeWebService(requestParams);
        }

        Log.i("AddInvoiceActivity", "Invoice ID: " + invoiceIdEditText.getText().toString());
    }

    public void invokeWebService(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        // host loopback interface in the emulator
        client.post("http://10.0.2.2:8080/invoice", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("id") > 0) {
                        Log.i("AddInvoiceActivity", "Saved Invoice with ID: " + obj.getString("uniqueCode") + " was saved");
                    } else {
                        Log.e("AddInvoiceActivity", "smth");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.i("AddInvoiceActivity", statusCode + " " + response);
            }
        });
    }
}
