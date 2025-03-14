package adt.flexible;

public class Trie
{
    private CellTrieH root;

    public Trie()
    {
        root = new CellTrieH();
    }

    public void insert(String str) {
        insert(str, root, 0);
    }

    private void insert(String str, CellTrieH cell, int i)
    {
        if(i == str.length())
            return;
        
        if(cell.children[str.charAt(i)] == null)
            cell.children[str.charAt(i)] = new CellTrieH(str.charAt(i));

        insert(str, cell.children[str.charAt(i)], i + 1);
    }

    public boolean search(String str)
    {
        return search(str, root, 0); 
    }

    private boolean search(String str, CellTrieH cell, int i)
    {
        if(cell == null)
            return false;
        else if(i == str.length())
            return true;

        return search(str, cell.children[str.charAt(i)], i + 1);
    }

    public void print() {
        throw new UnsupportedOperationException("Unimplemented method 'print'");
    }
    
}
