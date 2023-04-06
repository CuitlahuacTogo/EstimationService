package com.buttermove.estimate.exception;

import com.buttermove.estimate.constant.EstimateConstant;

public class EstimateException extends Throwable  {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public EstimateException(String message) {
        this.message = message;
    }
    public EstimateException() {
        this.message = EstimateConstant.IP_CLIENT_WRONG_MESSAGE;
    }
}
