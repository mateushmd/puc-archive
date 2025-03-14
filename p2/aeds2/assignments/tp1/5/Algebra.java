import java.util.Scanner;
import java.util.Stack;
import java.util.function.BiFunction;

public class Algebra 
{
    // Representa uma operação booleana
    class Operation 
    {
        // Objeto de função anônima que realiza a operação
        private BiFunction<Boolean, Boolean, Boolean> doOperation;
        public int requiredAmmount;

        public Operation(String operator)
        {
            switch (operator) {
                case "and":
                    doOperation = (a, b) -> a && b;
                    break;
                case "or":
                    doOperation = (a, b) -> a || b;
                    break;
                case "not":
                    doOperation = (a, b) -> !a;
                    break;
                default:
                    break;
            }

            requiredAmmount = 1;
        }

        // Realiza a operação entre qualquer quantidade de valores
        public boolean resolve(boolean ...values)
        {
            if(requiredAmmount == 1)
                return doOperation.apply(values[0], null);

            boolean result = values[0];

            for(int i = 1; i < values.length; i ++)
            {
                result = doOperation.apply(result, values[i]);
            }

           return result;
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
            MyIO.println(instance.interpret(inputs, expression) ? 1 : 0);

            inAmmount = MyIO.readInt();
        }
    }

    // Intepreta uma expressão booleana a partir de uma coleção de entradas rotuladas de 'A' a 'Z'
    public boolean interpret(boolean[] inputs, String expr)
    {
        Stack<Operation> operationStack = new Stack<>();
        Stack<Boolean> valueStack = new Stack<>();

        String token = ""; 

        for(int i = 0; i < expr.length(); i++)
        {
            char curChar = expr.charAt(i);
            
            if(curChar == ' ')
                continue;

            if(curChar == '(')
            {
                operationStack.push(new Operation(token));   
                token = "";
            }
            else if(curChar == ')')
            {
                Operation curOperation = operationStack.pop();
                
                int requiredAmmount = curOperation.requiredAmmount;

                boolean[] values = new boolean[requiredAmmount];
                for(int j = 0; j < requiredAmmount; j++)
                {
                    values[j] = valueStack.pop();
                }

                valueStack.push(curOperation.resolve(values));
            }
            else if(curChar == ',')
                operationStack.peek().requiredAmmount++;
            else if(curChar >= 'A' && curChar <= 'Z')
                valueStack.push(inputs[curChar - 'A']);
            else
                token += curChar;
        }

        return valueStack.pop();
    }
}