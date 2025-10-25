package com.LPC.user_service.exception;

public class DuplicateEmailException extends RuntimeException
{
    public DuplicateEmailException(String message)
    {
        super(message);
    }
}
