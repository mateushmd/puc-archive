package adt.flexible;

public class CellTrieH {
    private static final int SIZE = 255;

    public char value;
    public CellTrieH[] children;

    public CellTrieH(char value)
    {
        this.value = value;
        children = new CellTrieH[SIZE];
    }

    public CellTrieH()
    {
        this('\0');
    }
}
