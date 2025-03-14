class No
{
    public No esq, dir;
    public char letra;
    public NoIn sub;

    public No(char letra)
    {
        this.letra = letra; 
    }
}

class NoIn
{
    public NoIn esq, dir;
    public String str;
    public NoIn(String str)
    {
        this.str = str;
    }
}

public class Main 
{
    public static void main(String[] args) 
    {
        No no = new No('B');
        no.esq = new No('A');
        no.dir = new No('C');
        
        NoIn noIn = new NoIn("BBBBBBBBC");
        noIn.esq = new NoIn("BBBBC");
        noIn.dir = new NoIn("BBBBBBBBC");
        no.sub = noIn;

        noIn = new NoIn("AAAAC");
        noIn.esq = new NoIn("AAC");
        noIn.dir = new NoIn("AC");
        no.esq.sub = noIn;

        noIn = new NoIn("C");
        noIn.esq = new NoIn("CC");
        noIn.dir = new NoIn("CCCCCCCCCC");
        no.dir.sub = noIn;

        System.out.println(hasStringTam10(no));
    }

    static boolean hasStringTam10(No no)
    {
        if(no == null)
            return false;

        return subHasStringTam10(no.sub) || hasStringTam10(no.esq) || hasStringTam10(no.dir);
    }

    static boolean subHasStringTam10(NoIn noIn)
    {
        if(noIn == null) 
            return false;

        return (noIn.str.length() == 10 && noIn.str.charAt(noIn.str.length() - 1) == 'C') || subHasStringTam10(noIn.esq) || subHasStringTam10(noIn.dir);
    }
}
