package adt;

import adt.flexible.*;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    static final int ITERATIONS = 10;
    public static void main(String[] args) 
    {
        for(int i = 0; i < ITERATIONS; i++)
        {   
            Integer[] vals = generateValues(20000, false);

            BinaryTree tree = new BinaryTree();
            
            for(Integer val : vals)
                tree.insert(val);

            System.out.println(tree.getHeight());
        }

        for(int i = 0; i < ITERATIONS; i++)
        {   
            Integer[] vals = generateValues(20000, false);

            AVL tree = new AVL();
            
            for(Integer val : vals)
                tree.insert(val);

            System.out.println(tree.getHeight());
        }
    } 
    
    static Integer[] generateValues(int size, boolean shuffle)
    {
        ArrayList<Integer> list = new ArrayList<>();
    
        for(int i = 0; i < size; i++)
        {
            list.add(i);
        }
        
        if(shuffle)
            Collections.shuffle(list);

        Integer[] arr = new Integer[list.size()];
        list.toArray(arr);

        return arr;
    }
}