package com.buttermove.estimate.exception;

import com.buttermove.estimate.constant.EstimateConstant;

public class StateException extends Throwable  {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public StateException(String message) {
        this.message = message;
    }
    public StateException() {
        this.message = EstimateConstant.IP_CLIENT_WRONG_MESSAGE;
    }
}
