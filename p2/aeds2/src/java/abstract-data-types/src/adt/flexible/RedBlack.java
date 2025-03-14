package adt.flexible;

public class RedBlack extends AbstractDataType<Integer> 
{
    CellRB root;

    public RedBlack()
    {
        super();
        root = null;
    }

    @Override
    public void insert(Integer obj)
    {

    }

    @Override
    public Integer remove()
    {
        throw new UnsupportedOperationException();
    }

    private boolean isTypeFour(CellRB cell)
    {
        return cell.l != null && cell.r != null && cell.l.color && cell.r.color;
    }

    private void fragment(CellRB cell)
    {
        cell.l.color = cell.r.color = false;
        cell.color = true;
    }

    @Override
    public void print()
    {

    }
}
