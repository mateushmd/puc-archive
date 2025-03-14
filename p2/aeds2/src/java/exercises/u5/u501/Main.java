import java.util.Random;

class No
{
    public int valor;
    public int qtd;
    public No esq, dir;

    public No(int valor)
    {
        this.valor = valor;
        qtd = 1;
        esq = dir = null;
    }
}

public class Main
{
    private static final int TAMANHO = 10;

    public static void main(String[] args) 
    {
        Random rand = new Random();
        No raiz = null;

        for(int i = 0; i < TAMANHO; i++)
        {   
            int valor = rand.nextInt(TAMANHO / 2);
            raiz = inserir(raiz, valor);
            System.out.print(valor + " ");
        }
        System.out.println();

        printArvore(raiz);
    }

    public static No inserir(No no, int valor)
    {
        if(no == null)
            no = new No(valor);
        else if(valor < no.valor)
            no.esq = inserir(no.esq, valor);
        else if(valor > no.valor)
            no.dir = inserir(no.dir, valor);
        else
            no.qtd++;

        return no;
    }

    public static void printArvore(No no)
    {
        if(no == null)
            return;

        System.out.println(String.format("%d (%d)", no.valor, no.qtd));
        printArvore(no.esq);
        printArvore(no.dir);
    }
}