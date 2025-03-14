package adt.flexible;

abstract class Cell<T> {
    private T value;

    public Cell(T value)
    {
        this.value = value;
    }

    public Cell()
    {
        value = null;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value) 
    {
        this.value = value;
    }
}
