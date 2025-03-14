#include <stdio.h>
#include <stdlib.h>

typedef struct CelulaL
{
    struct CelulaL *prox;
    int valor;
} CelulaL;

typedef struct CelulaM
{
    struct CelulaM *sup, *inf, *esq, *dir;
    CelulaL *lista;
} CelulaM;

CelulaM *gerarMatriz(int);
CelulaL *gerarLista();
void removerImpares(CelulaM *);
void imprimir(CelulaM *);

int main()
{
    CelulaM *matriz = gerarMatriz(3);
    imprimir(matriz);
    removerImpares(matriz);
    imprimir(matriz);
}

void imprimir(CelulaM *matriz)
{
    for (CelulaM *c1 = matriz; c1 != NULL; c1 = c1->inf)
    {
        for (CelulaM *c2 = c1; c2 != NULL; c2 = c2->dir)
        {
            for (CelulaL *cl = c2->lista->prox; cl != NULL; cl = cl->prox)
            {
                printf("%d ", cl->valor);
            }

            printf("\t");
        }

        printf("\n");
    }
}

void removerImpares(CelulaM *matriz)
{
    for (CelulaM *c1 = matriz; c1 != NULL; c1 = c1->inf)
    {
        for (CelulaM *c2 = c1; c2 != NULL; c2 = c2->dir)
        {
            for (CelulaL *ca = c2->lista, *cc = c2->lista->prox; cc != NULL; cc = cc->prox)
            {
                if (cc->valor % 2 != 0)
                    ca->prox = cc->prox;
                else
                    ca = ca->prox;
            }
        }
    }
}

CelulaM *gerarMatriz(int t)
{
    CelulaM *inicio = (CelulaM *)malloc(sizeof(CelulaM));
    inicio->lista = gerarLista();

    CelulaM *tmp = inicio;

    for (int i = 0; i < t - 1; i++)
    {
        tmp->dir = (CelulaM *)malloc(sizeof(CelulaM));
        tmp->dir->esq = tmp;
        tmp = tmp->dir;
        tmp->lista = gerarLista();
    }

    CelulaM *s, *o;
    s = inicio;

    for (int i = 0; i < t - 1; i++)
    {
        o = s;
        s->inf = (CelulaM *)malloc(sizeof(CelulaM));
        s->inf->sup = s;
        s = s->inf;
        s->lista = gerarLista();
        tmp = s;

        for (int j = 0; j < t - 1; j++)
        {
            tmp->dir = (CelulaM *)malloc(sizeof(CelulaM));
            tmp->dir->esq = tmp;
            tmp = tmp->dir;
            tmp->lista = gerarLista();
            o = o->dir;
            tmp->sup = o;
            o->inf = tmp;
        }
    }

    return inicio;
}

CelulaL *gerarLista()
{
    CelulaL *inicio = (CelulaL *)malloc(sizeof(CelulaL));
    CelulaL *tmp = inicio;

    for (int i = 0; i < 4; i++)
    {
        tmp->prox = (CelulaL *)malloc(sizeof(CelulaL));
        tmp = tmp->prox;
        tmp->valor = i + 1;
    }

    return inicio;
}