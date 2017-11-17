package com.das.trackingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddInvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_invoice_productsLayout);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.product_id_prompt);
        layout.addView(editText);
        editText.addTextChangedListener(new MyTextWatcher(editText, layout));
    }

    /**
     * Called when the user taps the Save button is clicked
     */
    public void saveInvoice(View view) { // view is the View object that was clicked
        EditText invoiceIdEditText = (EditText) findViewById(R.id.invoiceIdText);

        String invoiceUniqueCode = invoiceIdEditText.getText().toString();
        AlertDialog alertDialog = new AlertDialog.Builder(AddInvoiceActivity.this).create();
        if (Utility.isNotNull(invoiceUniqueCode)) {
            RequestParams requestParams = new RequestParams("uniqueCode", invoiceUniqueCode);
            LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_invoice_productsLayout);
            for (int i = 0; i < layout.getChildCount(); i++) {
                EditText productText = (EditText) layout.getChildAt(i);
                String productUniqueCode = productText.getText().toString();
                if (Utility.isNotNull(productUniqueCode)) {
                    requestParams.add("productsUniqueCodes[]", productUniqueCode);
                }
            }
            // add info about customer
            requestParams.add("firstName", "dummyFirstName");
            requestParams.add("lastName", "dummyLastName");
            requestParams.add("email", "dummyEmail@gma.com");
            requestParams.add("address", "saint carolina");
            requestParams.add("phoneNumber", "50 23 48 91");
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Invoice was saved");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            Log.i("AddInvoiceActivity", "Saving Invoice with ID: " + invoiceUniqueCode);
            invokeWebService(requestParams);
        } else {
            Log.e("SaveInvoice", "Invoice ID is empty");
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please provide Invoice ID");
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
