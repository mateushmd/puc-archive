import java.util.Random;

class CelulaM
{
    CelulaM sup, inf, esq, dir;
    int valor;

    public CelulaM(int valor) { this.valor = valor; }
}

class No
{
    No esq, dir;
    int valor;

    public No(int valor) { this.valor = valor; }
}

class CelulaL
{
    CelulaL prox;
    int valor;
}

public class Main 
{
    static Random random = new Random();

    public static void main(String[] args)
    {
        CelulaM matriz = montarMatriz(3);
        No arvore = null;
        arvore = montarArvore(arvore, 1);
        arvore = montarArvore(arvore, 0);
        arvore = montarArvore(arvore, 2);

        CelulaL lista = montarLista(arvore, matriz);
        for(CelulaL c = lista.prox; c != null; c = c.prox)
            System.out.print(c.valor + " ");
    }

    static CelulaL montarLista(No arvore, CelulaM matriz)
    {
        CelulaL inicio = new CelulaL();
        CelulaL tmp = inicio;

        for(CelulaM o = matriz; o != null; o = o.inf)
        {
            for(CelulaM c = o; c != null; c = c.dir)
            {
                if(temRepetidoArvoreMatriz(arvore, c.valor) && !temRepetidoLista(inicio, c.valor))
                {
                    tmp.prox = new CelulaL();
                    tmp = tmp.prox;
                    tmp.valor = c.valor;
                }
            }
        }

        return inicio;
    }

    static boolean temRepetidoLista(CelulaL lista, int valor)
    {
        for(CelulaL c = lista.prox; c != null; c = c.prox)
            if(c.valor == valor) return true;
        
        return false;
    }

    static boolean temRepetidoArvoreMatriz(No no, int valor)
    {
        if(no == null)
            return false;

        return no.valor == valor || temRepetidoArvoreMatriz(no.esq, valor) || temRepetidoArvoreMatriz(no.dir, valor);
    }

    static No montarArvore(No no, int valor)
    {
        if(no == null)
            no = new No(valor);
        else if(valor < no.valor)
            no.esq = montarArvore(no.esq, valor);
        else if(valor > no.valor)
            no.dir = montarArvore(no.dir, valor);

        return no;
    }

    static CelulaM montarMatriz(int t)
    {
        CelulaM inicio, tmp;
        tmp = inicio = new CelulaM(random.nextInt(4));
        
        for(int i = 1; i < t; i++)
        {
            tmp.dir = new CelulaM(random.nextInt(4));
            tmp.dir.esq = tmp;
            tmp = tmp.dir;
        }

        CelulaM a, s, o = inicio;

        for(int i = 1; i < t; i++)
        {
            s = o;
            o.inf = new CelulaM(random.nextInt(4));
            o.inf.sup = o;
            o = o.inf;
            a = o;

            for(int j = 1; j < t; j++)
            {
                s = s.dir;
                a.dir = new CelulaM(random.nextInt(4));
                a.dir.esq = a;
                a = a.dir;
                a.sup = s;
                s.inf = a;
            }
        }

        return inicio;
    }
}
