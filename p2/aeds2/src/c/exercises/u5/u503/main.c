#include <stdio.h>
#include <stdlib.h>

typedef struct Celula
{
    struct Celula *prox;
    int valor;
} Celula;

void meiose(Celula *);

int main()
{
    int valores[] = {8, 5, 6, 1};

    Celula *lista = (Celula *)malloc(sizeof(Celula));
    lista->valor = valores[0];

    Celula *celula = lista;

    for (int i = 1; i < sizeof(valores) / sizeof(valores[0]); i++)
    {
        celula->prox = (Celula *)malloc(sizeof(Celula));
        celula->prox->valor = valores[i];
        celula = celula->prox;
    }

    meiose(lista);

    for (Celula *c = lista; c != NULL; c = c->prox)
    {
        printf("%d ", c->valor);
    }
}

void meiose(Celula *c)
{
    if (c == NULL)
        return;

    Celula *tmp = c->prox;

    int valor = c->valor / 2;
    c->valor = valor;

    c->prox = (Celula *)malloc(sizeof(Celula));
    c->prox->valor = valor;
    c->prox->prox = tmp;

    meiose(tmp);
}