#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Contato
{
    char nome[51];
    char telefone[12];
    char email[51];
    char cpf[12];
} Contato;

typedef struct Celula
{
    Contato *contato;
    struct Celula *prox;
} Celula;

typedef struct No
{
    char letra;
    struct No *esq;
    struct No *dir;
    Celula *primeiro;
    Celula *ultimo;
} No;

void inserir(No **, char);
No *procurarLetra(No *, char);
void inserirContato(No *, Contato *);
Contato *procurarPorNome(No *, char *);
Contato *procurarPorCpf(No *, char *);
Contato *procurarPorCpfLista(Celula *, char *);

int main()
{
    char letras[] = {'M', 'L', 'N', 'K', 'O', 'J', 'P', 'I', 'Q', 'H', 'R',
                     'G', 'S', 'F', 'T', 'E', 'U', 'D', 'V', 'C', 'W', 'B', 'X',
                     'A', 'Y', 'Z'};

    No *raiz;

    for (int i = 0; i < 26; i++)
        inserir(&raiz, letras[i]);

    Contato *contato = (Contato *)malloc(sizeof(Contato));
    strcpy(contato->nome, "Mateus");
    strcpy(contato->cpf, "16719087673");
    inserirContato(raiz, contato);

    contato = (Contato *)malloc(sizeof(Contato));
    strcpy(contato->nome, "Zasdasd");
    strcpy(contato->cpf, "00000000000");
    inserirContato(raiz, contato);

    contato = procurarPorCpf(raiz, "00000000000");

    if (contato == NULL)
        printf("DEU RUIM BURRO BURRO BURRO BURRO BURRO");

    printf("%s", contato->nome);
}

void inserir(No **no, char letra)
{
    if (*no == NULL)
    {
        *no = (No *)malloc(sizeof(No));
        (*no)->primeiro = (Celula *)malloc(sizeof(Celula));
        (*no)->ultimo = (*no)->primeiro;
        (*no)->letra = letra;
        return;
    }

    if (letra < (*no)->letra)
        inserir(&(*no)->esq, letra);
    else if (letra > (*no)->letra)
        inserir(&(*no)->dir, letra);
}

No *procurarLetra(No *no, char letra)
{
    if (no == NULL || no->letra == letra)
        return no;

    if (letra < no->letra)
        return procurarLetra(no->esq, letra);
    return procurarLetra(no->dir, letra);
}

void inserirContato(No *raiz, Contato *contato)
{
    char inicial = contato->nome[0];

    if (!((inicial >= 'a' && inicial <= 'z') || (inicial >= 'A' && inicial <= 'Z')))
        return;

    No *no = procurarLetra(raiz, inicial);

    if (no == NULL)
        printf("PORASRASRARAR\n");

    Celula *novo = no->ultimo->prox = (Celula *)malloc(sizeof(Celula));

    novo->contato = contato;

    no->ultimo = no->ultimo->prox;
}

Contato *procurarPorNome(No *no, char *nome)
{
    char inicial = nome[0];

    if (no == NULL)
        return NULL;

    if (no->letra == inicial)
    {
        Celula *celula;

        for (celula = no->primeiro->prox; celula != NULL && strcmp(celula->contato->nome, nome) != 0; celula = celula->prox)
            ;

        if (celula == NULL)
            return NULL;

        return celula->contato;
    }

    if (inicial < no->letra)
        return procurarPorNome(no->esq, nome);

    return procurarPorNome(no->dir, nome);
}

Contato *procurarPorCpf(No *no, char *cpf)
{
    if (no == NULL)
        return NULL;

    Contato *contato = procurarPorCpfLista(no->primeiro, cpf);

    if (contato == NULL)
        contato = procurarPorCpf(no->esq, cpf);

    if (contato == NULL)
        contato = procurarPorCpf(no->dir, cpf);

    return contato;
}

Contato *procurarPorCpfLista(Celula *inicio, char *cpf)
{
    Celula *celula;

    for (celula = inicio->prox; celula != NULL; celula = celula->prox)
        if (strcmp(celula->contato->cpf, cpf) == 0)
            return celula->contato;

    return NULL;
}