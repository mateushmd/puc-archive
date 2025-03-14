package adt.linear;

// Hash table with overflow space
public class HashOverflow implements IHashTable<Integer>
{
    private int size, table, extra;
    private int eIdx;
    private Integer[] array;

    public HashOverflow(int table, int extra)
    {
        this.table = table;
        this.extra = extra;
        size = table + extra;
        eIdx = 0;

        array = new Integer[size];
    }

    public HashOverflow()
    {
        this(13, 7);
    }

    private int hash(Integer key)
    {
        if(key == null)
            throw new IllegalArgumentException("Cannot hash null key");

        return key % table;
    }

    public boolean insert(Integer value)
    {
        if(value == null)
            return false;

        int idx = hash(value);

        if(array[idx] == null)
        {
            array[idx] = value;
            return true;
        }
        else if(eIdx < extra)
        {
            array[table + eIdx] = value;
            eIdx++;
            return true;
        }

        return false;
    }

    public boolean search(Integer value)
    {
        if(value == null)
            return false;

        int idx = hash(value);

        if(array[idx] == null)
            return false;

        if(array[idx].equals(value))
            return true;

        for(int i = 0; i < eIdx; i++)
            if(array[table + i].equals(value))
                return true;
        
        return false;
    }

    public Integer remove(Integer value) 
    {
        if(value == null)
            return null;

        int idx = hash(value);

        if(array[idx] != null && array[idx].equals(value))
        {
            Integer removed = array[idx];

            array[idx] = null;
            
            for(int i = 0; i < eIdx; i++)
            {
                if(hash(array[table + i]) == idx)
                {
                    array[idx] = array[table + i];

                    for(int j = i + 1; j < eIdx; j++)
                    {
                        array[table + j - 1] = array[table + j];
                    }
                    
                    array[table + eIdx - 1] = null;
                    eIdx--;
                    break;
                }
            }
            
            return removed;
        }

        for(int i = 0; i < eIdx; i++)
        {
            if(array[table + i].equals(value))
            {
                Integer removed = array[table + i];
                
                for(int j = i + 1; j < eIdx; j++)
                {
                    array[table + j - 1] = array[table + j];
                }

                array[table + eIdx - 1] = null;
                eIdx--;

                return removed;
            }
        }

        return null;
    }
}
