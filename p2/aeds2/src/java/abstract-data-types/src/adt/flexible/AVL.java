package adt.flexible;

public class AVL extends BinaryTree 
{
    public AVL()
    {
        super();
    }    

    @Override
    protected Cell2 insert(Integer obj, Cell2 c)
    {
        c = super.insert(obj, c);
        return balance(c);
    }

    private Cell2 balance(Cell2 c)
    {
        if(c == null)
            return null;

        int diff = getLevel(c.r) - getLevel(c.l);

        if(diff == 2)
        {
            int cDiff = getLevel(c.r.r) - getLevel(c.r.l);

            if(cDiff == -1)
                c.r = rotateR(c.r);

            c = rotateL(c);
        }
        else if(diff == -2)
        {
            int cDiff = getLevel(c.l.r) - getLevel(c.l.l);

            if(cDiff == 1)
                c.l = rotateL(c.l);

            c = rotateR(c);
        }

        return c;
    }

    private int getLevel(Cell2 c)
    {
        if(c == null)
            return 0;
        
        return 1 + Math.max(getLevel(c.l), getLevel(c.r));
    }

    private Cell2 rotateR(Cell2 c)
    {
        Cell2 cL = c.l;
        Cell2 cLR = cl.r;

        c.l = cLR;
        cL.r = c;

        return cL;
    }

    private Cell2 rotateL(Cell2 c)
    {
        Cell2 cR = c.r;
        Cell2 cRL = cR.l;

        c.r = cRL;
        cR.r = c;

        return cR;
    }
}
