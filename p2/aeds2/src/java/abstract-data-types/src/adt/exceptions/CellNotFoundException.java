package adt.exceptions;

public class CellNotFoundException extends RuntimeException 
{
    public CellNotFoundException()
    {

    }

    public CellNotFoundException(String message)
    {
        super(message);
    }
}
