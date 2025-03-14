#include <stdio.h>
#include <stdlib.h>

typedef struct No
{
    struct No *esq, *dir;
    int valor;
} No;

void inserir(No **, int);
void intercalar(No **, No *, No *);
void imprimir(No *);

int main()
{
    No *no1, *no2, *no3;

    inserir(&no1, 3);
    inserir(&no1, 1);
    inserir(&no1, 2);

    inserir(&no2, 6);
    inserir(&no2, 4);
    inserir(&no2, 5);

    intercalar(&no3, no1, no2);

    imprimir(no3);
}

void inserir(No **no, int valor)
{
    if ((*no) == NULL)
    {
        (*no) = (No *)malloc(sizeof(No));
        (*no)->valor = valor;
    }
    else if (valor < (*no)->valor)
        inserir(&((*no)->esq), valor);
    else if (valor > (*no)->valor)
        inserir(&((*no)->dir), valor);
}

void intercalar(No **destino, No *no1, No *no2)
{
    if (no1 != NULL)
    {
        inserir(destino, no1->valor);
        intercalar(destino, no2, no1->esq);
        intercalar(destino, no2, no1->dir);
    }
}

void imprimir(No *no)
{
    if (no == NULL)
        return;

    imprimir(no->esq);
    printf("%d ", no->valor);
    imprimir(no->dir);
}