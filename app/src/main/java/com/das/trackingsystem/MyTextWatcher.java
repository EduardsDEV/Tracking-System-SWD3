package com.das.trackingsystem;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Chris on 15-Nov-17.
 */

public class MyTextWatcher implements TextWatcher {

    private final EditText previousEditText;
//    private final TextWatcher previousTextWatcher;
    private final  LinearLayout layout;

    public MyTextWatcher(EditText previousEditText, LinearLayout layout) {
        super();
        this.previousEditText = previousEditText;
//        this.previousTextWatcher = previousTextWatcher;
        this.layout = layout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (previousEditText != null) {
            previousEditText.removeTextChangedListener(this);
        }
        final EditText newEditText = new EditText(previousEditText.getContext());
        newEditText.requestFocus();
        newEditText.addTextChangedListener(new MyTextWatcher(newEditText, layout));
        layout.addView(newEditText);
    }
}
