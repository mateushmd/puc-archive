import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.RandomAccess;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class Pokemon
{
    private int id;
    private int generation;
    private String name;
    private String description;
    private List<String> types;
    private List<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    public Pokemon() { }

    public Pokemon(int id, int generation, String name, String description, 
        List<String> types, List<String> abilities, double weight, double height, 
        int captureRate, boolean isLegendary, Date captureDate)
    {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = types;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    public int getId() { return id; }
    public int getGeneration() { return generation; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public int getCaptureRate() { return captureRate; }
    public boolean getIsLegendary() { return isLegendary; }

    //Retorna a data de captura como um objeto Date
    //Usado para clonar pokemons
    public Date getCaptureDate() { return captureDate; }

    //Retorna a data de captura como uma string no formado dd/MM/yyyy
    //Usado para o método print()
    public String getCaptureDateAsString() 
    {
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        return sfd.format(captureDate);
    }

    //Transforma uma lista em uma string no padrão [{item1}, {item2}...]
    //Usado para o método print()
    private String listToString(List<String> list)
    {
        StringBuilder sb = new StringBuilder("["); 

        for (int i = 0; i < list.size(); i++) 
        {
            sb.append(String.format("'%s'", list.get(i)));

            if(i < list.size() - 1)
                sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }

    public List<String> getTypes() { return types; }
    public String getTypesAsString() { return listToString(types); }
    public List<String> getAbilities() { return abilities; }
    public String getAbilitiesAsString() { return listToString(abilities); }

    public void setId(int id) { this.id = id; }
    public void setGeneration(int generation) { this.generation = generation; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setTypes(List<String> types) { this.types = types; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }
    public void setIsLegendary(boolean isLegendary) { this.isLegendary = isLegendary; }
    public void setCaptureDate(Date captureDate) { this.captureDate = captureDate; }

    //Cria um novo pokemon com base nos atributos fornecidos na linha
    public static Pokemon read(String line)
    {
        Pokemon pokemon = new Pokemon();

        String[] values = line.split("(?<!'),");
        for(int i = 0; i < values.length; i++) values[i].trim();

        pokemon.setId(Integer.parseInt(values[0]));
        pokemon.setGeneration(Integer.parseInt(values[1]));
        pokemon.setName(values[2]);
        pokemon.setDescription(values[3]);

        ArrayList<String> types = new ArrayList<>();
        if(values[4].length() > 0) types.add(values[4]);
        if(values[5].length() > 0) types.add(values[5]);
        pokemon.setTypes(types);
        
        ArrayList<String> abilities = new ArrayList<>();
        String a = values[6].substring(2, values[6].length() - 2);
        String[] as = a.split(",");
        for(String ability : as)
        {
            ability = ability.trim();
            abilities.add(ability.substring(1, ability.length() - 1));
        }
        pokemon.setAbilities(abilities);

        double weight = 0;
        try { weight = Double.parseDouble(values[7]); }
        catch(NumberFormatException ex) {}
        pokemon.setWeight(weight);

        double height = 0;
        try { height = Double.parseDouble(values[8]); }
        catch(NumberFormatException ex) {}
        pokemon.setHeight(height);

        int captureRate = 0;
        try { captureRate = Integer.parseInt(values[9]); }
        catch(NumberFormatException ex) {}
        pokemon.setCaptureRate(captureRate);

        pokemon.setIsLegendary(values[10].equals("1") ? true : false);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try { pokemon.setCaptureDate(sdf.parse(values[11])); }
        catch(ParseException ex) { ex.printStackTrace(); }

        return pokemon;
    }

    // Retorna um array de pokemons dado um arquivo de tabela com dados de pokemons
    public static List<Pokemon> loadPokedex(String path)
    {
        List<Pokemon> pokedex = new ArrayList<>();

        try(BufferedReader bReader = new BufferedReader(new FileReader(path)))
        {
            bReader.readLine();

            String line;
            
            while((line = bReader.readLine()) != null)
            {
                pokedex.add(read(line));
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return pokedex;
    }

    //Imprime todos os atributos do pokemon no formato especificado
    public void print()
    {
        MyIO.println(String.format(
            "[#%d -> %s: %s - %s - %s - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s", 
            getId(), 
            getName(), 
            getDescription(), 
            getTypesAsString(), 
            getAbilitiesAsString(), 
            getWeight(), 
            getHeight(), 
            getCaptureRate(), 
            getIsLegendary(), 
            getGeneration(), 
            getCaptureDateAsString()));
    }

    //Cria um clone do objeto
    @Override
    public Pokemon clone()
    {
        Pokemon clone = new Pokemon();
        clone.setId(getId());
        clone.setName(getName());
        clone.setDescription(getDescription());

        clone.setTypes(new ArrayList<String>());
        clone.getTypes().addAll(getTypes());

        clone.setAbilities(new ArrayList<String>());
        clone.getAbilities().addAll(getAbilities());

        clone.setWeight(getWeight());
        clone.setHeight(getHeight());
        clone.setCaptureRate(getCaptureRate());
        clone.setIsLegendary(getIsLegendary());
        clone.setGeneration(getGeneration());
        clone.setCaptureDate(getCaptureDate());

        return clone;
    }
}

class FileUtil
{
    RandomAccessFile file;

    public FileUtil(String path) throws IOException
    {
        file = new RandomAccessFile(path, "r");    
    }

    //Move o ponteiro para o início do arquivo e retorna a linha desejada
    public String readLine(int line) throws IOException
    {
        file.seek(0);
        
        String fileLine = null;
        
        /*
            Apesar de poder manipular livremente o ponteiro, o seu reposiocionamento
            é no nível dos bytes, portanto não há como posicioná-lo diretamente
            com a função seek(). A alternativa é ler linha por linha até encontrar
            a desejada.
        */
        for(int i = 0; i < line; i++, fileLine = file.readLine());

        return fileLine;
    }

    public void close() throws IOException
    {
        file.close();
    }

    // Cria um novo arquivo e escreve
    public static void writeFile(String fileName, String string) 
    {
        try 
        {
            File file = new File(fileName);
            file.delete();
            file.createNewFile();

            FileWriter fWriter = new FileWriter(file);
            fWriter.write(string);
            fWriter.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}

class NodeNode
{
    Node node;
    int value;
    NodeNode l, r;

    public NodeNode(int value)
    {
        this.value = value;
        node = null;
        l = r = null;
    }
}

class Node
{
    Pokemon pokemon;
    Node l, r;

    public Node(Pokemon pokemon)
    {
        this.pokemon = pokemon;
        l = r = null;
    }
}

class BinaryTree
{
    public class SearchResult
    {
        public String path;
        public int comparisons;

        public SearchResult()
        {
            path = ""; 
            comparisons = 0;
        }
    }

    NodeNode root;

    public BinaryTree()
    {
        int[] vals = new int[]{7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};

        for(int val : vals)
        {
            root = init(root, val);
        }
    }

    private NodeNode init(NodeNode node, int value)
    {
        if(node == null)
            node = new NodeNode(value);
        else if(value < node.value)
            node.l = init(node.l, value);
        else if(value > node.value)
            node.r = init(node.r, value);

        return node;
    }   

    public void insert(Pokemon pokemon)
    {
        try {
            root = insert(pokemon, root);
        } catch (Exception e) {

        }
    }

    private NodeNode insert(Pokemon pokemon, NodeNode node) throws Exception
    {
        if(pokemon.getCaptureRate() % 15 < node.value)
            node.l = insert(pokemon, node.l);
        else if(pokemon.getCaptureRate() % 15 > node.value)
            node.r = insert(pokemon, node.r);
        else
            node.node = insertNode(pokemon, node.node);

        return node;
    }

    private Node insertNode(Pokemon pokemon, Node node) throws Exception
    {
        if(node == null)
            node = new Node(pokemon);
        else if(pokemon.getName().compareTo(node.pokemon.getName()) < 0)
            node.l = insertNode(pokemon, node.l);
        else if(pokemon.getName().compareTo(node.pokemon.getName()) > 0)
            node.r = insertNode(pokemon, node.r);
        else
            throw new Exception("Duplicate Pokemon");

        return node;
    }

    public SearchResult search(String pokemonName)
    {
        SearchResult searchResult = new SearchResult();
        boolean found = search(pokemonName, root, searchResult);
        searchResult.path += found ? "SIM" : "NAO";
        return searchResult;
    }

    private boolean search(String pokemonName, NodeNode node, SearchResult sr)
    {
        if(node == root)
            sr.path += "raiz ";

        boolean found = false;

        if(node != null) found = search(pokemonName, node.node, sr);
        sr.path += " ";
        if(node == null) return false;

        if(found)  
            return true;

        sr.path += "ESQ ";
        sr.comparisons++;
        if(search(pokemonName, node.l, sr)) return true;
        
        sr.path += "DIR ";
        sr.comparisons++;
        if(search(pokemonName, node.r, sr)) return true;
        
        return false;
    }

    private boolean search(String pokemonName, Node node, SearchResult sr)
    {
        if(node == null)
            return false;

        boolean found = false;

        if(pokemonName.equals(node.pokemon.getName()))
        {
            sr.comparisons++;
            found = true;
        }
        else if(pokemonName.compareTo(node.pokemon.getName()) < 0)
        {
            sr.comparisons++;
            sr.path += "esq ";
            found = found || search(pokemonName, node.l, sr);
        }
        else
        {
            sr.path += "dir ";
            found = found || search(pokemonName, node.r, sr);    
        }

        return found;
    }
}

public class Main 
{
    public static void main(String[] args) throws IOException
    {
        FileUtil fileUtil = new FileUtil("/tmp/pokemon.csv");

        BinaryTree tree = new BinaryTree();

        String line = MyIO.readLine();
    
        while(!line.equals("FIM"))
        {
            int id = Integer.parseInt(line);
            String strPokemon = fileUtil.readLine(id + 1);
            Pokemon pokemon = Pokemon.read(strPokemon);
            tree.insert(pokemon);
            line = MyIO.readLine();
        }

        fileUtil.close();

        line = MyIO.readLine();
        long time = System.currentTimeMillis();
        int comparisons = 0;

        while(!line.equals("FIM"))
        {
            BinaryTree.SearchResult searchResult = tree.search(line);
            comparisons += searchResult.comparisons;
            MyIO.println("=> " + line);
            MyIO.println(searchResult.path);
            line = MyIO.readLine();
        }

        long delta = System.currentTimeMillis() - time;

        FileUtil.writeFile("842536_arvoreArvore.txt",
            842536 + "\t" + delta + "ms\t" + comparisons);
    }
}