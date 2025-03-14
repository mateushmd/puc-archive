#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

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
    struct Cell *next, *before;
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
void insertLast(List *, Pokemon *);
void printList(List *);
void quickSort(List *, int *, int *);
void sort(List *, int *, int *, int, int);
int compare(Pokemon *, Pokemon *);

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

    struct timespec start, end;

    clock_t beginTime = clock();

    int comparisons = 0;
    int moves = 0;

    quickSort(list, &comparisons, &moves);

    clock_t endTime = clock();

    printList(list);

    /*
        Calculando o tempo em milissegundos que o Selection Sort levou para
        ordenar
    */
    double delta = (double)(endTime - beginTime) / CLOCKS_PER_SEC * 1000;

    file = fopen("842536_quicksort2.txt", "w");

    fprintf(file, "842536\t%d\t%d\t%.3fms", comparisons, moves, delta);

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

// Aloca memória para uma nova lista e retorna seu endereço
List *buildList()
{
    List *list = (List *)malloc(sizeof(List));
    list->size = 0;
    list->first = list->last = malloc(sizeof(Cell));
    return list;
}

// Insere um novo pokemon na última posição da lista
void insertLast(List *list, Pokemon *pokemon)
{
    Cell *cell = malloc(sizeof(Cell));
    cell->pokemon = pokemon;
    list->last->next = cell;
    cell->before = list->last;
    list->last = cell;
    list->size++;
}

// Imprime a lista no formato [i] %Pokemon
void printList(List *list)
{
    for (Cell *c = list->first->next; c != NULL; c = c->next)
        print(*(c->pokemon));
}

Cell *getCell(List *list, int idx)
{
    int i;
    Cell *c;
    for (i = 0, c = list->first->next; i < idx; i++, c = c->next)
        ;
    return c;
}

// Função fachada do quick sort
void quickSort(List *list, int *comps, int *movs)
{
    sort(list, comps, movs, 0, list->size - 1);
}

// Implementação do quick sort
void sort(List *list, int *comps, int *movs, int l, int r)
{
    int i = l, j = r;
    Pokemon *pivot = getCell(list, (l + r) / 2)->pokemon;

    while (i <= j)
    {
        while (compare(getCell(list, i)->pokemon, pivot) < 0)
        {
            (*comps)++;
            i++;
        }

        while (compare(getCell(list, j)->pokemon, pivot) > 0)
        {
            (*comps)++;
            j--;
        }

        if (i <= j)
        {
            Cell *ci = getCell(list, i);
            Cell *cj = getCell(list, j);
            Pokemon *tmp = ci->pokemon;
            ci->pokemon = cj->pokemon;
            cj->pokemon = tmp;
            (*movs) += 3;
            i++;
            j--;
        }
    }

    if (i < r)
        sort(list, comps, movs, i, r);

    if (j > l)
        sort(list, comps, movs, l, j);
}

// Aplica as regras de comparação para ordenação da atividade
// Se o Pokemon p1 deve vir antes de p2, retorna um número menor que 0
// Se ambos forem iguais, retorna 0
// Se o Pokemon p2 deve vir antes de p1, retorna um número maior que 0
int compare(Pokemon *p1, Pokemon *p2)
{
    if (p1->generation == p2->generation)
        return strcmp(p1->name, p2->name);

    return p1->generation - p2->generation;
}