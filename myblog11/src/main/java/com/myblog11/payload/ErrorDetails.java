package com.myblog11.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetails(java.util.Date date, String message, String details) {

        this.timeStamp = (Date) date;
        this.message = message;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}

