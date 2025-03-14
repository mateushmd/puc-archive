package adt.flexible;

import adt.exceptions.EmptyException;

public class DoublyLinkedList extends AbstractDataType<Integer>
{
    private class Cell extends adt.flexible.Cell<Integer>
    {
        public Cell before; 
        public Cell next;

        public Cell(Integer value)
        {
            super(value);
            before = null;
            next = null;
        }
    }

    private Cell first;
    private Cell last;

    public DoublyLinkedList()
    {
        super();
        first = null;
        last = null;
    }

    @Override
    public void insert(Integer obj) 
    {
        Cell cell = new Cell(obj);
        if(isEmpty()) first = last = cell;
        else 
        {
            last.next = cell;
            cell.before = last;
            last = cell;
        }
    }

    public void insertFirst(Integer obj)
    {
        if(isEmpty())
        {
            insert(obj);
            return;
        }

        Cell cell = new Cell(obj);
        first.before = cell;
        cell.next = first;
        first = cell;
    }

    public void insertAt(Integer obj, int index)
    {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        if(index == 0)
        {
            insertFirst(obj);
            return;
        }

        if(index == size - 1)
        {
            insert(obj);
            return;
        }

        Cell c = first;

        for(int i = 0; i < index - 1; i++, c = c.next);

        Cell cell = new Cell(obj);
        c.next.before = cell;
        c.next = cell;
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
        first.before = null;
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
        c.next.before = c;

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
    
    public void printInverted()
    {
        Cell c;

        System.out.print("[");

        for(c = last; c.before != null; c = c.before)
        {
            System.out.print(c.getValue() + ", ");
        }

        System.out.println(c.getValue());
    }
}
