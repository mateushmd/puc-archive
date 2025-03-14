import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

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
    private static Pokemon read(String line)
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
        try
        { weight = Double.parseDouble(values[7]); }
        catch(NumberFormatException ex) {}
        pokemon.setWeight(weight);

        double height = 0;
        try { height = Double.parseDouble(values[8]); }
        catch(NumberFormatException ex) {}
        pokemon.setHeight(height);

        int captureRate = 0;
        try{ captureRate = Integer.parseInt(values[9]); }
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

public class QuickParcial
{
    public static final String PATH = "/tmp/pokemon.csv";

    static int comparisons = 0;
    static int moves = 0;

    public static void main(String[] args) 
    {
        MyIO.setCharset("UTF-8");    

        List<Pokemon> pokedex = Pokemon.loadPokedex(PATH);

        List<Pokemon> selectedPokemons = new ArrayList<>();

        String line = MyIO.readLine();

        while(!line.equals("FIM"))
        {
            selectedPokemons.add(pokedex.get(Integer.parseInt(line) - 1));
            
            line = MyIO.readLine();
        }

        Pokemon[] pokemons = new Pokemon[selectedPokemons.size()];
        selectedPokemons.toArray(pokemons);

        long time = System.nanoTime();

        quickSort(pokemons);

        // Conversão de nanossegundos para milissegundos
        double delta = (double)(System.nanoTime() - time) / 1000000;

        for(int i = 0; i < 10; i++)
            pokemons[i].print();

        FileUtil.writeFile("842536_quicksort.txt",
            "842536\t" + comparisons + "\t" + moves + "\t" + 
                String.format("%.1f", delta) + "ms");
    }

    // Fachada do Quick sort
    static void quickSort(Pokemon[] pokemons)
    {
        int k = 10;
        sort(pokemons, 0, pokemons.length - 1);
    }

    // Quick sort parcial
    static void sort(Pokemon[] pokemons, int l, int r)
    {
        if (l < r)
        {
            int i = l - 1;
            Pokemon pivot = pokemons[r];

            for (int j = l; j < r; j++)
            {
                comparisons++;
                if (compare(pokemons[j], pivot) < 0)
                {
                    i++;
                    swap(pokemons, i, j);
                    moves += 3;
                }
            }

            swap(pokemons, i + 1, r);
            moves += 3;

            sort(pokemons, l, i);
            sort(pokemons, i + 2, r);
        }
    }

    static void swap(Pokemon[] pokemons, int i, int j)
    {
        Pokemon tmp = pokemons[i];
        pokemons[i] = pokemons[j];
        pokemons[j] = tmp;
    }

    /*
        Retorna a diferença entre as gerações. Em caso de empate, retorna
        a diferença entre os nomes
    */
    static int compare(Pokemon pokemon1, Pokemon pokemon2)
    {
        int genDiff = pokemon1.getGeneration() - 
            pokemon2.getGeneration();

        if (genDiff != 0)
            return genDiff;

        return pokemon1.getName().compareTo(pokemon2.getName());
    }
} 
