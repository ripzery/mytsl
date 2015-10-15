package com.socket9.tsl.Utils;


import android.util.Log;

/**
 * Created by Euro on 10/15/2015 AD.
 */
public class Logger {

    private static boolean LOGGING_ENABLED = true;

    private static final int STACK_TRACE_LEVELS_UP = 5;
    private static final String TAG = "DEBUG";

    public static void d(String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.d(tag, getClassNameMethodNameAndLineNumber() + message);
        }
    }

    public static void d(String message) {
        if (LOGGING_ENABLED) {
            Log.d(TAG, getClassNameMethodNameAndLineNumber() + message);
        }
    }

    public static void e(String message) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, getClassNameMethodNameAndLineNumber() + message);
        }
    }

    /**
     * Get the current line number. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return int - Current line number.
     * @author kvarela
     */
    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getLineNumber();
    }

    /**
     * Get the current class name. Note, this will only work as called from this
     * class as it has to go a predetermined number of steps up the stack trace.
     * In this case 5.
     *
     * @return String - Current line number.
     * @author kvarela
     */
    private static String getClassName() {
        String fileName = Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getFileName();

        // kvarela: Removing ".java" and returning class name
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return String - Current line number.
     * @author kvarela
     */
    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getMethodName();
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @return String - String representing class name, method name, and line
     * number.
     * @author kvarela
     */
    private static String getClassNameMethodNameAndLineNumber() {
        return "[" + getClassName() + "." + getMethodName() + "()-" + getLineNumber() + "]: ";
    }
}
