package com.buttermove.estimate.exception;

import com.buttermove.estimate.constant.EstimateConstant;

public class TypeException extends Throwable  {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public TypeException(String message) {
        this.message = message;
    }
    public TypeException() {
        this.message = EstimateConstant.IP_CLIENT_WRONG_MESSAGE;
    }
}
