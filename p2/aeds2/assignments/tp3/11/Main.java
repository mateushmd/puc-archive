import java.util.List;
import java.util.ArrayList;

class Matrix
{
    private class Cell
    {
        public Cell left;
        public Cell right;
        public Cell sup;
        public Cell inf;
        public int value;

        public Cell()
        {
            this(0);
        }

        public Cell(int value)
        {
            this.value = value;
 
            left = null;
            right = null;
            sup = null;
            inf = null;
        }
    }

    Cell start;
    int lines, columns;

    // Cria todas as relações das celulas seguindo os tamanhos fornecidos
    public Matrix(int lines, int columns)
    {
        super();

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

    // Define o valor de uma célula específica
    public void setValue(int line, int column, int value)
    {   
        Cell c = start;

        for(int i = 0; i < column; i++, c = c.right);
        for(int i = 0; i < line; i++, c = c.inf);

        c.value = value;
    }

    // Imprime a matriz
    public void print() 
    {
        Cell c;

        for(c = start; c != null; c = c.inf)
        {
            Cell c2;

            for(c2 = c; c2.right != null; c2 = c2.right)
            {
                MyIO.print(c2.value + " ");
            }

            MyIO.println(c2.value + " ");
        }
    }

    // Imprime a diagonal principal
    public void mainDiagonal()
    {
        Cell c = start;

        for(c = start; c.right != null; c = c.right.inf)
        {
            MyIO.print(c.value + " ");
        }
    
        MyIO.println(c.value + " ");
    }

    // Imprime a diagonal secundária
    public void secondaryDiagonal()
    {
        Cell c;

        for(c = start; c.right != null; c = c.right);
        for(; c.left != null; c = c.left.inf)
        {
            MyIO.print(c.value + " ");
        }

        MyIO.println(c.value + " ");
    }

    // Retorna uma nova matriz como resultado da soma das matrizes fornecidas
    public static Matrix sum(Matrix a, Matrix b)
    {
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
                cC.value = cA.value + cB.value;
            }

            cA = a.start;
            cB = b.start;
            cC = c.start;
        }
        
        return c;
    }

    // Retorna uma nova matriz como resultado da multiplicação das matrizes fornecidas
    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.lines, b.columns);
        
        // Ponteiros para os inícios das linhas das matrizes a e c
        Cell lA = a.start;
        Cell lC = c.start;
    
        for (int i = 0; i < a.lines; i++) {
            // Ponteiro para o início das colunas da matriz b
            Cell uB = b.start;

            Cell currentC = lC;
    
            for (int j = 0; j < b.columns; j++) {
                int result = 0;
    
                Cell currentA = lA;
                Cell currentB = uB;
    
                for (int k = 0; k < a.columns; k++) {
                    result += currentA.value * currentB.value;
                    currentA = currentA.right;
                    currentB = currentB.inf;
                }
    
                currentC.value = result;
                currentC = currentC.right;
                uB = uB.right;
            }
    
            lA = lA.inf;
            lC = lC.inf;
        }
    
        return c;
    }   
}

public class Main 
{
    public static void main(String[] args) 
    {
        int n = MyIO.readInt();

        for(int i = 0; i < n; i++)
        {
            int c1 = MyIO.readInt();
            int l1 = MyIO.readInt();

            Matrix m1 = new Matrix(c1, l1);

            for(int j = 0; j < c1; j++)
            {
                for(int k = 0; k < l1; k++)
                {
                    int v = MyIO.readInt();
                    m1.setValue(j, k, v);
                }
            }

            int c2 = MyIO.readInt();
            int l2 = MyIO.readInt();

            Matrix m2 = new Matrix(c2, l2);

            for(int j = 0; j < c1; j++)
            {
                for(int k = 0; k < l1; k++)
                {
                    int v = MyIO.readInt();
                    m2.setValue(j, k, v);
                }
            }

            m1.mainDiagonal();
            m1.secondaryDiagonal();
            Matrix.sum(m1, m2).print();
            Matrix.multiply(m1, m2).print();
        }
    }
}