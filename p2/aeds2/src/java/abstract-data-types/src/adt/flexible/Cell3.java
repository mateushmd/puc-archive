package adt.flexible;

public class Cell3 extends Cell2 {
    int value2;

    public Cell2 m;
    
    public Cell3(int value, int value2)
    {
        super(value);
        this.value2 = value2;
        l = m = r = null;
    }
}
