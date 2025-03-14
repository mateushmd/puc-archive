package adt.linear;

public class HashRehash implements IHashTable<Integer> 
{
    private int size;
    private Integer array[];

    public HashRehash(int size)
    {
        this.size = size;

        array = new Integer[size];
    }

    public HashRehash()
    {
        this(10);
    }    

    private int hash(Integer key)
    {   
        return key % size;
    }

    private int rehash(Integer key)
    {
        return ++key % size;
    }

    @Override
    public boolean insert(Integer value) 
    {
        if(value == null)
            return false;

        int idx = hash(value);

        if(array[idx] != null)
        {
            idx = rehash(value);

            if(array[idx] != null)
                return false;
        }

        array[idx] = value;

        return true;
    }

    @Override
    public boolean search(Integer value) 
    {
        if(value == null)
            return false;
            
        if(array[hash(value)].equals(value) || array[rehash(value)].equals(value))
            return true;

        return false;
    }

    @Override
    public Integer remove(Integer value) {
        if(value == null)
            return null;

        int idx = hash(value);

        if(array[idx] == null || !array[idx].equals(value))
        {
            idx = rehash(value);

            if(array[idx] == null || !array[idx].equals(value))
                return null;
        }

        Integer removed = array[idx];
        array[idx] = null;
        return removed;
    }
    
}
