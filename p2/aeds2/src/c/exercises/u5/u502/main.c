#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct lCelula
{
    struct lCelula *prox;
    int valor;
} lCelula;

typedef struct mCelula
{
    struct mCelula *sup;
    struct mCelula *inf;
    struct mCelula *esq;
    struct mCelula *dir;
    lCelula *primeiro;
    lCelula *ultimo;
} mCelula;

void inicializarMatriz(mCelula **, int);
mCelula *criarCelulaMatriz();
void inicializarLista(lCelula **);

int main()
{
    srand(time(NULL));

    mCelula *inicio;
    inicializarMatriz(&inicio, 3);

    lCelula *lista, *tmp;
    tmp = lista = (lCelula *)malloc(sizeof(lCelula));

    for (mCelula *m = inicio; m != NULL; m = m->inf->dir)
    {
        for (lCelula *l = m->primeiro->prox; l != NULL; l = l->prox)
        {
            tmp->prox = (lCelula *)malloc(sizeof(lCelula));
            tmp->prox->valor = l->valor;
            tmp = tmp->prox;
        }

        if (m->inf == NULL)
            break;
    }

    for (lCelula *l = lista->prox; l != NULL; l = l->prox)
    {
        printf("%d ", l->valor);
    }
}

void inicializarMatriz(mCelula **inicio, int tamanho)
{
    (*inicio) = criarCelulaMatriz();

    mCelula *a = *inicio;
    mCelula *b = *inicio;
    mCelula *c = *inicio;

    for (int i = 0; i < tamanho - 1; i++)
    {
        c->dir = criarCelulaMatriz();
        c->dir->esq = c;
        c = c->dir;
    }

    for (int i = 0; i < tamanho - 1; i++)
    {
        c = a;
        c->inf = criarCelulaMatriz();
        c->inf->sup = c;
        a = c = c->inf;

        for (int i = 0; i < tamanho - 1; i++)
        {
            c->dir = criarCelulaMatriz();
            c->dir->esq = c;
            c = c->dir;
            b = b->dir;
            c->sup = b;
            b->inf = c;
        }

        b = a;
    }
}

mCelula *criarCelulaMatriz()
{
    mCelula *celula = (mCelula *)malloc(sizeof(mCelula));
    celula->ultimo = celula->primeiro = (lCelula *)malloc(sizeof(lCelula));
    inicializarLista(&(celula->ultimo));
    return celula;
}

void inicializarLista(lCelula **ultimo)
{
    for (int i = 0; i < 3; i++, (*ultimo) = (*ultimo)->prox)
    {
        (*ultimo)->prox = (lCelula *)malloc(sizeof(lCelula));
        (*ultimo)->prox->valor = rand() % 10;
    }
}