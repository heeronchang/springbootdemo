package com.example.springbootdemo.exception;

public class TestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;
    private int code = 501;

    public TestException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TestException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public TestException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
