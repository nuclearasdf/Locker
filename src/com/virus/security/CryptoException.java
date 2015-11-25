package com.virus.security;

/**
 * Created by codertimo on 15. 11. 25..
 */
public class CryptoException extends Exception
{
    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
