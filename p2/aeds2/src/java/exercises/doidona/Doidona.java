class Celula
{
    public String palavra;
    public Celula prox;

    public Celula(String palavra)
    {
        this.palavra = palavra;
        prox = null;
    }
}

class CelulaT2
{
    public Celula inicio, fim;

    public void inserir(String palavra)
    {
        Celula celula = new Celula(palavra);

        if(inicio == null)
        {
            inicio = fim = celula;
            return;
        }

        if(palavra.compareTo(inicio.palavra) < 0)
        {
            celula.prox = inicio;
            inicio = celula;
            return;
        }

        for(Celula c = inicio; c != fim; c = c.prox)
        {
            if(palavra.compareTo(c.prox.palavra) < 0)
            {
                celula.prox = c.prox;
                c.prox = celula;
                return;
            }
        }

        fim.prox = celula;
    }

    public boolean pesquisar(String palavra)
    {
        for(Celula c = inicio; c != null; c = c.prox)
            if(c.palavra.equals(palavra)) return true;

        return false;
    }
}

class T2
{
    public CelulaT2 celulast2[];
    
    public T2(int tam)
    {
        celulast2 = new CelulaT2[tam];
        for(int i = 0; i < tam; i++, celulast2[i] = new CelulaT2());
    }

    private int hash(int x)
    {
        return x % celulast2.length;
    }

    public void inserir(String palavra)
    {
        int idx = hash(palavra.length());

        celulast2[idx].inserir(palavra);
    }

    public boolean pesquisar(String palavra)
    {
        int idx = hash(palavra.length());

        return celulast2[idx].pesquisar(palavra);
    }
}

class T1
{
    public String palavras[];
    public T2 t2;

    public T1(int tam)
    {
        palavras = new String[tam];
        t2 = new T2(10);
    }

    private int hash(char x)
    {
        return x % palavras.length;
    }

    private int rehash(char x)
    {
        return ++x % palavras.length;
    }

    public void inserir(String palavra)
    {
        int idx = hash(palavra.charAt(palavra.length() - 1));

        if(palavras[idx] != null)
        {
            idx = rehash(palavra.charAt(palavra.length() - 1));

            if(palavras[idx] != null)
            {
                t2.inserir(palavra);
                return;
            }
        }

        palavras[idx] = palavra;
    }

    public boolean pesquisar(String palavra)
    {
        int idx = hash(palavra.charAt(palavra.length() - 1));

        if(palavras[idx] != null && palavras[idx].equals(palavra))
            return true;

        idx = rehash(palavra.charAt(palavra.length() - 1));

        if(palavras[idx] != null && palavras[idx].equals(palavra))
            return true;

        return t2.pesquisar(palavra);
    }
}

class No
{
    public char caractere;
    public T1 t1;
    public No esq, dir;

    public No(char caractere)
    {
        this.caractere = caractere;
        t1 = new T1(10);
        esq = dir = null;
    }

    public boolean pesquisar(String palavra)
    {
        return t1.pesquisar(palavra);
    }
}

public class Doidona
{
    private No raiz;

    public void inserirNo(char caractere)
    {
        raiz = inserirNo(caractere, raiz);
    }

    private No inserirNo(char caractere, No no)
    {
        if(no == null)
            no = new No(caractere);
        else if(caractere < no.caractere)
            no.esq = inserirNo(caractere, no);
        else if(caractere > no.caractere)
            no.dir = inserirNo(caractere, no);

        return no;
    }

    public boolean pesquisar(String palavra)
    {
        return pesquisar(palavra, raiz);
    }

    private boolean pesquisar(String palavra, No no)
    {
        if(no == null)
            return false;

        char caractere = palavra.charAt(0);

        if(caractere < no.caractere)
            return pesquisar(palavra, no.esq);
        if(caractere > no.caractere)
            return pesquisar(palavra, no.dir);
        
        return no.pesquisar(palavra);
    }
}