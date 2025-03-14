class CelulaM
{
    CelulaM esq, dir, sup, inf;
    int valor;

    public CelulaM(int valor)
    {
        this.valor = valor;
    }
}

class CelulaL
{
    CelulaL prox, ant;
    int valor;
}

class CelulaLL
{
    CelulaLL prox, ant;
    CelulaL celula;
}

class No
{
    No esq, dir;
    int valor;
}

public class Main 
{
    public static void main(String[] args) 
    {
        CelulaM matriz = montarMatriz(3);
        CelulaLL listaLista = montarListaLista(matriz);
        No arvore = montarArvore(listaLista);
        printArvore(arvore);
    }

    static CelulaM montarMatriz(int t)
    {
        int n = 0; 
        CelulaM o = new CelulaM(n++);
        CelulaM a = o, i = o, s = null;

        for(int ii = 0; ii < t - 1; ii++)
        {
            a.dir = new CelulaM(n++);
            a.dir.esq = a;
            a = a.dir;
        }

        for(int ii = 0; ii < t - 1; ii++)
        {
            s = i;
            i.inf = new CelulaM(n++);
            i.inf.sup = i;
            a = i = i.inf;

            for(int ij = 0; ij < t - 1; ij++)
            {
                s = s.dir;
                a.dir = new CelulaM(n++);
                a.dir.esq = a;
                a = a.dir;
                s.inf = a;
                a.sup = s;
            }
        }

        return o;
    }

    static void printArvore(No no)
    {
        if(no == null)
            return;

        System.out.print(" " + no.valor + " ");
        printArvore(no.esq);
        printArvore(no.dir);
    }

    static No montarArvore(CelulaLL listaLista)
    {
        No raiz = null;
        
        for(;;)
        {
            boolean f = false;

            for(CelulaLL c = listaLista; c != null; c = c.prox)
            {
                if(c.celula == null)
                {
                    f = true;
                    continue;
                }

                CelulaL cc;

                for(cc = c.celula; cc.prox != null; cc = cc.prox);

                raiz = inserir(raiz, cc.valor);

                if(cc.ant != null)
                    cc.ant.prox = null;
                else
                    c.celula = null;
            }

            if(f)
                break;
        }

        return raiz;
    }

    static No inserir(No no, int valor)
    {
        if(no == null)
        {
            no = new No();
            no.valor = valor;
        }
        else if(valor < no.valor)
            no.esq = inserir(no.esq, valor);
        else if(valor > no.valor)
            no.dir = inserir(no.dir, valor);
        else if(valor == no.valor)
            throw new RuntimeException("DUPLICADOS");

        return no;
    }

    static CelulaLL montarListaLista(CelulaM matriz)
    {
        CelulaM aM = matriz;

        CelulaLL inicio = new CelulaLL();
        inicio.celula = montarLista(aM);

        CelulaLL aL = inicio;

        for(aM = matriz.inf; aM != null; aM = aM.inf)
        {   
            aL.prox = new CelulaLL();
            aL.prox.celula = montarLista(aM);
            aL.prox.ant = aL;
            aL = aL.prox;
        }

        return inicio;
    }

    static CelulaL montarLista(CelulaM matriz)
    {
        CelulaL inicio = new CelulaL();
        inicio.valor = matriz.valor;

        CelulaL a = inicio;

        for(matriz = matriz.dir; matriz != null; matriz = matriz.dir)
        {
            a.prox = new CelulaL();
            a.prox.valor = matriz.valor;
            a.prox.ant = a;
            a = a.prox;
        }

        return inicio;
    }
}
