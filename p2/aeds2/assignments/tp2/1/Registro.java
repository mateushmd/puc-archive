import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    //Retorna a linha correspondente ao número especificado dentro de um arquivo
    public static String getLine(int lineNumber, String path)
    {
        String line = null;

        try(BufferedReader bReader = new BufferedReader(new FileReader(path)))
        { 
            int i = 1;

            while((line = bReader.readLine()) != null)
            {
                if(i == lineNumber)
                    return line;

                i++;
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return line;
    }
}

public class Registro
{
    public static final String PATH = "/tmp/pokemon.csv";

    public static void main(String[] args) 
    {
        MyIO.setCharset("UTF-8");

        String line = MyIO.readLine();

        while(!line.equals("FIM"))
        {
            int lineNumber = Integer.parseInt(line) + 1;

            String fileLine = FileUtil.getLine(lineNumber, PATH);
            
            Pokemon.read(fileLine).print();

            line = MyIO.readLine();
        }
    }
}