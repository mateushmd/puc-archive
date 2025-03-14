#include <stdio.h>

int main()
{
    float valorCota[4] = {200, 300, 50, 100};
    int vendas[4] = {0};
    int qtdClientes = 0;
    int maisVendido = 0, menosVendido = 0;
    int cadastroAtual;
    float total;

    scanf("%d", &cadastroAtual);

    while (cadastroAtual != 5)
    {
        vendas[cadastroAtual - 1]++;

        qtdClientes++;

        if (vendas[cadastroAtual - 1] > vendas[maisVendido])
            maisVendido = cadastroAtual - 1;
        else if (vendas[cadastroAtual - 1] < vendas[menosVendido])
            menosVendido = cadastroAtual - 1;

        scanf("%d", &cadastroAtual);
    }

    for (int i = 0; i < 4; i++)
    {
        if (vendas[i] < vendas[menosVendido])
            menosVendido = i;

        total += vendas[i] * valorCota[i];

        printf("%.2f\n", (float)(vendas[i] * valorCota[i]));
    }

    printf("%d\n", maisVendido + 1);
    printf("%d\n", menosVendido + 1);
    printf("%.2f", (float)(total / qtdClientes));
}