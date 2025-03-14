#include <stdio.h>
#include <stdlib.h>

int main()
{
    int n, qtdNegativo = 0;

    float soma = 0.0;

    float *vet;

    scanf("%d", &n);

    vet = (float *)malloc(n * sizeof(float));

    for (int i = 0; i < n; i++)
    {
        scanf("%f", &vet[i]);
    }

    for (int i = 0; i < n; i++)
    {
        if (vet[i] < 0)
        {
            qtdNegativo++;
            continue;
        }

        soma += vet[i];
    }

    printf("%d ", qtdNegativo);

    // Verifica se a parte decimal é nula ou não
    if ((int)(soma * 10) % 10 != 0)
        printf("%.1f\n", soma);
    else
        printf("%.0f\n", soma);
}