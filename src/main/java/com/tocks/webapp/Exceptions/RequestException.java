package com.tocks.webapp.Exceptions;

public class RequestException extends Throwable
{
    private String error;

    public RequestException(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
