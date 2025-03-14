package adt.flexible;

public abstract class AbstractDataType<T> 
{
    protected int size;

    public AbstractDataType()
    {
        size = 0;
    }
    
    public abstract void insert(T obj);

    public abstract T remove();

    public abstract void print();

    public int getSize()
    {
        return size; 
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}
