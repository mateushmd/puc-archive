#include <stdio.h>
#include <math.h>

int main()
{
    int populacao, infectados = 1, dias = 1;

    scanf("%d", &populacao);

    while (infectados < populacao)
    {
        infectados += pow(2, dias);

        dias++;
    }

    printf("%d", dias);
}