package adt.flexible;

public class Cell2 extends Cell<Integer> {
    public Cell2 l, r;

    public Cell2(int value)
    {
        super(value);
        l = r = null;
    }
}
