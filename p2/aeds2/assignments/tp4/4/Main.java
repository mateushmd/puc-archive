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
			é no nível dos bytes, portanto não há como posicioná-lo retamente
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

class Node
{
	boolean color;
	Pokemon pokemon;
	Node l, r;

	public Node(Pokemon pokemon, boolean color)
	{
		this.pokemon = pokemon;
		l = r = null;
		this.color = color;
	}

	public Node(Pokemon pokemon)
	{
		this(pokemon, false);
	}
}

class RedBlack
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

	Node root;

	public void insert(Pokemon pokemon) {
		if (root == null) {
			root = new Node(pokemon);
		
		} else if (root.l == null && root.r == null) {
			if (pokemon.getName().compareTo(root.pokemon.getName()) < 0) {
				root.l = new Node(pokemon);
			} else {
				root.l = new Node(pokemon);
			}
		} else if (root.l == null) {
			if (pokemon.getName().compareTo(root.pokemon.getName()) < 0) {
				root.l = new Node(pokemon);
			} else if (pokemon.getName().compareTo(root.r.pokemon.getName()) < 0) {
				root.l = new Node(root.pokemon);
				root.pokemon = pokemon;
			} else {
				root.l = new Node(root.pokemon);
				root.pokemon = root.r.pokemon;
				root.r.pokemon = pokemon;
			}
			root.l.color = root.r.color = false;
		} else if (root.r == null) {
			if (pokemon.getName().compareTo(root.pokemon.getName()) > 0) {
				root.r = new Node(pokemon);
			} else if (pokemon.getName().compareTo(root.l.pokemon.getName()) > 0) {
				root.r = new Node(root.pokemon);
				root.pokemon = pokemon;
		
			} else {
				root.r = new Node(root.pokemon);
				root.pokemon = root.l.pokemon;
				root.l.pokemon = pokemon;
			}
			root.l.color = root.r.color = false;
		} else {
			insert(pokemon, null, null, null, root);
		}
		root.color = false;
	}

	private void insert(Pokemon pokemon, Node ggfather, Node gfather, Node father, Node i) {
		if (i == null) {
			if (pokemon.getName().compareTo(father.pokemon.getName()) < 0) {
				i = father.l = new Node(pokemon, true);
			} else {
				i = father.r = new Node(pokemon, true);
			}
			if (father.color == true) {
				balance(ggfather, gfather, father, i);
			}
		} else {
			if (i.l != null && i.r != null && i.l.color == true && i.r.color == true) {
				i.color = true;
				i.l.color = i.r.color = false;
				if (i == root) {
					i.color = false;
				} else if (father.color == true) {
					balance(ggfather, gfather, father, i);
				}
			}
			if (pokemon.getName().compareTo(i.pokemon.getName()) < 0) {
				insert(pokemon, gfather, father, i, i.l);
			} else if (pokemon.getName().compareTo(i.pokemon.getName()) > 0) {
				insert(pokemon, gfather, father, i, i.r);
			}
		}
	}

	private void balance(Node ggfather, Node gfather, Node father, Node i) {
		if (father.color == true) {
			if (father.pokemon.getName().compareTo(gfather.pokemon.getName()) > 0) {
				if (i.pokemon.getName().compareTo(father.pokemon.getName()) > 0) {
					gfather = rotateL(gfather);
				} else {
					gfather = rotateRL(gfather);
				}
			} else {
				if (i.pokemon.getName().compareTo(father.pokemon.getName()) < 0) {
					gfather = rotateR(gfather);
				} else {
					gfather = rotateLR(gfather);
				}
			}
			if (ggfather == null) {
				root = gfather;
			} else if (gfather.pokemon.getName().compareTo(ggfather.pokemon.getName()) < 0) {
				ggfather.l = gfather;
			} else {
				ggfather.r = gfather;
			}
			gfather.color = false;
			gfather.l.color = gfather.r.color = true;
		}
	}

	private Node rotateR(Node node) {
		Node nol = node.l;
		Node nolr = nol.r;

		nol.r = node;
		node.l = nolr;

		return nol;
	}

	private Node rotateL(Node node) {
		Node nor = node.r;
		Node norl = nor.l;

		nor.l = node;
		node.r = norl;
		return nor;
	}

	private Node rotateRL(Node node) {
		node.r = rotateR(node.r);
		return rotateL(node);
	}

	private Node rotateLR(Node node) {
		node.l = rotateL(node.l);
		return rotateR(node);
	}

	public SearchResult search(String pokemonName)
	{
		SearchResult searchResult = new SearchResult();
		search(pokemonName, root, searchResult);
		return searchResult;
	}

	private void search(String pokemonName, Node node, SearchResult sr)
	{
		if(node == null)
		{
			sr.path += "NAO";
			return;
		}

		if(node == root)
			sr.path += "raiz ";

		if(pokemonName.compareTo(node.pokemon.getName()) == 0)
		{
			sr.comparisons++;
			sr.path += "SIM";
		}
		else if(pokemonName.compareTo(node.pokemon.getName()) < 0)
		{
			sr.comparisons++;
			sr.path += "esq ";
			search(pokemonName, node.l, sr);
		}
		else
		{
			sr.path += "dir ";
			search(pokemonName, node.r, sr);    
		}
	}
}

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		FileUtil fileUtil = new FileUtil("/tmp/pokemon.csv");

		RedBlack tree = new RedBlack();

		String line = MyIO.readLine();
	
		while(!line.equals("FIM"))
		{
			int id = Integer.parseInt(line);
			String strPokemon = fileUtil.readLine(id + 1);
			tree.insert(Pokemon.read(strPokemon));
			line = MyIO.readLine();
		}

		fileUtil.close();

		line = MyIO.readLine();
		long time = System.currentTimeMillis();
		int comparisons = 0;

		while(!line.equals("FIM"))
		{
			RedBlack.SearchResult searchResult = tree.search(line);
			comparisons += searchResult.comparisons;
			System.out.println(line);
			System.out.println(searchResult.path);
			line = MyIO.readLine();
		}

		long delta = System.currentTimeMillis() - time;

		FileUtil.writeFile("842536_alvinegra.txt",
			842536 + "\t" + delta + "ms\t" + comparisons);
	}
}