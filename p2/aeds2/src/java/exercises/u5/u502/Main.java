import java.util.Scanner;

class No
{
    No esq, dir;
    Celula inicio, fim;
    char letra;

    public No(char letra)
    {
        this.letra = letra;
        esq = dir = null; 
        inicio = fim = null;
    }
}

class Celula
{
    Celula prox;
    Contato contato;

    public Celula(Contato contato)
    {
        this.contato = contato;
        prox = null; 
    }
}

class Contato
{
    String nome;
    String cpf;

    public Contato(String nome, String cpf)
    {
        this.nome = nome;
        this.cpf = cpf;
    }

    @Override
    public String toString()
    {
        return String.format("[nome: %s, cpf: %s]", nome, cpf);
    }
}

public class Main 
{
    public static void main(String[] args) 
    {   
        Scanner in = new Scanner(System.in);
        
        char[] letras = new char[]{'M', 'L', 'N', 'K', 'O', 'J', 'P', 'I', 'Q', 
            'H', 'R', 'G', 'S', 'F', 'T', 'E', 'U', 'D', 'V', 'C', 'W', 'B', 'X', 
            'A', 'Y', 'Z'};

        No raiz = null;

        for(int i = 0; i < letras.length; i++)
            raiz = inserir(raiz, letras[i]);
    
        int n = in.nextInt();

        for(int i = 0; i < n; i++)
        {
            String nome = in.next();
            String cpf = in.next();
            inserir(new Contato(nome, cpf), raiz);
        }

        Contato contato = buscarPorNome("Etrtaphr", raiz);

        System.out.println(contato == null ? "erro" : contato.toString());

        in.close();
    }   
    
    static No inserir(No no, char letra)
    {
        if(no == null)
        {
            no = new No(letra);
            no.fim = no.inicio = new Celula(null);
        }
        else if(letra < no.letra)
            no.esq = inserir(no.esq, letra);
        else if(letra > no.letra)
            no.dir = inserir(no.dir, letra);
        
        return no;
    }

    static void inserir(Contato contato, No no)
    {
        if(no == null)
            return;
        
        char inicial = contato.nome.charAt(0);

        if(inicial < no.letra)
            inserir(contato, no.esq);
        else if(inicial > no.letra)
            inserir(contato, no.dir);
        else
            no.fim = no.fim.prox = new Celula(contato);
    }

    static Contato buscarPorNome(String nome, No no)
    {
        if(no == null)
            return null;
        
        char inicial = nome.charAt(0);

        if(inicial < no.letra)
            return buscarPorNome(nome, no.esq);
        else if(inicial > no.letra)
            return buscarPorNome(nome, no.dir);
        else
        {
            Contato contato = null;
            
            for(Celula c = no.inicio.prox; c != null; c = c.prox)
            {
                if(c.contato.nome.equals(nome))
                {
                    contato = c.contato;
                    break;
                }
            }

            return contato;
        }
    }

    static Contato buscarPorCPF(String cpf, No no)
    {
        if(no == null) 
            return null;

            
        for(Celula c = no.inicio.prox; c != null; c = c.prox)
        {
            if(c.contato.cpf.equals(cpf))
            return c.contato;
        }
            
        Contato contato;

        contato = buscarPorCPF(cpf, no.esq);
        
        if(contato == null)
            contato = buscarPorCPF(cpf, no.dir);

        return contato;
    }
}
