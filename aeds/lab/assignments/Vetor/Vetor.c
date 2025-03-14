#include <stdio.h>
#include <stdlib.h>

int main()
{
    int size, greatest, pos = 0;

    scanf("%d", &size);

    int *arr = (int *)malloc(size * sizeof(int));

    for (int i = 0; i < size; i++)
    {
        scanf("%d", &arr[i]);
    }

    greatest = arr[0];

    for (int i = 1; i < size; i++)
    {
        if (arr[i] >= greatest)
        {
            greatest = arr[i];
            pos = i;
        }
    }

    printf("%d %d\n", greatest, pos);

    free(arr);
}
