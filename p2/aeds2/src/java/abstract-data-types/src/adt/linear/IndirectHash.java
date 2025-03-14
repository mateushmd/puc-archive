package adt.linear;

import adt.flexible.LinkedList;

public class IndirectHash implements IHashTable<Integer>
{
    private int size;
    private LinkedList[] array;

    public IndirectHash(int size)
    {
        this.size = size;
        array = new LinkedList[size];
        for(int i = 0; i < size; i++)
        {
            array[i] = new LinkedList();
        }
    }

    public IndirectHash()
    {
        this(10);
    }

    public int hash(Integer key)
    {
        return key % size;
    } 

    @Override
    public boolean insert(Integer value) {
        if(value == null)
            return false;
        
        int idx = hash(value);

        array[idx].insert(value);

        return true;
    }

    @Override
    public boolean search(Integer value) {
        if(value == null)
            return false;

        int idx = hash(value);

        return array[idx].search(value);
    }

    @Override
    public Integer remove(Integer value) {
        if(value == null)
            return null;

        int idx = hash(value);

        int valueIdx = array[idx].firstOf(value);

        if(valueIdx == -1)
            return null;

        return array[idx].removeAt(valueIdx);
    }
}
