package adt.linear;

public interface IHashTable<T> 
{
    public boolean insert(T value);
    public boolean search(T value);
    public T remove(T value);    
}
