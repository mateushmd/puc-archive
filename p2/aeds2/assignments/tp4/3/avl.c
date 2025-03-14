#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define POKEDEX_SIZE 801

#define max(a, b) \
    ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })

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
    Pokemon *pokemon;
    struct Cell *l, *r;
    int level;
} Cell;

typedef struct SearchResult
{
    bool found;
    char path[256];
    int comparisons;
} SearchResult;

char *read(FILE *, int);
void parse(Pokemon *, char *);
size_t nextVal(char *, int *, char *);
void print(Pokemon);
char *arrayToString(char **, int);
void parseListStr(char *, int, char **);
char *trim(char *, int);
void freePokemon(Pokemon *);
bool validateStr();

void setLevel(Cell *);
int getLevel(Cell *);
void insert(Pokemon *, Cell **);
void balance(Cell **);
void rotateR(Cell **);
void rotateL(Cell **);
SearchResult *search(char *, Cell *);
void srch(char *, Cell *, SearchResult *);

int main()
{
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    Cell *root;

    char line[16];

    fgets(line, 15, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        int id;
        sscanf(line, "%d", &id);

        char *data = read(file, id);

        Pokemon *pokemon = (Pokemon *)malloc(sizeof(Pokemon));
        parse(pokemon, data);
        insert(pokemon, &root);
        free(data);

        fgets(line, 15, stdin);
        while (!validateStr(line))
            fgets(line, 15, stdin);
    }

    fclose(file);

    struct timespec start, end;

    clock_t beginTime = clock();

    int comparisons = 0;

    fgets(line, 15, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        line[strlen(line) - 1] = '\0';

        SearchResult *searchResult = search(line, root);
        printf("%s\n%s\n", line, searchResult->path);
        comparisons += searchResult->comparisons;

        fgets(line, 15, stdin);
        while (!validateStr(line))
            fgets(line, 15, stdin);
    }

    clock_t endTime = clock();

    /*
        Calculando o tempo em milissegundos que o Selection Sort levou para
        ordenar
    */
    double delta = (double)(endTime - beginTime) / CLOCKS_PER_SEC * 1000;

    file = fopen("842536_avl.txt", "w");

    fprintf(file, "842536\t%.3fms\t%d", delta, comparisons);

    fclose(file);
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

void setLevel(Cell *cell)
{
    cell->level = 1 + max(getLevel(cell->l), getLevel(cell->r));
}

int getLevel(Cell *cell)
{
    return cell != NULL ? cell->level : 0;
}

void insert(Pokemon *pokemon, Cell **cell)
{
    if (*cell == NULL)
    {
        *cell = malloc(sizeof(Cell));
        (*cell)->pokemon = pokemon;
        (*cell)->l = NULL;
        (*cell)->r = NULL;
        (*cell)->level = 1;
    }
    else if (strcmp(pokemon->name, (*cell)->pokemon->name) < 0)
        insert(pokemon, &((*cell)->l));
    else if (strcmp(pokemon->name, (*cell)->pokemon->name) > 0)
        insert(pokemon, &((*cell)->r));
    else
        printf("ERROR");

    balance(cell);
}

void balance(Cell **cell)
{
    if ((*cell) == NULL)
        return;

    int factor = getLevel((*cell)->r) - getLevel((*cell)->l);

    if (abs(factor) <= 1)
    {
        setLevel(*cell);
    }
    else if (factor == 2)
    {
        int rChildFactor = getLevel((*cell)->r->r) - getLevel((*cell)->r->l);

        if (rChildFactor == -1)
            rotateR(&((*cell)->r));

        rotateL(cell);
    }
    else if (factor == -2)
    {
        int lChildFactor = getLevel((*cell)->l->r) - getLevel((*cell)->l->l);

        if (lChildFactor == 1)
            rotateL(&((*cell)->l));

        rotateR(cell);
    }
}

void rotateR(Cell **cell)
{
    Cell *lCell = (*cell)->l;
    Cell *lrCell = lCell->r;
    lCell->r = *cell;
    (*cell)->l = lrCell;
    setLevel(*cell);
    setLevel(lCell);
    *cell = lCell;
}

void rotateL(Cell **cell)
{
    Cell *rCell = (*cell)->r;
    Cell *rlCell = rCell->l;
    rCell->l = *cell;
    (*cell)->r = rlCell;
    setLevel(*cell);
    setLevel(rCell);
    *cell = rCell;
}

SearchResult *search(char *pokemonName, Cell *cell)
{
    SearchResult *searchResult = malloc(sizeof(SearchResult));
    searchResult->comparisons = 0;
    searchResult->found = FALSE;
    memset(searchResult->path, 0, sizeof(searchResult->path));
    strcat(searchResult->path, "raiz ");
    srch(pokemonName, cell, searchResult);
    return searchResult;
}

void srch(char *pokemonName, Cell *cell, SearchResult *searchResult)
{
    if (cell == NULL)
    {
        strcat(searchResult->path, "NAO");
    }
    else if (strcmp(pokemonName, cell->pokemon->name) < 0)
    {
        searchResult->comparisons++;
        strcat(searchResult->path, "esq ");
        srch(pokemonName, cell->l, searchResult);
    }
    else if (strcmp(pokemonName, cell->pokemon->name) > 0)
    {
        searchResult->comparisons++;
        strcat(searchResult->path, "dir ");
        srch(pokemonName, cell->r, searchResult);
    }
    else
    {
        strcat(searchResult->path, "SIM");
        searchResult->found = TRUE;
    }
}