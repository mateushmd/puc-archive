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

typedef struct Cell
{
    struct Cell *next;
    Pokemon *pokemon;
} Cell;

typedef struct List
{
    Cell *first, *last;
    int size;
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
List *buildList();
void insertAt(List *, Pokemon *, int);
void insertFirst(List *, Pokemon *);
void insertLast(List *, Pokemon *);
Pokemon *removeAt(List *, int);
Pokemon *removeFirst(List *);
Pokemon *removeLast(List *);
void printList(List *);

int main()
{
    List *list = buildList();
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
            freePokemon(pokemon);
        }
        else if (strncmp(token, "RF", 2) == 0)
        {
            Pokemon *pokemon = removeLast(list);
            printf("(R) %s\n", pokemon->name);
            freePokemon(pokemon);
        }
        else if (strncmp(token, "R*", 2) == 0)
        {
            scanf("%d", &pos);
            Pokemon *pokemon = removeAt(list, pos);
            printf("(R) %s\n", pokemon->name);
            freePokemon(pokemon);
        }
    }

    fclose(file);

    printList(list);
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

List *buildList()
{
    List *list = malloc(sizeof(List));
    list->first = list->last = malloc(sizeof(Cell));
    list->size = 0;
    return list;
}

void insertAt(List *list, Pokemon *pokemon, int pos)
{
    Cell *target, *before;
    int i;
    for (i = 0, target = list->first->next; i < pos; i++, before = target, target = target->next)
        ;

    Cell *new = malloc(sizeof(Cell));
    new->pokemon = pokemon;
    new->next = target;
    before->next = new;
    list->size++;
}

// Insere um novo pokemon na primeira posição da lista
void insertFirst(List *list, Pokemon *pokemon)
{
    Cell *cell = malloc(sizeof(Cell));
    cell->pokemon = pokemon;
    cell->next = list->first->next;
    list->first->next = cell;
    list->size++;
}

// Insere um novo pokemon na última posição da lista
void insertLast(List *list, Pokemon *pokemon)
{
    Cell *cell = malloc(sizeof(Cell));
    cell->pokemon = pokemon;
    list->last->next = cell;
    list->last = cell;
    list->size++;
}

// Remove o pokemon na posição pos na lista
Pokemon *removeAt(List *list, int pos)
{
    Cell *target, *before;

    int i;

    for (i = 0, target = list->first->next; i < pos; before = target, target = target->next, i++)
        ;

    Pokemon *pokemon = target->pokemon;
    before->next = target->next;
    free(target);

    list->size--;

    return pokemon;
}

// Remove o primeiro pokemon da lista
Pokemon *removeFirst(List *list)
{
    Cell *cell = list->first->next;
    Pokemon *pokemon = cell->pokemon;
    list->first->next = cell->next;
    free(cell);
    list->size--;
    return pokemon;
}

// Remove o último pokemon da lista
Pokemon *removeLast(List *list)
{
    return removeAt(list, list->size - 1);
}

// Imprime a lista no formato [i] %Pokemon
void printList(List *list)
{
    Cell *cell;
    int i;
    for (i = 0, cell = list->first->next; cell != NULL; cell = cell->next, i++)
    {
        printf("[%d] ", i);
        print(*(cell->pokemon));
    }
}