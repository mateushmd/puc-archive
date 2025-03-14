#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    int size, *binary, decimal = 0;

    scanf("%d", &size);

    binary = (int *)malloc(size * sizeof(int));

    for (int i = size - 1; i >= 0; i--)
    {
        scanf("%d", &binary[i]);
    }

    for (int i = 0; i < size; i++)
    {
        decimal += binary[i] * pow(2, i);
    }

    printf("%d\n", decimal);

    free(binary);
}