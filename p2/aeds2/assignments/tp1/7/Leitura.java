import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Leitura 
{
    public static void main(String[] args)
    {
        MyIO.setCharset("UTF-8");

        char[] keys = new char[]{'a', 'e', 'i', 'o', 'u', 'á', 'é', 'í', 'ó', 'ú', 'à', 'è', 'ì', 'ò', 'ù', 'ã', 'õ', 'â', 'ê', 'î', 'ô', 'û', 'c', 'b', 't', 'n'};
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for(int i = 0; i < keys.length; i++)
            hashMap.put(keys[i], 0);
        
        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            String pageName = line;
            line = MyIO.readLine();
            String html = getHtml(line);
            
            count(html, pageName, hashMap);
            printResults(pageName, hashMap, keys);
            resetHashMap(hashMap);

            line = MyIO.readLine();
        }
    }   

    public static void printResults(String pageName, HashMap<Character, Integer> hashMap, char[] keyOrder)
    {
        for(int i = 0; i < keyOrder.length; i++)
        {
            char key = keyOrder[i];
            int value = hashMap.get(keyOrder[i]);

            switch(key)
            {
                case 'c':
                    MyIO.print("consoante");
                    break;
                case 'b':
                    MyIO.print("<br>");
                    break;
                case 't':
                    MyIO.print("<table>");
                    break;
                case 'n':
                    MyIO.println(pageName);
                    return;
                default:
                    MyIO.print(key);
            }

            MyIO.print("(" + value + ") ");
        }
    }

    public static void count(String html, String pageName, HashMap<Character, Integer> hashMap)
    {
        String[] specificStringsReference = new String[]{"<br>", "<table>", pageName};
        int[] specificStringsProgression = new int[3];
        char[] specificStringsKeys = new char[]{'b', 't', 'n'};

        for(int i = 0; i < html.length(); i++)
        {
            char c = html.charAt(i);
            char lowerCase = toLowerCase(c);

            if(isConsonant(c))
                increment(hashMap, 'c');
            else if(hashMap.containsKey(lowerCase))
                increment(hashMap, lowerCase);

            for(int j = 0; j < 3; j++)
            {
                char charToCompare = j != 2 ? lowerCase : c;

                if(charToCompare == specificStringsReference[j].charAt(specificStringsProgression[j]))
                {
                    specificStringsProgression[j]++;

                    if(specificStringsProgression[j] == specificStringsReference[j].length())
                    {
                        increment(hashMap, specificStringsKeys[j]);
                        specificStringsProgression[j] = 0;
                    }
                }
                else
                    specificStringsProgression[j] = 0;
            }
        }
    }


    public static void increment(HashMap<Character, Integer> hashMap, char key)
    {
        hashMap.put(key, hashMap.get(key) + 1);
    }

    public static void resetHashMap(HashMap<Character, Integer> hashMap)
    {
        for(char key : hashMap.keySet())
        {
            hashMap.put(key, 0);
        }
    }

    public static boolean isConsonant(char c)
    {
        c = toLowerCase(c);
        
        return c >= 'a' && c <= 'z' && c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u';
    }

    public static char toLowerCase(char c)
    {
        String specialCharsUpper = "ÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛ";
        String specialCharsLower = "áéíóúàèìòùãõâêîôû";

        int i;

        if(c >= 'A' && c <= 'Z')
            return (char)(c + ('a' - 'A'));
        else if((i = indexOf(c, specialCharsUpper)) != -1)
            return specialCharsLower.charAt(i);

        return c;
    }

    public static int indexOf(char c, String str)
    {
        for(int i = 0; i < str.length(); i++)
            if(c == str.charAt(i))
                return i;

        return -1;
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

    public static String getHtml(String endereco)
    {
        URL url;
        InputStream is = null;
        BufferedReader br;
        StringBuilder resp = new StringBuilder();
        String line;

        try 
        {
            url = new URI(endereco).toURL();
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) 
            {
                resp.append(line).append("\n");
            }
        } 
        catch (MalformedURLException mue) 
        {
            mue.printStackTrace();
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        catch (URISyntaxException uri) 
        {
            uri.printStackTrace();
        }   
        finally
        {
            try 
            {
                is.close();
            } 
            catch (IOException ioe) 
            {
                ioe.printStackTrace();
            }
        }

        return resp.toString();
   }
}