package com.das.trackingsystem;

/**
 * Created by Chris on 15-Nov-17.
 */
public class Utility {
    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and non-empty, and false for null or empty String object
     */
    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0;
    }
}

