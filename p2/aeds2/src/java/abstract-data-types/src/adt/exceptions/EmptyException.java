package adt.exceptions;

public class EmptyException extends RuntimeException
{
    public EmptyException()
    {
        super();
    }

    public EmptyException(String message)
    {
        super(message);
    }
}