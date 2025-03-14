package sorting.lib;

public class TreeSort extends Sorting 
{
    private static class Node
    {
        public int value;
        public Node l, r;
    }

    public static void sort(int[] arr)
    {
        Node root = new Node();
        root.value = arr[0];

        for(int i = 1; i < arr.length; i++)
            insert(root, arr[i]);
        
        int[] sorted = treeToArray(root, arr.length);

        for(int i = 0; i < arr.length; arr[i] = sorted[i], i++);
    }

    private static Node insert(Node node, int value)
    {
        if(node == null)
        {
            node = new Node();
            node.value = value;
        }
        else if(value < node.value)
            node.l = insert(node.l, value);
        else
            node.r = insert(node.r, value);

        return node;
    }

    private static int[] treeToArray(Node root, int size)
    {
        int[] arr = new int[size];
        treeToArray(arr, root, 0);
        return arr;
    }

    private static int treeToArray(int[] arr, Node node, int i)
    {
        if(node == null) 
            return i;

        i = treeToArray(arr, node.l, i);
        arr[i++] = node.value;
        i = treeToArray(arr, node.r, i);
        return i;
    }
}
