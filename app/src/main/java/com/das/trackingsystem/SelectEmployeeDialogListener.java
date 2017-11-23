package com.das.trackingsystem;

/**
 * Created by Chris on 23-Nov-17.
 */

/**
 * Interface for receiving fragment dialog click event callback.
 */
public interface SelectEmployeeDialogListener {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
//    void onEmployeeSelected(DialogFragment dialog);

    /**
     * onClick() event callback method for a fragment dialog.
     * @param employeeName the name of the employee that was selected by the user.
     */
    void onEmployeeSelected(String employeeName);
}
