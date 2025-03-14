#include "arrays.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int *generateArray(int n)
{
    srand(time(NULL));

    int *arr = (int *)malloc(n * sizeof(int));

    for (int i = 0; i < n; arr[i] = rand() % n, i++);
}

void printArray(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        printf("%d", arr[i]);

        if (i < n - 1)
            printf(" ");
    }

    printf("\n");
}