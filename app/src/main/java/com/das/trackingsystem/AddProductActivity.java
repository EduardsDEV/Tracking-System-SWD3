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

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
//        findViewById(R.id.productIdText).requestFocus();
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_product_componentsLayout);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.add_product_component);
        layout.addView(editText);
        editText.addTextChangedListener(new MyTextWatcher(editText, layout));
    }


    /**
     * Called when the user taps the Save button is clicked
     */
    public void saveProduct(View view) { // view is the View object that was clicked
        EditText editText = (EditText) findViewById(R.id.productIdText);
        String productUniqueCode = editText.getText().toString();
        AlertDialog alertDialog = new AlertDialog.Builder(AddProductActivity.this).create();
        if (Utility.isNotNull(productUniqueCode)) {
            RequestParams requestParams = new RequestParams("uniqueCode", productUniqueCode);
            LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_product_componentsLayout);
            for (int i = 0; i < layout.getChildCount(); i++) {
                EditText componentText = (EditText) layout.getChildAt(i);
                String componentUniqueCode = componentText.getText().toString();
                if (Utility.isNotNull(componentUniqueCode)) {
                    requestParams.add("componentsUniqueCodes[]", componentUniqueCode);
                }
            }
            Log.i("AddProductActivity", "Saving Product with ID: " + editText.getText().toString());
            invokeWebService(requestParams);
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Product was saved");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            Log.e("SaveProduct", "Product ID is empty");
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please provide Product ID");
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
}
