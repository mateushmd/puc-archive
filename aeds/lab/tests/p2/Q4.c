#include <stdio.h>

void f(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = *a;
}

void g(int **x, int **y)
{
    int *tmp;
    tmp = *x;
    *x = *y;
    *y = tmp;
}

void printArr(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        printf("%d ", arr[i]);
    }

    printf("\n");
}

int main()
{
    int v1[5] = {1, 2, 3, 4, 5};
    int v2[5] = {6, 7, 8, 9, 10};

    g(v1, v2);

    printArr(v1, 5);
    printArr(v2, 5);
}