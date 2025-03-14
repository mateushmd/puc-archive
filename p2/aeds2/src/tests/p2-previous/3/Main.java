
public class Main
{
    public static void main(String[] args) throws Exception
    {
        int[] a = new int[]{46, 38, 22, 10};
        int[] b = new int[]{57, 33, 21};

        int[] o = vetorOrdenado(a, b);

        for(int i = 0; i < o.length; i++)
        {
            System.out.println(o[i]);
        }
    }

    static int[] vetorOrdenado(int[] vetA, int vetB[]) throws Exception
    {
        int[] vet = new int[vetA.length + vetB.length];
        
        for(int i = 0; i < vetA.length; vet[i] = vetA[i], i++);
        for(int i = 0; i < vetB.length; vet[i + vetA.length] = vetB[i], i++);
        
        int[] count = new int[getMaior(vet) + 1];
        int[] ordenado = new int[vet.length];
        
        for(int i = 0; i < count.length; count[i] = 0, i++);
        
        for(int i = 0; i < vet.length; count[vet[i]]++, i++);
        
        for(int i = 1; i < count.length; count[i] += count[i - 1], i++);
        
        for(int i = vet.length - 1; i >= 0; ordenado[count[vet[i]] - 1] = vet[i], count[vet[i]]--, i--);
        
        return ordenado;
    }

    static int getMaior(int[] vet) throws Exception
    {
        if(vet.length == 0)
            throw new Exception();
        
        int maior = vet[0];
        
        for(int i = 1; i < vet.length; i++)
        {
            if(vet[i] > maior)
                maior = vet[i];
        }
        
        return maior;
    }
}