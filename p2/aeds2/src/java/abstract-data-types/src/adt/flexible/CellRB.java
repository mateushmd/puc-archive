package adt.flexible;

public class CellRB extends Cell<Integer> {
    public CellRB l, r;
    public boolean color;

    public CellRB(int value, boolean color)
    {
        super(value);
        this.color = color;
        l = r = null;
    }

    public CellRB(int value)
    {
        this(value, false);
    }
}
