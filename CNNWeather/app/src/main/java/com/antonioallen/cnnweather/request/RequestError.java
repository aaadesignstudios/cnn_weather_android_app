package com.antonioallen.cnnweather.request;

/**
 * Created by antonioallen on 11/3/16.
 */

public class RequestError {
    /**
     * Error Returned from GetWeather
     */
    String message, friendlyMessage;

    public RequestError(String errorMessage, String friendlyMessage){
        this.message = errorMessage;
        this.friendlyMessage = friendlyMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    public void setFriendlyMessage(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
    }

}
