#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define POKEDEX_SIZE 801
#define QUEUE_SIZE 5

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
    char **abilities;
    int abilitiesAmmount;
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    char captureDate[11];
} Pokemon;

typedef struct CQueue
{
    Pokemon **pokemons;
    int front;
    int rear;
    int size;
} CQueue;

char *read(FILE *, int);
void parse(Pokemon *, char *);
int splitAbilities(char *, char ***);
size_t nextVal(char *, int *, char *);
void print(Pokemon);
char *arrayToString(char **, int);
void parseListStr(char *, int, char **);
char *trim(char *, int);
char *removeWhiteSpaces(char *);
void freePokemon(Pokemon *);
bool validateStr();
CQueue *buildQueue();
void enqueue(CQueue *, Pokemon *);
Pokemon *dequeue(CQueue *);
int avgCapRate(CQueue *);
void printQueue(CQueue *);

int main()
{
    CQueue *queue = buildQueue();

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
        free(data);

        enqueue(queue, pokemon);

        printf("Média: %d\n", avgCapRate(queue));

        fgets(line, 5, stdin);
        while (!validateStr(line))
            fgets(line, 5, stdin);
    }

    int n;
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        char cmd = fgetc(stdin);
        while (cmd != 'I' && cmd != 'R')
            cmd = fgetc(stdin);

        if (cmd == 'I')
        {
            int id;
            scanf("%d", &id);
            char *data = read(file, id);
            Pokemon *pokemon = malloc(sizeof(Pokemon));
            parse(pokemon, data);
            enqueue(queue, pokemon);
            free(data);
            printf("Média: %d\n", avgCapRate(queue));
        }
        else
        {
            Pokemon *pokemon = dequeue(queue);
            printf("(R) %s\n", pokemon->name);
        }
    }

    printQueue(queue);

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
    char *val = (char *)malloc(128 * sizeof(char));
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
    char *trimVal = trim(val, 2);
    char **splitted;
    int splittedSize = splitAbilities(trimVal, &splitted);
    pokemon->abilities = (char **)malloc(splittedSize * sizeof(char *));
    for (int i = 0; i < splittedSize; i++)
    {
        char *ability = splitted[i];
        ability = removeWhiteSpaces(ability);
        ability = trim(ability, 1);
        pokemon->abilities[i] = (char *)malloc((strlen(ability) + 1) * sizeof(char));
        strcpy(pokemon->abilities[i], ability);
    }
    for (int i = 0; i < splittedSize; i++)
        free(splitted[i]);
    free(splitted);
    pokemon->abilitiesAmmount = splittedSize;

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

    free(val);
}

// Transforma a lista de habilidades em string em um vetor com as habilidades
// e retorna o tamanho do vetor
int splitAbilities(char *buffer, char ***destination)
{
    *destination = (char **)malloc(16 * sizeof(char *));
    int i = 0, offset = 0;
    char *val = (char *)malloc(128 * sizeof(char));

    int size = nextVal(val, &offset, buffer);
    (*destination)[i] = (char *)malloc((size + 1) * sizeof(char));
    strcpy((*destination)[i], val);
    i++;

    while ((size = nextVal(val, &offset, buffer)) > 0)
    {
        (*destination)[i] = (char *)malloc((size + 1) * sizeof(char));
        strcpy((*destination)[i], val);
        i++;
    }

    return i;
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

    if (off > 0 && dataBuffer[off - 1] == '\0')
    {
        buffer[i] = '\0';
        return i;
    }

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
    char *pokemonAbilities = arrayToString(pokemon.abilities, pokemon.abilitiesAmmount);

    printf("[#%d -> %s: %s - %s - %s - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
           pokemon.id,
           pokemon.name,
           pokemon.description,
           pokemonTypes,
           pokemonAbilities,
           pokemon.weight,
           pokemon.height,
           pokemon.captureRate,
           pokemon.isLegendary ? "true" : "false",
           pokemon.generation,
           pokemon.captureDate);

    free(pokemonTypes);
    free(pokemonAbilities);
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

// Remove os espaços em branco no início da string
char *removeWhiteSpaces(char *buffer)
{
    while (*buffer == ' ')
        buffer++;

    return buffer;
}

// Libera a memória alocada de um pokemon corretamente
void freePokemon(Pokemon *pokemon)
{
    free(pokemon->name);
    free(pokemon->description);
    free(pokemon->types[0]);
    free(pokemon->types[1]);
    for (int i = 0; i < pokemon->abilitiesAmmount; i++)
        free(pokemon->abilities[i]);
    // free(pokemon->abilities);
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

CQueue *buildQueue()
{
    CQueue *queue = malloc(sizeof(CQueue));
    queue->pokemons = malloc(QUEUE_SIZE * sizeof(Pokemon));
    queue->front = queue->rear = -1;
    queue->size = 0;
    return queue;
}

void enqueue(CQueue *queue, Pokemon *pokemon)
{
    if (queue->front == -1)
    {
        queue->front = queue->rear = 0;
    }
    else
    {
        queue->rear = (queue->rear + 1) % QUEUE_SIZE;
        if (queue->rear == queue->front)
        {
            queue->front = (queue->front + 1) % QUEUE_SIZE;
            queue->size--;
        }
    }

    queue->pokemons[queue->rear] = pokemon;
    queue->size++;
}

Pokemon *dequeue(CQueue *queue)
{
    Pokemon *pokemon = queue->pokemons[queue->front];
    queue->front = (queue->front + 1) % QUEUE_SIZE;
    queue->size--;

    if (queue->size == 0)
        queue->front = -1;

    return pokemon;
}

int avgCapRate(CQueue *queue)
{
    int sum = 0;

    for (int i = queue->front, j = 0; j < queue->size; i = (i + 1) % QUEUE_SIZE, j++)
        sum += queue->pokemons[i]->captureRate;

    double avg = (double)sum / (double)queue->size;

    return (int)(round(avg));
}

void printQueue(CQueue *queue)
{
    printf("\n[0] ");
    print(*(queue->pokemons[queue->front]));
    for (int i = (queue->front + 1) % QUEUE_SIZE, j = 1; j < queue->size; i = (i + 1) % QUEUE_SIZE, j++)
    {
        printf("[%d] ", j);
        print(*(queue->pokemons[i]));
    }
}