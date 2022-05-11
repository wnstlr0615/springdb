package com.example.springdb.exception;

import com.example.springdb.repository.ex.MyDbException;

public class MyDuplicateKeyException extends MyDbException {
    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
