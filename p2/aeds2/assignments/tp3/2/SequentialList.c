#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define POKEDEX_SIZE 801

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

typedef struct List
{
    int size;
    Pokemon **pokemons;
} List;

char *read(FILE *, int);
void parse(Pokemon *, char *);
size_t nextVal(char *, int *, char *);
void print(Pokemon);
char *arrayToString(char **, int);
void parseListStr(char *, int, char **);
char *trim(char *, int);
void freePokemon(Pokemon *);
bool validateStr();
// Funções da lista
List *createList();
int insertAt(List *, Pokemon *, int);
int insertFirst(List *, Pokemon *);
int insertLast(List *, Pokemon *);
Pokemon *removeAt(List *, int);
Pokemon *removeFirst(List *);
Pokemon *removeLast(List *);
void printList(List *);
void freeList(List *);

int main()
{
    List *list = createList();
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    char line[6];

    fgets(line, 5, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        int id;
        sscanf(line, "%d", &id);

        char *data = read(file, id);

        Pokemon *pokemon = (Pokemon *)malloc(sizeof(Pokemon));
        parse(pokemon, data);
        insertLast(list, pokemon);
        free(data);

        fgets(line, 5, stdin);
        while (!validateStr(line))
            fgets(line, 5, stdin);
    }

    int n;
    scanf("%d", &n);
    char token[3];

    for (int i = 0; i < n; i++)
    {
        fgets(token, 3, stdin);

        while (!validateStr(token))
            fgets(token, 3, stdin);

        int id, pos;

        if (strncmp(token, "II", 2) == 0)
        {
            scanf("%d", &id);
            char *data = read(file, id);
            Pokemon *pokemon = (Pokemon *)malloc(sizeof(Pokemon));
            parse(pokemon, data);
            insertFirst(list, pokemon);
            free(data);
        }
        else if (strncmp(token, "IF", 2) == 0)
        {
            scanf("%d", &id);
            char *data = read(file, id);
            Pokemon *pokemon = (Pokemon *)malloc(sizeof(Pokemon));
            parse(pokemon, data);
            insertLast(list, pokemon);
            free(data);
        }
        else if (strncmp(token, "I*", 2) == 0)
        {
            scanf("%d", &pos);
            scanf("%d", &id);
            char *data = read(file, id);
            Pokemon *pokemon = (Pokemon *)malloc(sizeof(Pokemon));
            parse(pokemon, data);
            insertAt(list, pokemon, pos);
            free(data);
        }
        else if (strncmp(token, "RI", 2) == 0)
        {
            Pokemon *pokemon = removeFirst(list);
            printf("(R) %s\n", pokemon->name);
        }
        else if (strncmp(token, "RF", 2) == 0)
        {
            Pokemon *pokemon = removeLast(list);
            printf("(R) %s\n", pokemon->name);
        }
        else if (strncmp(token, "R*", 2) == 0)
        {
            scanf("%d", &pos);
            Pokemon *pokemon = removeAt(list, pos);
            printf("(R) %s\n", pokemon->name);
        }
    }

    fclose(file);

    printList(list);
    freeList(list);
}

/*
    Leva o ponteiro para o início do arquivo e retorna a linha
    correspondente ao número especificado
*/
char *read(FILE *file, int linePos)
{
    fseek(file, 0, SEEK_SET);

    char *line = (char *)malloc(256 * sizeof(char));

    fgets(line, 256, file);

    for (int i = 0; i < linePos; fgets(line, 256, file), i++)
        ;

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

// Valida a string lida na entrada padrão
bool validateStr(char *buffer)
{
    for (int i = 0; i < strlen(buffer); i++)
    {
        if (buffer[i] >= 'A' && buffer[i] <= 'Z' ||
            buffer[i] >= '0' && buffer[i] <= '9' ||
            buffer[i] == '*')
            return TRUE;
    }

    return FALSE;
}

// Aloca memória para uma nova lista e retorna seu endereço
List *createList()
{
    List *list = (List *)malloc(sizeof(List));
    list->size = 0;
    list->pokemons = (Pokemon **)malloc(801 * sizeof(Pokemon *));
    return list;
}

// Insere um novo pokemon na posição pos na lista
int insertAt(List *list, Pokemon *pokemon, int pos)
{
    if (list->size >= POKEDEX_SIZE || pos < 0 || pos > list->size)
        return 0;

    for (int i = list->size; i > pos; i--)
        list->pokemons[i] = list->pokemons[i - 1];
    list->pokemons[pos] = pokemon;
    list->size++;

    return 1;
}

// Insere um novo pokemon na primeira posição da lista
int insertFirst(List *list, Pokemon *pokemon)
{
    return insertAt(list, pokemon, 0);
}

// Insere um novo pokemon na última posição da lista
int insertLast(List *list, Pokemon *pokemon)
{
    return insertAt(list, pokemon, list->size);
}

// Remove o pokemon na posição pos na lista
Pokemon *removeAt(List *list, int pos)
{
    if (pos < 0 || pos >= list->size)
        return NULL;

    Pokemon *pokemon = list->pokemons[pos];

    for (int i = pos; i < list->size - 1; i++)
        list->pokemons[i] = list->pokemons[i + 1];

    list->size--;

    return pokemon;
}

// Remove o primeiro pokemon da lista
Pokemon *removeFirst(List *list)
{
    return removeAt(list, 0);
}

// Remove o último pokemon da lista
Pokemon *removeLast(List *list)
{
    return removeAt(list, list->size - 1);
}

// Imprime a lista no formato [i] %Pokemon
void printList(List *list)
{
    for (int i = 0; i < list->size; i++)
    {
        printf("[%d] ", i);
        print(*list->pokemons[i]);
    }
}

// Libera o espaço de memória ocupado pela lista
void freeList(List *list)
{
    for (int i = 0; i < list->size; i++)
    {
        freePokemon(list->pokemons[i]);
    }

    free(list->pokemons);
    free(list);
}