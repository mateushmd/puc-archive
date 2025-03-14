import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BiFunction;

public class Algebra 
{
    // Uma classe wrapper para fazer a passagem de um int como referência
    // Auxilia com a manipulação do offset da String da expressão booleana
    static class MutableInteger
    {
        public int value;

        public MutableInteger()
        {
            value = 0;
        }
    }

    public static void main(String[] args)
    {
        Algebra instance = new Algebra();

        int inAmmount = MyIO.readInt();

        while(inAmmount != 0)
        {
            boolean[] inputs = new boolean[inAmmount];
            for(int i = 0; i < inAmmount; i++)
            {
                inputs[i] = MyIO.readInt() == 1 ? true : false;
            }

            String expression = MyIO.readLine();
            MyIO.println(parse(expression, inputs) ? "1" : "0");

            inAmmount = MyIO.readInt();
        }
    }

    // Intepreta uma expressão booleana a partir de uma coleção de entradas rotuladas de 'A' a 'Z'
    public static boolean parse(String expression, boolean[] inputs)
    {
        return parse(expression, inputs, new MutableInteger());
    }

    // Intepreta uma expressão booleana a partir de uma coleção de entradas rotuladas de 'A' a 'Z' recursivamente
    public static boolean parse(String expression, boolean[] inputs, MutableInteger offset)
    {
        char c;
        String token = "";
        BiFunction<Boolean, Boolean, Boolean> operation = null;
        boolean foundOperator = false;
        ArrayList<Boolean> values = new ArrayList<>();

        while(offset.value < expression.length())
        {
            c = expression.charAt(offset.value);

            if(c == '(')
            {
                foundOperator = true;

                switch (token) {
                    case "and":
                        operation = (a, b) -> a && b;
                        break;
                    case "or":
                        operation = (a, b) -> a || b;
                        break;
                    case "not":
                        operation = (a, b) -> !a;
                        break;
                }
            }
            else if(c == ')')
            {
                boolean result = values.get(0);

                if(isEqual(token, "not"))
                    return operation.apply(result, null);

                for(int i = 1; i < values.size(); i++)
                {
                    result = operation.apply(result, values.get(i));
                }

                return result;
            }
            else if(c >= 'A' && c <= 'Z')
            {
                values.add(inputs[c - 'A']);
            }
            else if(c >= 'a' && c <= 'z')
            {
                if(!foundOperator)
                    token += c;
                else
                    values.add(parse(expression, inputs, offset));
            }

            offset.value++;
        }

        return true;
    }

    public static boolean isEqual(String str1, String str2)
    {
        for(int i = 0; i < str1.length(); i++)
        {
            if(i >= str2.length())
                return false;

            if(str1.charAt(i) != str2.charAt(i))
                return false;
        }

        return true;
    }
}