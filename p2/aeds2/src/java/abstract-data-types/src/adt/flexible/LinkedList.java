package adt.flexible;

import adt.exceptions.EmptyException;

public class LinkedList extends AbstractDataType<Integer>
{
    private class Cell extends adt.flexible.Cell<Integer>
    {
        public Cell next;

        public Cell(Integer value)
        {
            super(value);
            next = null;
        }
    }

    private Cell first;
    private Cell last;

    public LinkedList()
    {
        super();
        first = null;
        last = null;
    }

    @Override
    public void insert(Integer obj) 
    {
        Cell cell = new Cell(obj);
        if(isEmpty()) first = cell;
        else last.next = cell;
        last = cell;
    }

    @Override
    public Integer remove() 
    {
        if(isEmpty())
            throw new EmptyException("Cannot remove from empty list");

        Cell tmp = last;
        if(first == last) first = last = null;
        else
        {
            Cell c;
            for(c = first; c.next != last; c = c.next);
            c.next = null;
            last = c;
        }

        size--;

        return tmp.getValue();
    }

    public Integer removeFirst()
    {
        if(isEmpty())
            throw new EmptyException("Cannot remove from empty list");

        Cell tmp = first;
        first = first.next;
        if(last == tmp) last = first;

        size--;

        return tmp.getValue();
    }

    public Integer removeAt(int index)
    {
        if(isEmpty())
            throw new EmptyException("Cannot remove from empty list");

        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        if(index == 0)
            return removeFirst();

        if(index == size - 1)
            return remove();

        Cell c = first;
        for(int i = 0; i < index - 1; i++, c = c.next);
        Cell tmp = c.next;
        c.next = tmp.next;

        return tmp.getValue();
    }

    public boolean search(Integer value)
    {
        if(isEmpty())
            return false;

        for(Cell c = first; c != null; c = c.next)
        {
            if(c.getValue().equals(value))
                return true;
        }
        
        return false;
    }

    public int firstOf(Integer value)
    {
        if(isEmpty())
            return -1;

        Cell c;
        int i;

        for(i = 0, c = first; c != null; c = c.next, i++)
        {
            if(c.getValue().equals(value))
                return i;
        }

        return -1; 
    }

    @Override
    public void print() 
    {
        Cell c = first;

        for(c = first; c.next != null; c = c.next)
        {
            System.out.print(c.getValue() + " -> ");
        }

        System.out.println(c.getValue());
    }
    
}
