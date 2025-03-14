package adt.flexible;

import adt.exceptions.CellNotFoundException;

public class BinaryTree extends AbstractDataType<Integer>
{
    Cell2 root;

    public BinaryTree()
    {
        super();
        
        root = null;
    }

    @Override
    public void insert(Integer obj) 
    {    
        root = insert(obj, root);
    }

    protected Cell2 insert(Integer obj, Cell2 c)
    {
        if(c == null)
            c = new Cell2(obj);
        else if(obj < c.getValue())
            c.l = insert(obj, c.l);
        else if(obj > c.getValue())
            c.r = insert(obj, c.r);
        else
            throw new IllegalArgumentException("The tree does not accept duplicates");
        return c;
    }

    @Override
    public Integer remove()
    {   
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public Integer remove(Integer obj)
    {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public Integer remove(Integer obj, Cell2 c, Cell2 parent)
    {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public void doRemove(Cell2 c, Cell2 parent)
    {
        throw new UnsupportedOperationException("Unimplemented method 'doRemove'");
    }

    public boolean search(Integer obj)
    {
        return search(obj, root);
    }

    private boolean search(Integer obj, Cell2 c)
    {
        if(c == null)
            return false;
        else if(c.getValue() == obj)
            return true;
        else if(c.getValue() > obj)
            return search(obj, c.l);
        else
            return search(obj, c.r);
    }

    public int sum()
    {
        return sum(root);
    }

    private int sum(Cell2 c)
    {
        int sum = 0;

        if(c != null)
            return c.getValue() + sum(c.l) + sum(c.r);

        return sum;
    }

    public int getHeight()
    {
        return getHeight(0, root);
    }

    private int getHeight(int height, Cell2 c)
    {
        if(c == null)
            return height;

        height++;
        int l = getHeight(height, c.l);
        int r = getHeight(height, c.r);

        return Math.max(l, r);
    }

    public int getHeightOf(int value)
    {
        return getHeightOf(value, 0, root);
    }

    private int getHeightOf(int value, int height, Cell2 c)
    {
        if(c == null)
            throw new CellNotFoundException("Could not find value in tree");

        height++;

        if(c.getValue() == value)
            return height;
        else if(value < c.getValue())
            return getHeightOf(value, height, c.l);
        else
            return getHeightOf(value, height, c.r);
    }

    public int count()
    {
        return count(root); 
    }

    private int count(Cell2 c)
    {
        if(c == null)
            return 0;

        int count = 1;
        count += count(c.l);
        count += count(c.r);

        return count;
    }

    public int countEven()
    {
        return countEven(root);
    }

    private int countEven(Cell2 c)
    {   
        int counter = 0;

        if(c != null)
        {
            counter = c.getValue() % 2 == 0 ? 1 : 0;
            counter += countEven(c.l) + countEven(c.r);
        }

        return counter;
    }

    public Integer[] toArray()
    {
        Integer[] array = new Integer[(int)(Math.pow(2, getHeight()))];
        buildArray(array, 0, root);
        return array;
    }

    private void buildArray(Integer[] array, int i, Cell2 c)
    {
        if(c == null)
        {
            return;
        }

        array[i] = c.getValue();
        buildArray(array, i * 2 + 1, c.l);
        buildArray(array, i * 2 + 2, c.r);
    }

    @Override
    public void print() 
    {    
        
    }
}