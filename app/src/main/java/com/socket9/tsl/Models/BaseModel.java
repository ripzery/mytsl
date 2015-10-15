package com.socket9.tsl.Models;

/**
 * Created by Euro on 10/4/15 AD.
 */
public class BaseModel {
    /**
     * message : send email successful.
     * result : true
     */
    private String message;
    private boolean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
