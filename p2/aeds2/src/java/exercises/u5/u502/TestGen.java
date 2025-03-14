import java.util.Random;

public class TestGen {
    static final int S = 20;

    public static void main(String[] args) 
    {
        Random random = new Random();
        
        System.out.println(S);

        for(int i = 0; i < S; i++)
        {
            int nsize = random.nextInt(5) + 5;
    
            StringBuilder nameBuilder = new StringBuilder(), cpfBuilder = new StringBuilder();
    
            for(int j = 0; j < nsize; j++)
                nameBuilder.append((char)(random.nextInt(26) + (j == 0 ? 65 : 97)));
    
            for(int j = 0; j < 11; j++)
                cpfBuilder.append(random.nextInt(10));
    
            System.out.println(nameBuilder.toString());
            System.out.println(cpfBuilder.toString());
        }
    }
}
