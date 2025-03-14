package adt.flexible;

import adt.exceptions.EmptyException;

public class Stack extends AbstractDataType<Integer>
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

    private Cell top;

    public Stack()
    {
        super();
        top = null;
    }

    @Override
    public void insert(Integer obj)
    {
        Cell cell = new Cell(obj);
        cell.next = top;
        top = cell;
        size++;
    }

    @Override
    public Integer remove() 
    {
        if(isEmpty())
            throw new EmptyException("Cannot remove from empty stack");

        Cell tmp = top;
        top = tmp.next;
        size--;

        return tmp.getValue();
    }

    @Override
    public void print()
    {
        System.out.print("[");

        Cell c = top;

        for(c = top; c.next != null; c = c.next)
        {
            System.out.print(c.getValue() + ", ");
        }

        System.out.print(c.getValue());

        System.out.println("]");
    }

    public int sum()
    {
        int sum = 0;

        for(Cell c = top; c != null; c = c.next)
            sum += c.getValue();

        return sum;
    }

    public int recursiveSum()
    {
        return recursiveSum(top);
    }

    private int recursiveSum(Cell c)
    {
        if(c == null)
            return 0;

        int value = c.getValue();

        return value + recursiveSum(c.next);
    }

    public int getBiggestValue()
    {
        if(isEmpty())
            throw new EmptyException("Cannot find biggest value in a empty stack.");

        Cell c = top;
        int value = c.getValue();

        while(c.next != null)
        {
            c = c.next;

            if(c.getValue() > value)
                value = c.getValue();
        }

        return value;
    }

    public int getBiggestValueRecursive()
    {
        if(isEmpty())
            throw new EmptyException("Cannot find biggest value in a empty stack.");

        return getBiggestValueRecursive(top, top.getValue());
    }

    private int getBiggestValueRecursive(Cell c, int value)
    {
        if(c.getValue() > value)
            value = c.getValue();

        if(c.next != null)
            value = getBiggestValueRecursive(c.next, value);

        return value;
    }

    public void printRemoveOrderRecursive()
    {
        if(isEmpty())
            throw new EmptyException("Stack is empty");

        printRemoveOrderRecursive(top);
        
        System.out.println();
    }

    private void printRemoveOrderRecursive(Cell c)
    {
        System.out.print(c.getValue());

        if(c.next != null)
        {
            System.out.print(" -> ");
            printRemoveOrderRecursive(c.next);
        }
    }

    public void printInsertionOrder()
    {
        if(isEmpty())
            throw new EmptyException("Stack is empty");

        int offset = size;

        while(offset > 0)
        {  
            Cell c = top;
            for(int i = 0; i < offset - 1; i++, c = c.next);
            System.out.print(c.getValue());

            if(offset > 1)
                System.out.print(" -> ");

            offset--;
        }

        System.out.println();
    }

    public void printInsertionOrderRecursive()
    {
        if(isEmpty())
            throw new EmptyException("Stack is empty");

        printInsertionOrderRecursive(top);

        System.out.println();
    } 

    private void printInsertionOrderRecursive(Cell c)
    {
        if(c.next != null)
            printInsertionOrderRecursive(c.next);

        System.out.print(c.getValue());

        if(c != top)
            System.out.print(" -> ");
    }
}
