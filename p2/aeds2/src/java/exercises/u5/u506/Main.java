import java.util.Random;

import javax.swing.border.MatteBorder;

class CelulaM
{
    CelulaM sup, inf, esq, dir;
    int valor;
}

class No
{
    No esq, dir;
    int valor;
}

class CelulaL
{
    CelulaL prox;
    int valor;
}

public class Main 
{
    static Random rand = new Random();

    static No inserirArvore(No no, int valor)
    {
        if(no == null)
            no = new No();
        else if(valor < no.valor)
            no.esq = inserirArvore(no.esq, valor);
        else if(valor > no.valor)
            no.dir = inserirArvore(no.dir, valor);

        return no;
    }

    static CelulaM montarMatriz(int t)
    {
        CelulaM inicio = new CelulaM();
        inicio.valor = rand.nextInt(4);

        CelulaM tmp = inicio;

        for(int i = 0; i < t - 1; i++)
        {
            tmp.dir = new CelulaM();
            tmp.dir.esq = tmp;
            tmp = tmp.dir;
            tmp.valor = rand.nextInt(4);
        }

        CelulaM s, o = inicio;

        for(int i = 0; i < t - 1; i++)
        {
            s = o;
            o.inf = new CelulaM();
            o.inf.sup = o;
            o = o.inf;
            o.valor = rand.nextInt();
            tmp = o;

            for(int j = 0; j < t - 1; j++)
            {
                s = s.dir;
                tmp.dir = new CelulaM();
                tmp.dir.esq = tmp;
                tmp = tmp.dir;
                tmp.valor = rand.nextInt(4);
                tmp.sup = s;
                s.inf = tmp;
            }
        }

        return inicio;
    }
}
