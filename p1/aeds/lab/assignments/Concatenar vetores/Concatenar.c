#include <stdio.h>
#include <stdlib.h>

void printArr(int *, int);

int main()
{
    int m, n, *a, *b, *c;

    scanf("%d", &m);
    scanf("%d", &n);

    a = (int *)malloc(m * sizeof(int));
    b = (int *)malloc(n * sizeof(int));
    c = (int *)malloc((m + n) * sizeof(int));

    for (int i = 0; i < m; i++)
    {
        scanf("%d", &a[i]);
    }

    for (int i = 0; i < n; i++)
    {
        scanf("%d", &b[i]);
    }

    for (int i = 0; i < m; i++)
    {
        c[i] = a[i];
    }

    for (int i = 0; i < n; i++)
    {
        c[i + m] = b[i];
    }

    printArr(a, m);
    printArr(b, n);
    printArr(c, m + n);

    free(a);
    free(b);
    free(c);
}

void printArr(int *array, int size)
{
    for (int i = 0; i < size; i++)
    {
        printf("%d ", array[i]);
    }

    printf("\n");
}