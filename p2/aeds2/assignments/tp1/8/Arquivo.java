import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class Arquivo 
{
    public static void main(String[] args) 
    {
        MyIO.setCharset("UTF-8");

        int n = MyIO.readInt();
        
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("maiaiou.txt"));

            for(int i = 0; i < n; i++)
            {
                String line = MyIO.readLine();
    
                line = fixLine(line);

                writer.append(line);
                writer.newLine();
            }

            writer.close();

            RandomAccessFile raf = new RandomAccessFile("maiaiou.txt", "r");

            long pointer = raf.length();

            // Reposiciona o ponteiro do objeto RandomAccessFile até encontrar uma quebra de linha ou o início do arquivo
            // Chama a função de imprimir caso encontre um dos dois
            while(pointer > 0)
            {
                pointer--;
                raf.seek(pointer);

                if(raf.readByte() == '\n' && pointer != raf.length() - 1)
                    printFloat(raf);
                else if(pointer == 0)
                {
                    raf.seek(0);
                    printFloat(raf);
                }
            }

            raf.close();
        }
        catch(IOException ex)
        {
            MyIO.println(ex.getMessage());
        }
    }

    // Imprime o valor decimal de um arquivo utilizando um objeto de RandomAcessFile até encontrar uma quebra de linha
    public static void printFloat(RandomAccessFile raf) throws IOException
    {
        while(raf.getFilePointer() < raf.length())
        {
            char c = (char) raf.read();

            if(c == '\n' || c == '\r')
                break;

            MyIO.print(c);
        }

        MyIO.println("");
    }

    // Quando a linha contém um número real, remove os zeros a esquerda e adiciona um zero
    // se não houver nenhum dígito após o ponto
    public static String fixLine(String line)
    {
        if(!isRealNumber(line))
            return line;

        if(line.charAt(0) == '.')
            line = '0' + line;

        if(line.charAt(line.length() - 1) != '0')
            return line;

        int i;
        boolean hasLeftZeros = true;

        for(i = line.length() - 1; i >= 0; i--)
        {
            if(line.charAt(i) == '.')
            {
                hasLeftZeros = false;
                break;
            }

            if(line.charAt(i) != '0')
                break;
        }

        if(!hasLeftZeros)
            return line;

        String fixedLine = "";

        for(int j = 0; j <= i; j++)
            fixedLine += line.charAt(j);

        return fixedLine;
    }

    // Verifica se uma String é um número real
    public static boolean isRealNumber(String line)
    {
        for(int i = 0; i < line.length(); i++)
        {
            if(line.charAt(i) == '.')
                return true;
        }

        return false;
    }
}