#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define TRUE 1
#define FALSE 0
typedef short bool;

typedef struct Pokemon
{
    int id;
    int generation;
    char *name;
    char *description;
    char *types[2];
    int typeAmmount;
    char *abilities;
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    char captureDate[11];
} Pokemon;

void sort(Pokemon **, int, int *, int *);
void build(Pokemon **, int, int, int *, int *);
int compare(Pokemon, Pokemon);
void swap(Pokemon *, Pokemon *);
char *read(FILE *, int);
void parse(Pokemon *, char *);
size_t nextVal(char *, int *, char *);
void print(Pokemon);
char *arrayToString(char **, int);
void parseListStr(char *, int, char **);
char *trim(char *, int);
void freePokemon(Pokemon *);

int main()
{
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    char line[32];

    char **lines = (char **)malloc(801 * sizeof(char *));
    int count = 0;

    fgets(line, 5, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        int id;
        sscanf(line, "%d", &id);

        lines[count++] = read(file, id);

        fgets(line, 5, stdin);
    }

    fclose(file);

    Pokemon **pokemons = (Pokemon **)malloc(count * sizeof(Pokemon *));

    for (int i = 0; i < count; i++)
    {
        pokemons[i] = (Pokemon *)malloc(sizeof(Pokemon));

        parse(pokemons[i], lines[i]);
    }

    fgets(line, 32, stdin);

    struct timespec start, end;

    clock_t beginTime = clock();

    int comparisons = 0;
    int moves = 0;

    sort(pokemons, count, &comparisons, &moves);

    clock_t endTime = clock();

    for (int i = 0; i < 10; i++)
        print(*pokemons[i]);

    /*
        Calculando o tempo em milissegundos que o Selection Sort levou para
        ordenar
    */
    double delta = (double)(endTime - beginTime) / CLOCKS_PER_SEC * 1000;

    file = fopen("842536_heapsort.txt", "w");

    fprintf(file, "842536\t%d\t%d\t%.3fms", comparisons, moves, delta);

    fclose(file);

    for (int i = 0; i < count; i++)
    {
        freePokemon(pokemons[i]);
    }

    free(pokemons);
}

// Heap sort pacial
void sort(Pokemon **pokemons, int size, int *comparisons, int *moves)
{
    int k = 10;

    for (int i = size / 2 - 1; i >= 0; i--)
        build(pokemons, size, i, comparisons, moves);

    for (int i = size - 1; i > 0; i--)
    {
        (*moves) += 3;
        swap(pokemons[0], pokemons[i]);
        build(pokemons, i, 0, comparisons, moves);
    }
}

// Monta o heap
void build(Pokemon **pokemons, int n, int i, int *comparisons, int *moves)
{
    int lg = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;

    if (l < n && compare(*pokemons[l], *pokemons[lg]) > 0)
        lg = l;
    (*comparisons)++;

    if (r < n && compare(*pokemons[r], *pokemons[lg]) > 0)
        lg = r;
    (*comparisons)++;

    if (lg != i)
    {
        swap(pokemons[i], pokemons[lg]);
        (*moves) += 3;
        build(pokemons, n, lg, comparisons, moves);
    }
}

/*
    Retorna a diferença entre as alturas. Em caso de empate, retorna
    a diferença entre os nomes
*/
int compare(Pokemon pokemon1, Pokemon pokemon2)
{
    int heightDiff = (int)(pokemon1.height * 100) - (int)(pokemon2.height * 100);

    if (heightDiff != 0)
        return heightDiff;

    return strcmp(pokemon1.name, pokemon2.name);
}

void swap(Pokemon *pokemon1, Pokemon *pokemon2)
{
    Pokemon tmp = *pokemon1;
    *pokemon1 = *pokemon2;
    *pokemon2 = tmp;
}

/*
    Leva o ponteiro para o início do arquivo e retorna o pokemon com o id
    correspondente
*/
char *read(FILE *file, int id)
{
    fseek(file, 0, SEEK_SET);

    char *line = (char *)malloc(256 * sizeof(char));

    fgets(line, 256, file);

    for (int i = 0; i < id; i++)
        fgets(line, 256, file);

    return line;
}

// Cria um novo pokemon com base nos atributos fornecidos na linha
void parse(Pokemon *pokemon, char *dataBuffer)
{
    char val[128];
    int offset = 0;
    size_t size;

    size = nextVal(val, &offset, dataBuffer);
    sscanf(val, "%d", &pokemon->id);

    size = nextVal(val, &offset, dataBuffer);
    sscanf(val, "%d", &pokemon->generation);

    size = nextVal(val, &offset, dataBuffer);
    pokemon->name = (char *)malloc((size + 1) * sizeof(char));
    strcpy(pokemon->name, val);

    size = nextVal(val, &offset, dataBuffer);
    pokemon->description = (char *)malloc((size + 1) * sizeof(char));
    strcpy(pokemon->description, val);

    pokemon->typeAmmount = 1;

    size = nextVal(val, &offset, dataBuffer);
    pokemon->types[0] = (char *)malloc((size + 1) * sizeof(char));
    strcpy(pokemon->types[0], val);

    size = nextVal(val, &offset, dataBuffer);
    pokemon->types[1] = (char *)malloc((size + 1) * sizeof(char));
    strcpy(pokemon->types[1], val);

    if (strlen(pokemon->types[1]) > 1)
        pokemon->typeAmmount++;

    size = nextVal(val, &offset, dataBuffer);
    pokemon->abilities = (char *)malloc((size - 1) * sizeof(char));
    for (int i = 1, j = 0; i < size - 1; i++, j++)
    {
        pokemon->abilities[j] = val[i];
    }
    pokemon->abilities[size - 2] = '\0';

    size = nextVal(val, &offset, dataBuffer);
    if (strlen(val) > 1)
        sscanf(val, "%lf", &pokemon->weight);
    else
        pokemon->weight = 0;

    size = nextVal(val, &offset, dataBuffer);
    if (strlen(val) > 1)
        sscanf(val, "%lf", &pokemon->height);
    else
        pokemon->height = 0;

    size = nextVal(val, &offset, dataBuffer);
    sscanf(val, "%d", &pokemon->captureRate);

    size = nextVal(val, &offset, dataBuffer);
    sscanf(val, "%hd", &pokemon->isLegendary);

    size = nextVal(val, &offset, dataBuffer);
    strcpy(pokemon->captureDate, val);
}

/*
    Lê o próximo valor de uma linha com valores separados por vírgula
    com base em um offset que dita onde começar na linha para retornar
    o valor
*/
size_t nextVal(char *buffer, int *offset, char *dataBuffer)
{
    int i = 0;
    bool isInList = FALSE;
    int off = *offset;

    if (dataBuffer[off] == '\"')
    {
        isInList = TRUE;
        buffer[i++] = dataBuffer[off++];
    }

    while (isInList || dataBuffer[off] != ',' && dataBuffer[off] != '\0' && dataBuffer[off] != '\n')
    {
        if (isInList && dataBuffer[off] == '\"')
            isInList = FALSE;

        buffer[i++] = dataBuffer[(off)++];
    }

    off++;
    *offset = off;

    buffer[i] = '\0';

    return i;
}

// Imprime todos os atributos de um pokemon
void print(Pokemon pokemon)
{
    char *pokemonTypes = arrayToString(pokemon.types, pokemon.typeAmmount);

    printf("[#%d -> %s: %s - %s - %s - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
           pokemon.id,
           pokemon.name,
           pokemon.description,
           pokemonTypes,
           pokemon.abilities,
           pokemon.weight,
           pokemon.height,
           pokemon.captureRate,
           pokemon.isLegendary ? "true" : "false",
           pokemon.generation,
           pokemon.captureDate);

    free(pokemonTypes);
}

// Transforma uma matriz de strings em uma string no padrão [{item1}, {item2}...]
char *arrayToString(char **array, int n)
{
    size_t size = 32;
    char *str = (char *)malloc(size * sizeof(char));

    strcpy(str, "[");

    for (int i = 0; i < n; i++)
    {
        if (strlen(array[i]) == 0)
            break;

        size_t additional_size = strlen(array[i]) + 4;

        if (strlen(str) + additional_size >= size)
        {
            size *= 2;
            str = realloc(str, size);
            if (str == NULL)
            {
                return NULL;
            }
        }

        strcat(str, "'");
        strcat(str, array[i]);
        strcat(str, "'");

        if (i < n - 1)
            strcat(str, ", ");
    }

    strcat(str, "]");

    return str;
}

// Remove caracteres no início e no fim da string ammount vezes
char *trim(char *buffer, int ammount)
{
    buffer += ammount;

    char *end = buffer + strlen(buffer) - 1;
    end -= ammount;
    *(end + 1) = '\0';

    return buffer;
}

// Libera a memória alocada de um pokemon corretamente
void freePokemon(Pokemon *pokemon)
{
    free(pokemon->name);
    free(pokemon->description);
    free(pokemon->types[0]);
    free(pokemon->types[1]);
    free(pokemon->abilities);
    free(pokemon);
}