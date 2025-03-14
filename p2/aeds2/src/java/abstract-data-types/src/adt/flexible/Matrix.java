package adt.flexible;

import java.util.List;
import java.util.ArrayList;

public class Matrix extends AbstractDataType<Integer> 
{
    private class Cell extends adt.flexible.Cell<Integer>
    {
        public Cell left;
        public Cell right;
        public Cell sup;
        public Cell inf;

        public Cell()
        {
            this(0);
        }

        public Cell(Integer value)
        {
            super(value);
 
            left = null;
            right = null;
            sup = null;
            inf = null;
        }
    }

    Cell start;
    int lines, columns;

    public Matrix(int lines, int columns)
    {
        super();
        
        if(lines <= 0 || columns <= 0)
            throw new IllegalArgumentException("The matrix must have a " +
                "positive ammount of lines and columns");

        this.lines = lines;
        this.columns = columns;

        Cell cell = new Cell();
        start = cell;

        for(int i = 1; i < columns; i++)
        {
            Cell c = new Cell();
            c.left = cell;
            cell.right = c;
            cell = c;
        }

        Cell above, first;
        first = cell = start;

        for(int i = 1; i < lines; i++)
        {
            Cell c = new Cell();
            c.sup = cell;
            cell.inf = c;
            above = cell;
            cell = c;

            for(int j = 1; j < columns; j++)
            {
                above = above.right;
                c = new Cell();
                c.left = cell;
                c.sup = above;
                above.inf = c;
                cell.right = c;
                cell = c;
            }

            cell = first = first.inf;
        }
    }

    public void setValue(int line, int column, Integer value)
    {
        if(line >= lines || line < 0)
            throw new IndexOutOfBoundsException("Invalid line number");
        
        if(column >= columns || column < 0)
            throw new IndexOutOfBoundsException("Invalid column number");

        Cell c = start;

        for(int i = 0; i < column; i++, c = c.right);
        for(int i = 0; i < line; i++, c = c.inf);

        c.setValue(value);
    }

    @Override
    public void insert(Integer obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public Integer remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void print() 
    {
        Cell c;

        for(c = start; c != null; c = c.inf)
        {
            Cell c2;

            for(c2 = c; c2.right != null; c2 = c2.right)
            {
                System.out.print(c2.getValue() + " ");
            }

            System.out.println(c2.getValue());
        }
    }

    public int[] getMainDiagonal()
    {
        int size = lines < columns ? lines : columns;
        int[] values = new int[size];

        Cell c = start;
        int i = 0;

        for(c = start; c.right != null; values[i++] = c.getValue(), c = c.right.inf);
    
        values[i] = c.getValue();

        return values;
    }

    public List<Integer> getMainDiagonalAsList()
    {
        List<Integer> list = new ArrayList<>();

        for(int n : getMainDiagonal())
        {
            list.add(n);
        }

        return list;
    }

    public int[] getSecondaryDiagonal()
    {
        int size = lines < columns ? lines : columns;
        int[] values = new int[size];

        Cell c;
        int i = 0;

        for(c = start; c.right != null; c = c.right);
        for(; c.left != null; values[i++] = c.getValue(), c = c.left.inf);

        values[i] = c.getValue();

        return values;
    }

    public static Matrix sum(Matrix a, Matrix b)
    {
        if(a.lines != b.lines || a.columns != b.columns)
            throw new IllegalArgumentException("The matrices must be of the " +
                "same size.");

        int lines = a.lines, columns = a.columns;

        Matrix c = new Matrix(lines, columns);

        Cell cA = a.start;  
        Cell cB = b.start;
        Cell cC = c.start;

        for(int i = 0; i < lines; i++)
        {
            for(int j = 0; j < i; j++, cA = cA.inf, cB = cB.inf, cC = cC.inf);

            for(int j = 0; j < columns; j++, cA = cA.right, cB = cB.right, cC = cC.right)
            {
                cC.setValue(cA.getValue() + cB.getValue());
            }

            cA = a.start;
            cB = b.start;
            cC = c.start;
        }
        
        return c;
    }

    public static Matrix multiply(Matrix a, Matrix b)
    {
        if(a.columns != b.lines)
            throw new IllegalArgumentException("The matrices sizes does not " + 
                "meet the matrix multiplication conditions");

        Matrix c = new Matrix(a.lines, b.columns);

        Cell cellLeft = c.start;

        for(int i = 0; cellLeft != null; cellLeft = cellLeft.inf, i++)  
        {
            Cell cell;

            for(cell = cellLeft; cell != null; cell = cell.right)
            {
                int result = 0;

                Cell aCellLeft = a.start;
                Cell bCellTop = b.start;

                for(int k = 0; k < i; k++, aCellLeft = aCellLeft.inf, bCellTop = bCellTop.right);
                
                for(int k = 0; k < a.columns; k++, aCellLeft = aCellLeft.right, bCellTop = bCellTop.inf)
                {
                    result += aCellLeft.getValue() * bCellTop.getValue();
                }

                cell.setValue(result);
            }
        }

        return c;
    }

    public void printRelations()
    {
        Cell c;

        for(c = start; c != null; c = c.inf)
        {
            Cell c2;

            for(c2 = c; c2 != null; c2 = c2.right)
            {
                System.out.println(c2.getValue());
                System.out.println(String.format("\tsup: %s", c2.sup != null ? c2.sup.getValue().toString() : "NULL"));
                System.out.println(String.format("\tleft: %s", c2.left != null ? c2.left.getValue().toString() : "NULL"));
                System.out.println(String.format("\tinf: %s", c2.inf != null ? c2.inf.getValue().toString() : "NULL"));
                System.out.println(String.format("\tright: %s", c2.right != null ? c2.right.getValue().toString() : "NULL"));
            }
        }
    }
}