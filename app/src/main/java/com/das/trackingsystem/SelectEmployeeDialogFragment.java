package com.das.trackingsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris on 23-Nov-17.
 */

public class SelectEmployeeDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    SelectEmployeeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the SelectEmployeeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the SelectEmployeeDialogListener so we can send events to the host
            mListener = (SelectEmployeeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SelectEmployeeDialogListener");
        }
    }


    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    // show the dialog in full screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final List<String> employees = Arrays.asList("Cristi", "Frank", "Steven");
        final ListAdapter employeeList = new ArrayAdapter<>(getContext(), R.layout.simple_list_item_1, employees);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_select_employee)
                .setAdapter(employeeList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onEmployeeSelected(employees.get(which));
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
