package com.catsgram.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {

    String parameter;
    public IncorrectParameterException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
