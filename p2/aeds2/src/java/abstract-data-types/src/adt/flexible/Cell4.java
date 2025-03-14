package adt.flexible;

public class Cell4 extends Cell2 {
    int value2, value3;

    public Cell2 lm, rm;
    
    public Cell4(int value, int value2, int value3)
    {
        super(value);
        this.value2 = value2;
        this.value3 = value3;
        l = lm = rm = r = null;
    }
}
