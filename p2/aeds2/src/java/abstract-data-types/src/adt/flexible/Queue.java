package adt.flexible;

import adt.exceptions.EmptyException;

public class Queue extends AbstractDataType<Integer>
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

    public Queue()
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
        size++;
    }

    @Override
    public Integer remove()
    {
        if(isEmpty())
            throw new EmptyException("Cannot remove from empty queue");

        Cell tmp = first;
        first = first.next;
        if(last == tmp) last = first;
        size--;

        return tmp.getValue();
    }

    @Override
    public void print()
    {
        Cell c;
        for(c = first; c.next != null; c = c.next)
        {
            System.out.print(c.getValue() + " -> ");
        }
        System.out.println(c.getValue());
    }
}